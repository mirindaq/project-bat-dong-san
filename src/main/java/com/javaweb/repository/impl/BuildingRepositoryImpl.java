package com.javaweb.repository.impl;

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

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

import utils.ConnectJDBCUtil;
import utils.NumberCheckUtil;
import utils.StringCheckUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	

	public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		String staffId = (String) params.get("staffId");
		if (StringCheckUtil.checkString(staffId)) {
			sql.append(" join assignmentbuilding asb on asb.buildingid = b.id ");
		}
		
		if ( typeCode != null && typeCode.size() != 0 ) {
			sql.append(" join buildingrenttype brt on brt.buildingid = b.id ");
			sql.append(" join renttype rt on brt.renttypeid = rt.id ");
		}
		
		String areaTo = (String) params.get("areaTo");
		String areaFrom = (String ) params.get("areaFrom");
		
		if ( (StringCheckUtil.checkString(areaFrom) && NumberCheckUtil.checkNumber(areaFrom) ) ||
			( StringCheckUtil.checkString(areaTo) && NumberCheckUtil.checkNumber(areaTo)) ){
			sql.append(" join rentarea ra on ra.buildingid = b.id ");
		}
	}

	public static void queryNormal(Map<String, Object> params, StringBuilder sql) {
		for ( Map.Entry<String, Object> item : params.entrySet()) {
			String tmp = item.getKey();
			if ( !tmp.equals("staffId") && !tmp.equals("typeCode") && 
					!tmp.startsWith("area") && !tmp.startsWith("rentPrice") ) {
				String value = item.getValue().toString();
				if ( StringCheckUtil.checkString(value) ) {
					if ( NumberCheckUtil.checkNumber(value) ) {
						sql.append(" and b." + tmp + " = " + value + " " );
					}
					else {
						sql.append(" and b." + tmp + " like '%" + value + "%' " );
					}
				}
			}
		}
	}
	
	public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		String staffId = (String) params.get("staffId");
		if (StringCheckUtil.checkString(staffId)) {
			sql.append(" and asb.staffid = " + staffId );
		}
		
		String areaFrom = (String ) params.get("areaFrom");
		if ( StringCheckUtil.checkString(areaFrom) && NumberCheckUtil.checkNumber(areaFrom)) {
			sql.append(" and ra.value >= " + areaFrom );
		}
		
		String areaTo = (String) params.get("areaTo");
		if ( StringCheckUtil.checkString(areaTo) && NumberCheckUtil.checkNumber(areaTo)) {
			sql.append(" and ra.value <= " + areaTo );
		}
		
		String rentPriceFrom = (String) params.get("rentPriceTo");
		if ( StringCheckUtil.checkString(rentPriceFrom) && NumberCheckUtil.checkNumber(rentPriceFrom)) {
			sql.append(" and b.rentprice >= " + rentPriceFrom );
		}
		
		String rentPriceTo = (String) params.get("rentPriceTo");
		if ( StringCheckUtil.checkString(rentPriceTo) && NumberCheckUtil.checkNumber(rentPriceTo)) {
			sql.append(" and b.rentprice <= " + rentPriceTo );
		}
		
		if ( typeCode != null && typeCode.size() != 0 ) {
			String types = typeCode.stream().map(x-> "'" + x + "'").collect(Collectors.joining(","));
			sql.append(" and rt.code in ( " + types + " ) " );
		}
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		StringBuilder sql = new StringBuilder("select b.id , b.name, b.numberofbasement, b.ward, b.street, b.districtid, "
				+ "b.floorarea, b.rentprice, b.managername, b.managerphonenumber" + " from building b ");
		joinTable(params, typeCode, sql);
		sql.append("where 1 = 1 ");
		queryNormal(params, sql);
		querySpecial(params, typeCode, sql);
		sql.append(" group by b.id");
		List<BuildingEntity> list = new ArrayList<BuildingEntity>();
		try (	Connection conn = ConnectJDBCUtil.getConnection();
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
//				be.setDirection(rs.getString("direction"));
//				be.setLevel(rs.getString("level"));
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
