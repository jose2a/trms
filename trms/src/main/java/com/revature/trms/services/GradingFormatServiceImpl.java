package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.GradingFormatDAO;
import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.utilities.DAOUtilities;
import com.revature.trms.utilities.LogUtilities;

public class GradingFormatServiceImpl extends BaseService implements GradingFormatService {

	private GradingFormatDAO gradingFormatDao;

	public GradingFormatServiceImpl() {
		gradingFormatDao = DAOUtilities.getGradingFormatDAO();
	}

	@Override
	public boolean addGradingFormat(GradingFormat gradingFormat) throws PojoValidationException {
		LogUtilities.trace("addGradingFormat");
		
		pojoValException = PojoValidationException.getInstance();
		validateGradingFormat(gradingFormat, pojoValException);
		checkValidationResults(pojoValException);

		return gradingFormatDao.addGradingFormat(gradingFormat);
	}

	@Override
	public GradingFormat getGradingFormatById(Integer gradingFormatId) throws IllegalParameterException {
		LogUtilities.trace("getGradingFormatById");
		
		if (gradingFormatId == null) {
			throw new IllegalParameterException("getGradingFormatById - GradingFormatId should not be null.");
		}

		return gradingFormatDao.getGradingFormatById(gradingFormatId);
	}

	@Override
	public List<GradingFormat> getAllGradingFormats() {
		LogUtilities.trace("getAllGradingFormats");
		
		return gradingFormatDao.getAllGradingFormats();
	}

}
