package com.revature.trms.pojos;

import java.util.HashMap;
import java.util.Map;

public enum ApprovalStage {
	Direct_Supervisor_Approval(1), Department_Head_Approval(2);
	
	private int value;
	private static Map<Integer, ApprovalStage> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	public String getText() {
		return String.join(" ", name().toString().split("_"));
	}

	private ApprovalStage(int value) {
		this.value = value;
	}

	static {
		for (ApprovalStage status : ApprovalStage.values()) {
			map.put(status.value, status);
		}
	}

	public static ApprovalStage valueOf(int value) {
		return (ApprovalStage) map.get(value);
	}
}
