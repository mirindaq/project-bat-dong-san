package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.javaweb.converter.BuildingConverter;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.impl.BuildingRepositoryImpl;
import com.javaweb.service.BuildingService;


@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private BuildingConverter buildingConverter;
	


	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> typeCode) {

		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, typeCode);
		List<BuildingDTO> buildingDTOs = new ArrayList<BuildingDTO>();
		for (BuildingEntity x : buildingEntities) {
			BuildingDTO building = buildingConverter.toBuildingDTO(x);
//			building.setName(x.getName());
//			building.setNumberOfBasement(x.getNumberOfBasement());
//			building.setAddress(x.getStreet() + ", " + x.getWard());
			buildingDTOs.add(building);
		}
		return buildingDTOs;
	}

}
