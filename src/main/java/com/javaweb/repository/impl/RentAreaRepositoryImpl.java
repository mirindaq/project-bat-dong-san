package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;

import com.javaweb.utils.ConnectJDBCUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> findByBuildingId(Long id) {
		List<RentAreaEntity> list = new ArrayList<RentAreaEntity>();
		StringBuilder sql = new StringBuilder("select * from rentarea where buildingid = " + id);
		try (	Connection conn = ConnectJDBCUtil.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql.toString());) {
			while (rs.next()) {
				RentAreaEntity tmp = new RentAreaEntity();
				tmp.setId(rs.getLong("id"));
				tmp.setValue(rs.getInt("value"));
				tmp.setBuildingId(id);
				list.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
