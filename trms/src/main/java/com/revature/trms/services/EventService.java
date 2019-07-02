package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.IllegalParameterException;
import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Event;

public interface EventService {

	// Complete the Tuition Reimbursement Form
	public boolean completeTuitionReimbursementForm(Event event, String From, String To, List<Attachment> attachments)
			throws PojoValidationException, IllegalParameterException;

	// Approve Tuition Reimbursement Form
	// Approve Reimbursement by Direct Supervisor
	public boolean approveTuitionReimbursementByDirectSupervisor(Integer eventId, Integer supervisorId)
			throws NotFoundRecordException, IllegalParameterException;

	// Approve Reimbursement by Head Department
	public boolean approveTuitionReimbursementByHeadDepartment(Integer eventId)
			throws NotFoundRecordException, IllegalParameterException;

	// Approve Reimbursement by BenCo
	public boolean approveTuitionReimbursementByBenCo(Integer eventId)
			throws NotFoundRecordException, IllegalParameterException;

	// Deny Tuition Reimbursement Form
	// Deny Reimbursement by Direct Supervisor
	public boolean denyTuitionReimbursementByDirectSupervisor(Integer eventId, String reason)
			throws NotFoundRecordException, PreexistingRecordException, PojoValidationException,
			IllegalParameterException;

	// Deny Reimbursement by HeadDepartment
	public boolean denyTuitionReimbursementByHeadDepartment(Integer eventId, String reason)
			throws NotFoundRecordException, PreexistingRecordException, PojoValidationException,
			IllegalParameterException;

	// Deny Reimbursement by BenCo
	public boolean denyTuitionReimbursementByBenCo(Integer eventId, String reason) throws NotFoundRecordException,
			PreexistingRecordException, PojoValidationException, IllegalParameterException;

	public boolean requestInformationFromEmployee(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException,
			IllegalParameterException;

	public boolean requestInformationFromDirectSupervisor(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException,
			IllegalParameterException;

	public boolean requestInformationFromDepartmentHead(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException,
			IllegalParameterException;

	public void confirmSentOfInformationRequired(Integer eventId, Integer employeeId)
			throws NotFoundRecordException, IllegalParameterException, PojoValidationException;

	public void autoApproveEvents();

	public boolean changeReimbursementAmount(Integer eventId, double newAmount, String reason)
			throws PojoValidationException, NotFoundRecordException, PreexistingRecordException,
			IllegalParameterException;

	public boolean cancelReimbursementRequest(Integer eventId)
			throws NotFoundRecordException, IllegalParameterException;

	public boolean uploadFinalGrade(Integer eventId, String finalGrade, Attachment attachment)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException;

	public boolean uploadEventPresentation(Integer eventId, Attachment attachment)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException;

	public boolean confirmPassingGrade(Integer eventId, boolean successful)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException;

	public boolean confirmSuccessfulPresentation(Integer eventId, boolean successful)
			throws NotFoundRecordException, PojoValidationException, IllegalParameterException;

	public Event getEventById(Integer eventId) throws IllegalParameterException;
	
	public List<Event> getEventsByEmployeeId(Integer employeeId) throws IllegalParameterException;

	public List<Event> getEventsPendingOfDirectSupervisorApproval(Integer employeeId)
			throws IllegalParameterException;

	public List<Event> getEventsPendingOfHeadDepartmentApproval(Integer employeeId)
			throws IllegalParameterException;

	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval(Integer employeeId)
			throws IllegalParameterException;

	public double getProjectedReimbursementForEvent(double cost, Integer eventTypeId) throws IllegalParameterException;

	public double getAvailableReimbursementForEmployee(Integer employeeId);
}
