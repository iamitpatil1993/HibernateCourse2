package com.example.hibernate.immutable;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import com.example.hibernate.BaseEntity;

/**
 * Entity implementation class for Entity: ImmutableEntityDemo
 * Entity to test @Immutable annotation in hibernatate
 *
 */
@Entity
@Table(name="immutable_entity_demo")
@Immutable
public class ImmutableEntityDemo extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic
	@Id
	@GeneratedValue(generator = "immutable_entity_demo_id_gen")
	@GenericGenerator(name = "immutable_entity_demo_id_gen", strategy = "uuid2")
	private UUID id;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Basic
	@Column(name = "state")
	private String state;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ImmutableEntityDemo() {
		super();
	}
   
	public ImmutableEntityDemo(String state) {
		this.state = state;
	}
}
