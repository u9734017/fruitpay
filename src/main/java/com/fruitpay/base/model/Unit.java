package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Unit database table.
 * 
 */
@Entity
@NamedQuery(name="Unit.findAll", query="SELECT u FROM Unit u")
public class Unit extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="unit_id")
	private int unitId;

	@Column(name="unit_desc")
	private String unitDesc;

	@Column(name="unit_name")
	private String unitName;

	public Unit() {
	}

	public int getUnitId() {
		return this.unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitDesc() {
		return this.unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}