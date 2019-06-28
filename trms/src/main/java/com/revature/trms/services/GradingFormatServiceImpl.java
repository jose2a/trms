package com.revature.trms.services;

import java.util.List;

import com.revature.trms.daos.GradingFormatDAO;
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
		validateGradingFormat(gradingFormat);
		checkValidationResults();

		LogUtilities.trace("No validation errors - addGradingFormat");

		return gradingFormatDao.addGradingFormat(gradingFormat);
	}

	@Override
	public GradingFormat getGradingFormatById(Integer gradingFormatId) {
		if (gradingFormatId == null) {
			throw new IllegalArgumentException("GradingFormatId should not be null.");
		}

		return gradingFormatDao.getGradingFormatById(gradingFormatId);
	}

	@Override
	public List<GradingFormat> getAllGradingFormats() {
		return gradingFormatDao.getAllGradingFormats();
	}

}
