package com.revature.trms.pojos;

public class GradingFormat {

	private int gradingFormatId;
	private String name;
	private boolean isPassingGrade;

	public GradingFormat() {
		super();
	}

	public GradingFormat(String name, boolean isPassingGrade) {
		super();
		this.name = name;
		this.isPassingGrade = isPassingGrade;
	}

	public int getGradingFormatId() {
		return gradingFormatId;
	}

	public void setGradingFormatId(int gradingFormatId) {
		this.gradingFormatId = gradingFormatId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPassingGrade() {
		return isPassingGrade;
	}

	public void setPassingGrade(boolean isPassingGrade) {
		this.isPassingGrade = isPassingGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gradingFormatId;
		result = prime * result + (isPassingGrade ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (gradingFormatId != other.gradingFormatId)
			return false;
		if (isPassingGrade != other.isPassingGrade)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GradingFormat [gradingFormatId=" + gradingFormatId + ", name=" + name + ", isPassingGrade="
				+ isPassingGrade + "]";
	}

}
