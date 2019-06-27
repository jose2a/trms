package com.revature.trms.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.pojos.ApprovalStage;
import com.revature.trms.pojos.Attachment;
import com.revature.trms.pojos.Employee;

public class ModelMapperUtilities {

	public static void mapRsToEmployee(ResultSet rs, Employee employee) throws SQLException {
		employee.setEmployeeId(rs.getInt("employee_id"));
		employee.setUsername(rs.getString("username"));
		employee.setFirstName(rs.getString("first_name"));
		employee.setLastName(rs.getString("last_name"));
		employee.setSupervisorId(rs.getInt("supervisor_id"));
	}
	
	public static void mapRsToAttachment(ResultSet rs, Attachment attachment) throws SQLException {
		attachment.setAttachmentId(rs.getInt("attachment_id"));
		attachment.setFileName(rs.getString("file_name"));
		attachment.setDateSubmitted(rs.getDate("date_submitted").toLocalDate());
		attachment.setApprovalDoc(rs.getBoolean("approval_doc"));
		attachment.setFileContent(rs.getBytes("file_content"));
		attachment.setApprovalStage(ApprovalStage.valueOf(rs.getInt("approval_stage_id")));
		attachment.setEventId(rs.getInt("event_id"));
	}
}
