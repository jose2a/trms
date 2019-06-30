package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.NotFoundRecordException;
import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.exceptions.PreexistingRecordException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Event;

public interface EventService {

	// Complete the Tuition Reimbursement Form
	public boolean completeTuitionReimbursementForm(Event event, String From, String To, List<Attachment> attachments)
			throws PojoValidationException;

	// Approve Tuition Reimbursement Form
	// Approve Reimbursement by Direct Supervisor
	public boolean approveTuitionReimbursementByDirectSupervisor(Integer eventId, Integer supervisorId)
			throws NotFoundRecordException;

	// Approve Reimbursement by Head Department
	public boolean approveTuitionReimbursementByHeadDepartment(Integer eventId) throws NotFoundRecordException;

	// Approve Reimbursement by BenCo
	public boolean approveTuitionReimbursementByBenCo(Integer eventId) throws NotFoundRecordException;

	// Deny Tuition Reimbursement Form
	// Deny Reimbursement by Direct Supervisor
	public boolean denyTuitionReimbursementByDirectSupervisor(Integer eventId, String reason)
			throws NotFoundRecordException, PreexistingRecordException, PojoValidationException;

	// Deny Reimbursement by HeadDepartment
	public boolean denyTuitionReimbursementByHeadDepartment(Integer eventId, String reason)
			throws NotFoundRecordException, PreexistingRecordException, PojoValidationException;

	// Deny Reimbursement by BenCo
	public boolean denyTuitionReimbursementByBenCo(Integer eventId, String reason)
			throws NotFoundRecordException, PreexistingRecordException, PojoValidationException;

	public boolean requestInformationFromEmployee(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException;

	public boolean requestInformationFromDirectSupervisor(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException;

	public boolean requestInformationFromDepartmentHead(Integer eventId, String information, Integer requiredBy)
			throws NotFoundRecordException, PojoValidationException, PreexistingRecordException;

	public boolean autoApproveEvents();

	public boolean changeReimbursementAmount(Integer eventId, double newAmount, String reason)
			throws PojoValidationException, NotFoundRecordException, PreexistingRecordException;

	public boolean cancelReimbursementRequest(Integer eventId) throws NotFoundRecordException;

	public boolean uploadFinalGrade(Integer eventId, String finalGrade, Attachment attachment)
			throws NotFoundRecordException, PojoValidationException;

	public boolean uploadEventPresentation(Integer eventId, Attachment attachment)
			throws NotFoundRecordException, PojoValidationException;

	public boolean confirmPassingGrade(Integer eventId) throws NotFoundRecordException, PojoValidationException;

	public boolean confirmSuccessfulPresentation(Integer eventId)
			throws NotFoundRecordException, PojoValidationException;

	public Event getEventById(Integer eventId);

	public List<Event> getEventsPendingOfDirectSupervisorApproval(Integer employeeId) throws PojoValidationException;

	public List<Event> getEventsPendingOfHeadDepartmentApproval(Integer employeeId) throws PojoValidationException;

	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval(Integer employeeId) throws PojoValidationException;

	public double getProjectedReimbursementForEvent(double cost, Integer eventTypeId);

	public double getAvailableReimbursementForEmployee(Integer employeeId);
}
