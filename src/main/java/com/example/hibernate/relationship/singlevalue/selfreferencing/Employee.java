package com.example.hibernate.relationship.singlevalue.selfreferencing;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;

@Entity
@Table(name = "self_ref_one_to_one_employee")
public class Employee extends BaseEntity {

	private static final long serialVersionUID = -2822345381730514595L;

	@Basic
	@Id
	@GeneratedValue(generator = "employee_id_gen")
	@GenericGenerator(name = "employee_id_gen", strategy = "uuid2")
	@Column(name = "employee_id")
	private UUID employeeId;

	@Basic
	@Column(name = "full_name")
	private String fullName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private Employee manager;
	
	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Employee(String fullName) {
		super();
		this.fullName = fullName;
	}

	public Employee() {
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(UUID employeeId) {
		this.employeeId = employeeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
