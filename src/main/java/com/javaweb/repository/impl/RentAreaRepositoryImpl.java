package com.javaweb.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RentAreaEntity> findByBuildingId(Long id) {
		List<RentAreaEntity> list = new ArrayList<RentAreaEntity>();
		StringBuilder sql = new StringBuilder("select * from rentarea where buildingid = " + id);
		Query query = entityManager.createNativeQuery(sql.toString(), RentAreaEntity.class);
		return query.getResultList();
	}

}
