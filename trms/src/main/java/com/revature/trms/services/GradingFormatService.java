package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.GradingFormat;

public interface GradingFormatService {
	
	public boolean addGradingFormat(GradingFormat gradingFormat) throws PojoValidationException;

	public GradingFormat getGradingFormatById(Integer gradingFormatId) throws IllegalParameterException;
	
	public List<GradingFormat> getAllGradingFormats();

}
