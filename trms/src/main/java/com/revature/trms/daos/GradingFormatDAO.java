package com.revature.trms.daos;

import java.util.List;

import com.revature.trms.pojos.GradingFormat;

public interface GradingFormatDAO {

	public boolean addGradingFormat(GradingFormat gradingFormat);

	public GradingFormat getGradingFormatById(Integer gradingFormatId);

	public List<GradingFormat> getAllGradingFormats();
}
