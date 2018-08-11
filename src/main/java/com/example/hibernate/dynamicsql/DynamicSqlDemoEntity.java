package com.example.hibernate.dynamicsql;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;

@Entity
@Table(name = "dynamic_sql_demo")
@org.hibernate.annotations.DynamicInsert
@org.hibernate.annotations.DynamicUpdate
public class DynamicSqlDemoEntity extends BaseEntity {

	private static final long serialVersionUID = -1334986935018417079L;

	@Id
	@Basic
	@GeneratedValue(generator = "dynamic_sql_demo_entity_id_gen")
	@GenericGenerator(name = "dynamic_sql_demo_entity_id_gen", strategy = "uuid2")
	@Column(name = "id")
	private UUID id;

	@Column(name = "col_1")
	private String col1;

	@Column(name = "col_2")
	private String col2;

	@Column(name = "col_3")
	private String col3;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

}
