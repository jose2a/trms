package com.revature.trms.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.pojos.ApprovalStage;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Employee;
import com.revature.trms.pojos.EventType;
import com.revature.trms.pojos.GradingFormat;

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
		gradingFormat.setGradingFormatId(rs.getInt("grading_type_id"));
		gradingFormat.setFromRange(rs.getString("from_range"));
		gradingFormat.setToRange(rs.getString("to_range"));
	}
}
