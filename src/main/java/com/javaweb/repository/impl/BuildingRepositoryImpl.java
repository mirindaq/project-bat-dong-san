package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.NullCheckUtil;
import com.javaweb.utils.NumberCheckUtil;
import com.javaweb.utils.StringCheckUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

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
				"select b.* from building b ");
		joinTable(builderConverter, sql);
		sql.append("where 1 = 1 ");
		queryNormal(builderConverter, sql);
		querySpecial(builderConverter, sql);
		sql.append(" group by b.id");
		Query query = entityManager.createNativeQuery(sql.toString(),BuildingEntity.class);
		return query.getResultList();
	}

}
