package com.javaweb.repository.entity;

import java.util.Date;
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
@Table(name = "user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "fullname")
	private String fullName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "status", nullable = false)
	private boolean status;

	@Column(name = "createdate")
	private Date createDate;

	@ManyToMany
	@JoinTable(name = "user_role", 
			  joinColumns = @JoinColumn(name = "userid"), 
			  inverseJoinColumns = @JoinColumn(name = "roleid"))
	private List<RoleEntity> roleEntities;
	
	@ManyToMany
	@JoinTable(name = "assignmentbuilding", 
			  joinColumns = @JoinColumn(name = "staffid"), 
			  inverseJoinColumns = @JoinColumn(name = "buildingid"))
	private List<BuildingEntity> buildingStaffManage;
	
	@ManyToMany
	@JoinTable(name = "assignmentcustomer", 
			  joinColumns = @JoinColumn(name = "staffid"), 
			  inverseJoinColumns = @JoinColumn(name = "customerid"))
	private List<CustomerEntity> cusstomerList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<RoleEntity> getRoleEntities() {
		return roleEntities;
	}

	public void setRoleEntities(List<RoleEntity> roleEntities) {
		this.roleEntities = roleEntities;
	}

	public List<BuildingEntity> getBuildingEntities() {
		return buildingStaffManage;
	}

	public void setBuildingEntities(List<BuildingEntity> buildingStaffManage) {
		this.buildingStaffManage = buildingStaffManage;
	}
	
	
}