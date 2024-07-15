package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.repository.impl.RentAreaRepositoryImpl;

@Component
public class BuildingConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RentAreaRepositoryImpl rentAreaRepositoryImpl;
	
	public BuildingDTO toBuildingDTO( BuildingEntity item ) {
		BuildingDTO buildingDTO = modelMapper.map(item, BuildingDTO.class);
		buildingDTO.setAddress(item.getWard()+ " " + item.getStreet() + " " +item.getDistrict().getName());
		List<RentAreaEntity> listArea = rentAreaRepositoryImpl.findByBuildingId(item.getId());
		buildingDTO.setRentArea(listArea.stream().map(t->t.getValue().toString()).collect(Collectors.joining(",")));
		return buildingDTO;
	}
}
