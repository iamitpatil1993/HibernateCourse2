package com.example.hibernate.inheritance.embeddables;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Measurement implements Serializable {

	private static final long serialVersionUID = 4331339752045726255L;

	@Basic
	@Column(name = "unit")
	private String unit;

	@Basic
	@Column(name = "unit_symbol")
	private String unitSymbol;

	public Measurement() {
		super();
	}

	public Measurement(String unit, String unitSymbol) {
		super();
		this.unit = unit;
		this.unitSymbol = unitSymbol;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitSymbol() {
		return unitSymbol;
	}

	public void setUnitSymbol(String unitSymbol) {
		this.unitSymbol = unitSymbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((unitSymbol == null) ? 0 : unitSymbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Measurement other = (Measurement) obj;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (unitSymbol == null) {
			if (other.unitSymbol != null)
				return false;
		} else if (!unitSymbol.equals(other.unitSymbol))
			return false;
		return true;
	}

}
