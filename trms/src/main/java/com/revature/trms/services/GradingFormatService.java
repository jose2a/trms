package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.GradingFormat;

public interface GradingFormatService {
	
	public boolean addGradingFormat(GradingFormat gradingFormat) throws PojoValidationException;

	public GradingFormat getGradingFormatById(Integer gradingFormatId);
	
	public List<GradingFormat> getAllGradingFormats();

}
