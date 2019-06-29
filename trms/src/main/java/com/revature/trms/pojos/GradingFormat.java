package com.revature.trms.pojos;

public class GradingFormat {

	private Integer gradingFormatId;

	private String fromRange;
	private String toRange;

	public GradingFormat() {
		super();
	}

	public GradingFormat(String fromRange, String toRange) {
		super();
		this.fromRange = fromRange;
		this.toRange = toRange;
	}

	public Integer getGradingFormatId() {
		return gradingFormatId;
	}

	public void setGradingFormatId(Integer gradingFormatId) {
		this.gradingFormatId = gradingFormatId;
	}

	public String getFromRange() {
		return fromRange;
	}

	public void setFromRange(String fromRange) {
		this.fromRange = fromRange;
	}

	public String getToRange() {
		return toRange;
	}

	public void setToRange(String toRange) {
		this.toRange = toRange;
	}

	@Override
	public String toString() {
		return "GradingFormat [gradingFormatId=" + gradingFormatId + ", fromRange=" + fromRange + ", toRange=" + toRange
				+ "]";
	}

}
