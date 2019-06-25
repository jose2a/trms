package com.revature.trms.pojos;

import java.util.HashMap;
import java.util.Map;

public enum EventStageStatus {
	Pending(1), Approved(2), Denied(3);
	
	private int value;
	private static Map<Integer, EventStageStatus> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	public String getText() {
		return String.join(" ", name().toString().split("_"));
	}

	private EventStageStatus(int value) {
		this.value = value;
	}

	static {
		for (EventStageStatus status : EventStageStatus.values()) {
			map.put(status.value, status);
		}
	}

	public static EventStageStatus valueOf(int value) {
		return (EventStageStatus) map.get(value);
	}
}
