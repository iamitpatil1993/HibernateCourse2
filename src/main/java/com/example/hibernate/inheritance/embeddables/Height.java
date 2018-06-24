package com.example.hibernate.inheritance.embeddables;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "unit", column = @Column(name = "height_unit")),
		@AttributeOverride(name = "unitSymbol", column = @Column(name = "height_unit_symbol")) })
public class Height extends Measurement {

	private static final long serialVersionUID = -2407802297230080252L;

	@Column(name = "height")
	private Long height;

	public Height(String unit, String unitSymbol, Long height) {
		super(unit, unitSymbol);
		this.height = height;
	}

	public Height() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((height == null) ? 0 : height.hashCode());
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
		Height other = (Height) obj;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		return true;
	}
}
