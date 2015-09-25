package org.test.grizzly.entity;

import java.io.Serializable;
import java.util.Map;

public class TestEntity implements Serializable {

	private static final long serialVersionUID = -3504693306843856225L;

	private String a = null;

	private String b = null;

	private Integer ia = null;

	private Map<String, Integer> valueMap = null;

	public String getA() {
		return this.a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return this.b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public Integer getIa() {
		return this.ia;
	}

	public void setIa(Integer ia) {
		this.ia = ia;
	}

	public Map<String, Integer> getValueMap() {
		return this.valueMap;
	}

	public void setValueMap(Map<String, Integer> valueMap) {
		this.valueMap = valueMap;
	}

}
