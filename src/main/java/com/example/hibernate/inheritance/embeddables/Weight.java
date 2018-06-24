package com.example.hibernate.inheritance.embeddables;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "unit", column = @Column(name = "weight_unit")),
		@AttributeOverride(name = "unitSymbol", column = @Column(name = "weight_unit_symbol")) })
public class Weight extends Measurement {

	private static final long serialVersionUID = -235843010449309145L;

	@Column(name = "weight")
	private Long weight;

	public Long getWeight() {
		return weight;
	}

	public Weight(String unit, String unitSymbol, Long weight) {
		super(unit, unitSymbol);
		this.weight = weight;
	}
	
	public Weight() {
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weight other = (Weight) obj;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
}
