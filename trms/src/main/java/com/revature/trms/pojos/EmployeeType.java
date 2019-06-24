package com.revature.trms.pojos;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeType {
	Associate(1), Manager(2), Head_Department(3), Benefits_Coordinator(4);

	private int value;
	private static Map<Integer, EmployeeType> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	private EmployeeType(int value) {
		this.value = value;
	}

	static {
		for (EmployeeType status : EmployeeType.values()) {
			map.put(status.value, status);
		}
	}

	public static EmployeeType valueOf(int value) {
		return (EmployeeType) map.get(value);
	}

}
