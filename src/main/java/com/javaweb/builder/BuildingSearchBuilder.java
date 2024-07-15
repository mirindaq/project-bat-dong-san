package com.javaweb.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

public class BuildingSearchBuilder {
	private String name;
	private Integer rentPriceFrom;
	private Integer rentPriceTo;
	private String street;
	private String ward;
	private Integer numberOfBasement;
	private String level;
	private String direction;
	private Integer areaFrom;
	private Integer areaTo;
	private String managerName;
	private String managerPhoneNumber;
	private Long staffId;
	private Long districtId;
	private List<String> typeCode = new ArrayList<String>();
	
	private BuildingSearchBuilder(Builder builder ) {
		this.name = builder.name;
		this.rentPriceFrom = builder.rentPriceFrom;
		this.rentPriceTo = builder.rentPriceTo;
		this.street = builder.street;
		this.ward = builder.ward;
		this.numberOfBasement = builder.numberOfBasement;
		this.level = builder.level;
		this.direction = builder.direction;
		this.areaFrom = builder.areaFrom;
		this.areaTo = builder.areaTo;
		this.managerName = builder.managerName;
		this.managerPhoneNumber = builder.managerPhoneNumber;
		this.staffId = builder.staffId;
		this.typeCode = builder.typeCode;
		this.districtId = builder.districtId;
	}

	public String getName() {
		return name;
	}

	public Integer getRentPriceFrom() {
		return rentPriceFrom;
	}

	public Integer getRentPriceTo() {
		return rentPriceTo;
	}

	public String getStreet() {
		return street;
	}

	public String getWard() {
		return ward;
	}

	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}

	public String getLevel() {
		return level;
	}

	public String getDirection() {
		return direction;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public Integer getAreaFrom() {
		return areaFrom;
	}

	public Integer getAreaTo() {
		return areaTo;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}

	public Long getStaffId() {
		return staffId;
	}

	public List<String> getTypeCode() {
		return typeCode;
	}

	public static class Builder {
		private String name;
		private Integer rentPriceFrom;
		private Integer rentPriceTo;
		private String street;
		private String ward;
		private Integer numberOfBasement;
		private String level;
		private String direction;
		private Integer areaFrom;
		private Integer areaTo;
		private String managerName;
		private String managerPhoneNumber;
		private Long staffId;
		private Long districtId;
		private List<String> typeCode = new ArrayList<String>();
		
		public BuildingSearchBuilder build() {
			return new BuildingSearchBuilder(this);
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setDistrictId(Long districtId) {
			this.districtId = districtId;
			return this;
		}

		public Builder setRentPriceFrom(Integer rentPriceFrom) {
			this.rentPriceFrom = rentPriceFrom;
			return this;
		}

		public Builder setRentPriceTo(Integer rentPriceTo) {
			this.rentPriceTo = rentPriceTo;
			return this;
		}

		public Builder setStreet(String street) {
			this.street = street;
			return this;
		}

		public Builder setWard(String ward) {
			this.ward = ward;
			return this;
		}

		public Builder setNumberOfBasement(Integer numberOfBasement) {
			this.numberOfBasement = numberOfBasement;
			return this;
		}

		public Builder setLevel(String level) {
			this.level = level;
			return this;
		}

		public Builder setDirection(String direction) {
			this.direction = direction;
			return this;
		}

		public Builder setAreaFrom(Integer areaFrom) {
			this.areaFrom = areaFrom;
			return this;
		}

		public Builder setAreaTo(Integer areaTo) {
			this.areaTo = areaTo;
			return this;
		}

		public Builder setManagerName(String managerName) {
			this.managerName = managerName;
			return this;
		}

		public Builder setManagerPhoneNumber(String managerPhoneNumber) {
			this.managerPhoneNumber = managerPhoneNumber;
			return this;
		}

		public Builder setStaffId(Long staffId) {
			this.staffId = staffId;
			return this;
		}

		public Builder setTypeCode(List<String> typeCode) {
			this.typeCode = typeCode;
			return this;
		}

	}

}
