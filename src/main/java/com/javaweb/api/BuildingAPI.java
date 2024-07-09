package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

import customexception.FieldRequiredException;


@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	
//	@GetMapping("/api/building")
//	public List<BuildingDTO> getBuilding( @RequestParam(value="name", required = false) String name,
//							@RequestParam(value="districtid", required = false) Long districtId) {	
//		List<BuildingDTO> list = buildingService.findAll(name, districtId);
//		System.out.println("JDBC");
//		return list;
//	}
	
	@GetMapping("/api/building")
	public List<BuildingDTO> getBuilding3( @RequestParam Map<String, Object> params,
											@RequestParam(name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> list = buildingService.findAll(params, typeCode);
		System.out.println("JDBC");
		return list;
	}
	
	@PostMapping("/api/building")
	public Object getBuilding2( @RequestBody BuildingDTO buildingDTO) {
		validate(buildingDTO);
		return buildingDTO;
	}
	
	@DeleteMapping("/api/building/{id}")
	public String deleteBuilding(@PathVariable("id") String id ) {
		return "Delete " + id;
	}
	
	public void validate( BuildingDTO buildingDTO) {
		if ( buildingDTO.getName().equals("") || buildingDTO.getName() == null || buildingDTO.getNumberOfBasement() == null) {
			throw new FieldRequiredException("name or numofbasement null");
		}
	}
}
