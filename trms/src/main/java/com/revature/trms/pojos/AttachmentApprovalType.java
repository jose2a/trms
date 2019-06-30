package com.revature.trms.pojos;

import java.util.HashMap;
import java.util.Map;

public enum AttachmentApprovalType {
	Direct_Supervisor_Approval(1), Department_Head_Approval(2);

	private int value;
	private static Map<Integer, AttachmentApprovalType> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	public String getText() {
		return String.join(" ", name().toString().split("_"));
	}

	private AttachmentApprovalType(int value) {
		this.value = value;
	}

	static {
		for (AttachmentApprovalType status : AttachmentApprovalType.values()) {
			map.put(status.value, status);
		}
	}

	public static AttachmentApprovalType valueOf(int value) {
		return (AttachmentApprovalType) map.get(value);
	}
}
