package com.revature.trms.pojos;

import java.util.HashMap;
import java.util.Map;

public enum EvaluationResult {
	Pending(1), Yes(2), No(3);

	private int value;
	private static Map<Integer, EvaluationResult> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	public String getText() {
		return String.join(" ", name().toString().split("_"));
	}

	private EvaluationResult(int value) {
		this.value = value;
	}

	static {
		for (EvaluationResult status : EvaluationResult.values()) {
			map.put(status.value, status);
		}
	}

	public static EvaluationResult valueOf(int value) {
		return (EvaluationResult) map.get(value);
	}
}
