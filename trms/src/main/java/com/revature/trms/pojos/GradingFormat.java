package com.revature.trms.pojos;

public class GradingFormat {

	private int gradingFormatId;

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

	public int getGradingFormatId() {
		return gradingFormatId;
	}

	public void setGradingFormatId(int gradingFormatId) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromRange == null) ? 0 : fromRange.hashCode());
		result = prime * result + gradingFormatId;
		result = prime * result + ((toRange == null) ? 0 : toRange.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GradingFormat other = (GradingFormat) obj;
		if (fromRange == null) {
			if (other.fromRange != null)
				return false;
		} else if (!fromRange.equals(other.fromRange))
			return false;
		if (gradingFormatId != other.gradingFormatId)
			return false;
		if (toRange == null) {
			if (other.toRange != null)
				return false;
		} else if (!toRange.equals(other.toRange))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GradingFormat [gradingFormatId=" + gradingFormatId + ", fromRange=" + fromRange + ", toRange=" + toRange
				+ "]";
	}

}
