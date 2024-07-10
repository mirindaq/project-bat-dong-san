package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

import com.javaweb.utils.ConnectJDBCUtil;
import com.javaweb.utils.NullCheckUtil;
import com.javaweb.utils.NumberCheckUtil;
import com.javaweb.utils.StringCheckUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public static void joinTable(BuildingSearchBuilder builderConverter, StringBuilder sql) {
		Long staffId = builderConverter.getStaffId();
		if (NullCheckUtil.checkNull(staffId)) {
			sql.append(" join assignmentbuilding asb on asb.buildingid = b.id ");
		}

		if (builderConverter.getTypeCode() != null && builderConverter.getTypeCode().size() != 0) {
			sql.append(" join buildingrenttype brt on brt.buildingid = b.id ");
			sql.append(" join renttype rt on brt.renttypeid = rt.id ");
		}

		Integer areaTo = builderConverter.getAreaTo();
		Integer areaFrom = builderConverter.getAreaFrom();

		if (NullCheckUtil.checkNull(areaTo) || NullCheckUtil.checkNull(areaFrom)) {
			sql.append(" join rentarea ra on ra.buildingid = b.id ");
		}
	}

	public static void queryNormal(BuildingSearchBuilder builderConverter, StringBuilder sql) {
		try {
			Field[] fields = builderConverter.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String name = field.getName();
				if (!name.equals("staffId") && !name.equals("typeCode") && !name.startsWith("area")
						&& !name.startsWith("rentPrice")) {
					Object value = field.get(builderConverter);
					if (value != null) {
						if (StringCheckUtil.checkString(value.toString())) {
							if (NumberCheckUtil.checkNumber(value.toString())) {
								sql.append(" and b." + name + " = " + value + " ");
							} else {
								sql.append(" and b." + name + " like '%" + value + "%' ");
							}
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void querySpecial(BuildingSearchBuilder builderConverter, StringBuilder sql) {
		Long staffId = builderConverter.getStaffId();
		if (NullCheckUtil.checkNull(staffId)) {
			sql.append(" and asb.staffid = " + staffId );
		}
		
		Integer areaFrom = builderConverter.getAreaFrom();
		if ( NullCheckUtil.checkNull(areaFrom)) {
			sql.append(" and ra.value >= " + areaFrom );
		}
		
		Integer areaTo = builderConverter.getAreaTo();
		if ( NullCheckUtil.checkNull(areaTo)) {
			sql.append(" and ra.value <= " + areaTo );
		}
		
		Integer rentPriceFrom = builderConverter.getRentPriceFrom();;
		if ( NullCheckUtil.checkNull(rentPriceFrom)) {
			sql.append(" and b.rentprice >= " + rentPriceFrom );
		}
		
		Integer rentPriceTo = builderConverter.getRentPriceTo();
		if ( NullCheckUtil.checkNull(rentPriceTo)) {
			sql.append(" and b.rentprice <= " + rentPriceTo );
		}
		
		List<String> typeCode = builderConverter.getTypeCode();
		if ( typeCode != null && typeCode.size() != 0 ) {
			String types = typeCode.stream().map(x-> "'" + x + "'").collect(Collectors.joining(","));
			sql.append(" and rt.code in ( " + types + " ) " );
		}
	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder builderConverter) {
		StringBuilder sql = new StringBuilder(
				"select b.id , b.name, b.numberofbasement, b.ward, b.street, b.districtid, "
						+ "b.floorarea, b.rentprice, b.managername, b.managerphonenumber, b.level, b.direction"
						+ " from building b ");
		joinTable(builderConverter, sql);
		sql.append("where 1 = 1 ");
		queryNormal(builderConverter, sql);
		querySpecial(builderConverter, sql);
		sql.append(" group by b.id");
		List<BuildingEntity> list = new ArrayList<BuildingEntity>();
		try (Connection conn = ConnectJDBCUtil.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql.toString());) {
			while (rs.next()) {
				BuildingEntity be = new BuildingEntity();
				be.setId(rs.getLong("id"));
				be.setName(rs.getString("name"));
				be.setNumberOfBasement(rs.getInt("numberofbasement"));
				be.setWard(rs.getString("ward"));
				be.setStreet(rs.getString("street"));
				be.setDistrictId(rs.getString("districtid"));
				be.setFloorArea(rs.getInt("floorarea"));
				be.setDirection(rs.getString("direction"));
				be.setLevel(rs.getString("level"));
				be.setRentPrice(rs.getInt("rentprice"));
//				be.setRentPriceDescription(rs.getString("rentpricedescription"));
				be.setManagerName(rs.getString("managername"));
				be.setManagerPhoneNumber(rs.getString("managerphonenumber"));
				list.add(be);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
