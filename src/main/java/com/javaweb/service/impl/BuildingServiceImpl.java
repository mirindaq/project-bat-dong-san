package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.impl.BuildingRepositoryImpl;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	
	@Autowired
	private BuildingRepository buildingRepository;
	@Override
	public List<BuildingDTO> findAll( String name, Long districtId ) {
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(name, districtId);
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();
		for ( BuildingEntity x : buildingEntities ) {
			BuildingDTO building = new BuildingDTO();
			building.setName(x.getName());
			building.setNumberOfBasement(x.getNumberOfBasement());
			building.setAddress(x.getStreet() + ", " + x.getWard());
			buildingDTOs.add(building);
		}
		return buildingDTOs;
	}

}
