package com.revature.trms.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.pojos.ApprovalStage;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EvaluationResult;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventStatus;
import com.revature.trms.pojos.EventType;
import com.revature.trms.pojos.GradingFormat;
import com.revature.trms.pojos.InformationRequired;
import com.revature.trms.pojos.ReasonDenied;
import com.revature.trms.pojos.ReasonExceeding;

public class ModelMapperUtilities {

	// Mapping ResultSet to Attachment
	public static void mapRsToAttachment(ResultSet rs, Attachment attachment) throws SQLException {
		attachment.setAttachmentId(rs.getInt("attachment_id"));
		attachment.setFileName(rs.getString("file_name"));
		attachment.setDateSubmitted(rs.getDate("date_submitted").toLocalDate());
		attachment.setApprovalDoc(rs.getBoolean("approval_doc"));
		attachment.setFileContent(rs.getBytes("file_content"));
		attachment.setApprovalStage(ApprovalStage.valueOf(rs.getInt("approval_stage_id")));
		attachment.setEventId(rs.getInt("event_id"));
	}

	// Mapping ResultSet to Employee
	public static void mapRsToEmployee(ResultSet rs, Employee employee) throws SQLException {
		employee.setEmployeeId(rs.getInt("employee_id"));
		employee.setUsername(rs.getString("username"));
		employee.setFirstName(rs.getString("first_name"));
		employee.setLastName(rs.getString("last_name"));
		employee.setSupervisorId(rs.getInt("supervisor_id"));
	}

	// Mapping ResultSet to EventType
	public static void mapRsToEventType(ResultSet rs, EventType eventType) throws SQLException {
		eventType.setEventTypeId(rs.getInt("event_type_id"));
		eventType.setName(rs.getString("name"));
		eventType.setReimburseCoverage(rs.getInt("reimburse_coverage"));
	}

	// Mapping ResultSet to GradingFormat
	public static void mapRsToGradingFormat(ResultSet rs, GradingFormat gradingFormat) throws SQLException {
		gradingFormat.setGradingFormatId(rs.getInt("grading_format_id"));
		gradingFormat.setFromRange(rs.getString("from_range"));
		gradingFormat.setToRange(rs.getString("to_range"));
	}

	// Mapping ResultSet to InformationRequired
	public static void mapRsToInformationRequired(ResultSet rs, InformationRequired informationRequired)
			throws SQLException {
		informationRequired.setEventId(rs.getInt("event_id"));
		informationRequired.setEmployeeId(rs.getInt("employee_id"));
		informationRequired.setInformation(rs.getString("information"));
		informationRequired.setProvided(rs.getBoolean("provided"));
		informationRequired.setRequiredBy(rs.getInt("required_by"));
	}

	// Mapping ResultSet to ReasonDenied
	public static void mapRsToReasonDenied(ResultSet rs, ReasonDenied reasonDenied) throws SQLException {
		reasonDenied.setEventId(rs.getInt("event_id"));
		reasonDenied.setReason(rs.getString("reason"));
	}

	// Mapping ResultSet to ReasonExceeding
	public static void mapRsToReasonExceeding(ResultSet rs, ReasonExceeding reasonExceeding) throws SQLException {
		reasonExceeding.setEventId(rs.getInt("event_id"));
		reasonExceeding.setReason(rs.getString("reason"));

	}

	// Mapping ResultSet to Event
	public static void mapRsToEvent(ResultSet rs, Event event) throws SQLException {
		event.setEventId(rs.getInt("event_id"));
		event.setDateOfEvent(rs.getDate("date_of_event").toLocalDate());
		event.setTimeOfEvent(rs.getTime("time_of_event").toLocalTime());
		event.setLocation(rs.getString("location"));
		event.setDescription(rs.getString("description"));
		event.setCost(rs.getDouble("cost"));
		event.setWorkJustification(rs.getString("work_justification"));
		event.setWorkTimeMissed(rs.getInt("work_time_miss"));
		event.setPassingGradeProvided(EvaluationResult.valueOf(rs.getInt("passing_grade")));
		event.setSuccessfulPresentationProvided(EvaluationResult.valueOf(rs.getInt("presentation_succ")));
		event.setProjectedAmountReimbused(rs.getDouble("projected_amt_reimbursed"));
		event.setAcceptedAmountReimbursed(rs.getDouble("accepted_amt_reimbursed"));
		event.setUrgent(rs.getBoolean("urgent"));
		event.setExceedsAvaliableFunds(rs.getBoolean("exceeds_aval_funds"));
		event.setEventTypeId(rs.getInt("event_type_id"));
		event.setGradingFormatId(rs.getInt("grading_format_id"));
		event.setEmployeeId(rs.getInt("employee_id"));
		event.setRequiredPresentation(rs.getBoolean("required_presentation"));
		event.setGradeCutoff(rs.getString("grade_cutoff"));
		event.setDsEventStatus(EventStatus.valueOf(rs.getInt("ds_event_status_id")));
		event.setHdEventStatus(EventStatus.valueOf(rs.getInt("hd_event_status_id")));
		event.setBencoEventStatus(EventStatus.valueOf(rs.getInt("benco_event_status_id")));
		event.setCanceledByEmployee(rs.getBoolean("canceled_by_employee"));
	}
}
