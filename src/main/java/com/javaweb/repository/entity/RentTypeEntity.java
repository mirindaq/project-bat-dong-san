package com.javaweb.repository.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "renttype")
public class RentTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "buildingrenttype", 
			  joinColumns = @JoinColumn(name = "renttypeid"), 
			  inverseJoinColumns = @JoinColumn(name = "buildingid"))
	private List<BuildingEntity> rentTypeBuildingEntities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BuildingEntity> getRentTypeBuildingEntities() {
		return rentTypeBuildingEntities;
	}

	public void setRentTypeBuildingEntities(List<BuildingEntity> rentTypeBuildingEntities) {
		this.rentTypeBuildingEntities = rentTypeBuildingEntities;
	}
	
	
}