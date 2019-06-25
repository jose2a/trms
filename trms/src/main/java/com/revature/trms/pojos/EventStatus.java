package com.revature.trms.pojos;

import java.util.HashMap;
import java.util.Map;

public enum EventStatus {
	Pending(1), Approved(2), Denied(3);
	
	private int value;
	private static Map<Integer, EventStatus> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	public String getText() {
		return String.join(" ", name().toString().split("_"));
	}

	private EventStatus(int value) {
		this.value = value;
	}

	static {
		for (EventStatus status : EventStatus.values()) {
			map.put(status.value, status);
		}
	}

	public static EventStatus valueOf(int value) {
		return (EventStatus) map.get(value);
	}
}
