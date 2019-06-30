package com.revature.trms.pojos;

import java.util.HashMap;
import java.util.Map;

public enum AttachmentDocType {
	Direct_Supervisor_Approval(1), Department_Head_Approval(2), Grade_Document(3), Presentation_Document(4);

	private int value;
	private static Map<Integer, AttachmentDocType> map = new HashMap<>();

	public int getValue() {
		return value;
	}

	public String getText() {
		return String.join(" ", name().toString().split("_"));
	}

	private AttachmentDocType(int value) {
		this.value = value;
	}

	static {
		for (AttachmentDocType status : AttachmentDocType.values()) {
			map.put(status.value, status);
		}
	}

	public static AttachmentDocType valueOf(int value) {
		return (AttachmentDocType) map.get(value);
	}
}
