package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.GradingFormat;

public interface GradingFormatService {
	
	public GradingFormat getGradingFormatById(Integer gradingFormatId);

	public List<GradingFormat> getAllGradingFormat();

}
