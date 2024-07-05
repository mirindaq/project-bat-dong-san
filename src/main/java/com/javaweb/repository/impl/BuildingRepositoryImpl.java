package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String strDbUrl = "jdbc:mysql://localhost:3306/estatebasic";
	static final String user = "root";
	static final String pass = "viethoang123";
	
	@Override
	public List<BuildingEntity> findAll( String name, Long districtId) {
		StringBuilder sql = new StringBuilder("select * from building where 1 = 1 ");
		if ( name != null && !name.equals("")) {
			sql.append("and name like '%" + name + "%' ");
		}
		if( districtId != null ) {
			sql.append("and districtid = " + districtId + " ");
		}
		List<BuildingEntity> list = new ArrayList<BuildingEntity>();
		try(Connection conn = DriverManager.getConnection(strDbUrl,user,pass);
				Statement st = conn.createStatement();
	            ResultSet rs = st.executeQuery(sql.toString());){        
            while(rs.next()){
            	BuildingEntity buildingEntity = new BuildingEntity();
            	buildingEntity.setName(rs.getString("name"));
            	buildingEntity.setNumberOfBasement(rs.getInt("numberofbasement"));
            	buildingEntity.setWard(rs.getString("ward"));
            	buildingEntity.setStreet(rs.getString("street"));
            	list.add(buildingEntity);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
		return list;
	}
	
}
