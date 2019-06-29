package com.revature.trms.services;

import java.util.List;

import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.GradingFormat;

public interface EventService {

	// Complete the Tuition Reimbursement Form
	public boolean completeTuitionReimbursementForm(Event event, GradingFormat gradingFormat,
			List<Attachment> attachments);

	// Approve Tuition Reimbursement Form
	// Approve Reimbursement by Direct Supervisor
	public boolean approveTuitionReimbursementByDirectSupervisor(Integer eventId);

	// Approve Reimbursement by Head Department
	public boolean approveTuitionReimbursementByHeadDepartment(Integer eventId);

	// Approve Reimbursement by BenCo
	public boolean approveTuitionReimbursementByBenCo(Integer eventId);

	// Deny Tuition Reimbursement Form
	// Deny Reimbursement by Direct Supervisor
	public boolean denyTuitionReimbursementByDirectSupervisor(Integer eventId, String reason);

	// Deny Reimbursement by HeadDepartment
	public boolean denyTuitionReimbursementByHeadDepartmentr(Integer eventId, String reason);

	// Deny Reimbursement by BenCo
	public boolean denyTuitionReimbursementByBenCo(Integer eventId, String reason);

	public boolean requestAdditionalInformation(Integer fromEmployee, Integer toEmployee, String information);

	public boolean autoApproveEvents();

	public boolean changeReimbursementAmount(Integer eventId, double newAmount, String reason);

	public double getAvailableFundsForEmployee(Integer employeeId);

	public boolean uploadGradeOrPresentation(Integer eventId, Attachment attachment);

	public boolean confirmPassingGrade(Integer eventId);

	public boolean confirmSuccessfulPresentation(Integer eventId);

	public boolean awardAmount(Integer eventId);

	public Event getEventById(Integer eventId);

	public List<Event> getEventsPendingOfDirectSupervisorApproval();

	public List<Event> getEventsPendingOfHeadDepartmentApproval();

	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval();
}
