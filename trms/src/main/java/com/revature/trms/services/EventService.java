package com.revature.trms.services;

import java.util.List;

import com.revature.trms.exceptions.PojoValidationException;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Event;

public interface EventService {

	// Complete the Tuition Reimbursement Form
	public boolean completeTuitionReimbursementForm(Event event, String From, String To,
			List<Attachment> attachments) throws PojoValidationException;

	// Approve Tuition Reimbursement Form
	// Approve Reimbursement by Direct Supervisor
	public boolean approveTuitionReimbursementByDirectSupervisor(Integer eventId, Integer supervisorId);

	// Approve Reimbursement by Head Department
	public boolean approveTuitionReimbursementByHeadDepartment(Integer eventId, Integer supervisorId);

	// Approve Reimbursement by BenCo
	public boolean approveTuitionReimbursementByBenCo(Integer eventId, Integer supervisorId);

	// Deny Tuition Reimbursement Form
	// Deny Reimbursement by Direct Supervisor
	public boolean denyTuitionReimbursementByDirectSupervisor(Integer eventId, Integer supervisorId, String reason);

	// Deny Reimbursement by HeadDepartment
	public boolean denyTuitionReimbursementByHeadDepartment(Integer eventId, Integer supervisorId, String reason);

	// Deny Reimbursement by BenCo
	public boolean denyTuitionReimbursementByBenCo(Integer eventId, Integer supervisorId, String reason);

	public boolean requestAdditionalInformation(Integer fromEmployee, Integer toEmployee, String information);

	public boolean autoApproveEvents();

	public boolean changeReimbursementAmount(Integer eventId, double newAmount, String reason);

	public boolean uploadGradeOrPresentation(Integer eventId, Attachment attachment);

	public boolean confirmPassingGrade(Integer eventId);

	public boolean confirmSuccessfulPresentation(Integer eventId);

	public Event getEventById(Integer eventId);

	public List<Event> getEventsPendingOfDirectSupervisorApproval(Integer employeeId) throws PojoValidationException;

	public List<Event> getEventsPendingOfHeadDepartmentApproval(Integer employeeId) throws PojoValidationException;

	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval(Integer employeeId) throws PojoValidationException;

	public double getProjectedReimbursementForEvent(double cost, Integer eventTypeId);

	public double getAvailableReimbursementForEmployee(Integer employeeId);
}
