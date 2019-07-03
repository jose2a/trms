package com.revature.trms.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.pojos.EvaluationResult;
import com.revature.trms.pojos.Event;
import com.revature.trms.pojos.EventStatus;
import com.revature.trms.utilities.ConnectionUtilities;
import com.revature.trms.utilities.LogUtilities;
import com.revature.trms.utilities.ModelMapperUtilities;

public class EventDAOImpl extends BaseDAO implements EventDAO {

	private String selectQuery = "SELECT event_id, date_of_event, time_of_event, location, description, cost,"
			+ " work_justification, work_time_miss, passing_grade, presentation_succ, projected_amt_reimbursed,"
			+ " accepted_amt_reimbursed, urgent, exceeds_aval_funds, event_type_id, grading_format_id, employee_id,"
			+ " required_presentation, grade_cutoff, final_grade, ds_event_status_id, hd_event_status_id, benco_event_status_id,"
			+ " canceled_by_employee, presentation_up, reimbursement_awarded FROM event";

	@Override
	public boolean addEvent(Event event) {
		LogUtilities.trace("addEvent.");

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null;

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "INSERT INTO event"
					+ "(date_of_event, time_of_event, location, description, cost, work_justification, work_time_miss,"
					+ " passing_grade, presentation_succ, projected_amt_reimbursed, accepted_amt_reimbursed, urgent,"
					+ " exceeds_aval_funds, event_type_id, grading_format_id, employee_id, required_presentation,"
					+ " grade_cutoff, ds_event_status_id, hd_event_status_id, benco_event_status_id, canceled_by_employee,"
					+ " final_grade, presentation_up, reimbursement_awarded)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			setEventToPreparedStatement(event, ps);

			if (ps.executeUpdate() != 0) {
				rs = ps.getGeneratedKeys();

				if (rs.next()) {
					LogUtilities.trace("Getting id for evnet");

					event.setEventId(rs.getInt(1));
				}

				LogUtilities.trace("Event inserted Id: " + event.getEventId().toString());
				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. addEvent. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return false;
	}

	@Override
	public boolean updateEvent(Event event) {
		LogUtilities.trace("updateEvent. " + event.getEventId().toString());

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "UPDATE event SET date_of_event=?, time_of_event=?, location=?, description=?, cost=?, work_justification=?, "
					+ "work_time_miss=?, passing_grade=?, presentation_succ=?, projected_amt_reimbursed=?, accepted_amt_reimbursed=?, "
					+ "urgent=?, exceeds_aval_funds=?, event_type_id=?, grading_format_id=?, employee_id=?, required_presentation=?, "
					+ "grade_cutoff=?, ds_event_status_id=?, hd_event_status_id=?, benco_event_status_id=?, "
					+ "canceled_by_employee=?, final_grade=?, presentation_up=?, reimbursement_awarded=? WHERE event_id=?";

			ps = conn.prepareStatement(sql);

			setEventToPreparedStatement(event, ps);
			ps.setInt(26, event.getEventId()); // event_id

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Event updated.");

				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. updateEvent. " + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public boolean deleteEvent(int eventId) {
		LogUtilities.trace("deleteEvent. " + eventId);

		PreparedStatement ps = null; // Creates the prepared statement from the query

		try (Connection conn = ConnectionUtilities.getConnection();) {
			String sql = "DELETE FROM event WHERE event_id=?";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, eventId);

			if (ps.executeUpdate() != 0) {
				LogUtilities.trace("Event deleted.");

				return true;
			}
		} catch (SQLException e) {
			LogUtilities.error("Error. deleteEvent. " + e.getMessage());
		} finally {
			closeResources(null, ps, null);
		}

		return false;
	}

	@Override
	public Event getEventById(int eventId) {
		LogUtilities.trace("getEventById. " + eventId);

		Event event = null;

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = selectQuery + " WHERE event_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, eventId);

			rs = ps.executeQuery();

			if (rs.next()) {
				event = new Event();
				ModelMapperUtilities.mapRsToEvent(rs, event);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEventById. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return event;
	}
	
	@Override
	public List<Event> getEventsByEmployeeId(Integer employeeId) {
		LogUtilities.trace("getEventsByEmployeeId.");

		List<Event> events = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = selectQuery + " WHERE employee_id = ?";

			LogUtilities.trace(sql);

			ps = conn.prepareStatement(sql);

			ps.setInt(1, employeeId);

			rs = ps.executeQuery();

			while (rs.next()) {
				Event event = new Event();
				ModelMapperUtilities.mapRsToEvent(rs, event);

				events.add(event);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEventsByEmployeeId. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return events;
	}

	@Override
	public List<Event> getEventsNotDeniedByEmployeeAndYear(Integer employeeId, int year) {
		LogUtilities.trace("getEventsNotDeniedByEmployeeAndYear.");

		List<Event> events = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = selectQuery + " WHERE employee_id = ? AND canceled_by_employee != true"
					+ " AND date_of_event BETWEEN ? AND ? AND reimbursement_awarded != ?";

			LogUtilities.trace(sql);

			ps = conn.prepareStatement(sql);

			LocalDate startDate = LocalDate.of(year, 1, 1); // Beginning of the year
			LocalDate endDate = LocalDate.of(year, 12, 31); // End of the year

			ps.setInt(1, employeeId);
			ps.setDate(2, Date.valueOf(startDate));
			ps.setDate(3, Date.valueOf(endDate));
			ps.setInt(4, EventStatus.Denied.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				Event event = new Event();
				ModelMapperUtilities.mapRsToEvent(rs, event);

				events.add(event);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEventsNotDeniedByEmployeeAndYear. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return events;
	}

	@Override
	public List<Event> getEventsPendingOfDirectSupervisorApproval() {
		LogUtilities.trace("getEventsPendingOfDirectSupervisorApproval.");

		List<Event> events = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = selectQuery + " WHERE ds_event_status_id=? OR (reimbursement_awarded=?"
					+ " AND required_presentation=true AND presentation_up=true AND presentation_succ=?)";

			LogUtilities.trace(sql);

			ps = conn.prepareStatement(sql);

			ps.setInt(1, EventStatus.Pending.getValue());
			ps.setInt(2, EventStatus.Pending.getValue());
			ps.setInt(3, EvaluationResult.Pending.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				Event event = new Event();
				ModelMapperUtilities.mapRsToEvent(rs, event);

				events.add(event);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error event pending of direct supervisor approval." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return events;
	}

	@Override
	public List<Event> getEventsPendingOfHeadDepartmentApproval() {
		LogUtilities.trace("getEventsPendingOfHeadDepartmentApproval.");

		List<Event> events = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = selectQuery
					+ " WHERE ds_event_status_id=? AND hd_event_status_id=? AND reimbursement_awarded=?";
			
			LogUtilities.trace(sql);

			ps = conn.prepareStatement(sql);

			ps.setInt(1, EventStatus.Approved.getValue());
			ps.setInt(2, EventStatus.Pending.getValue());
			ps.setInt(3, EventStatus.Pending.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				Event event = new Event();
				ModelMapperUtilities.mapRsToEvent(rs, event);

				events.add(event);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEventsPendingOfHeadDepartmentApproval. " + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return events;
	}

	@Override
	public List<Event> getEventsPendingOfBenefitsCoordinatorApproval() {
		LogUtilities.trace("getEventsPendingOfBenefitsCoordinatorApproval.");

		List<Event> events = new ArrayList<>();

		PreparedStatement ps = null; // Creates the prepared statement from the query
		ResultSet rs = null; // Queries the database

		try (Connection conn = ConnectionUtilities.getConnection();) {

			String sql = selectQuery
					+ " WHERE (ds_event_status_id=? AND hd_event_status_id=? AND benco_event_status_id=?)"
					+ " OR (reimbursement_awarded=? AND required_presentation=false AND passing_grade=?"
					+ " AND final_grade != '' AND canceled_by_employee != true)";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, EventStatus.Approved.getValue());
			ps.setInt(2, EventStatus.Approved.getValue());
			ps.setInt(3, EventStatus.Pending.getValue());
			ps.setInt(4, EventStatus.Pending.getValue());
			ps.setInt(5, EvaluationResult.Pending.getValue());

			rs = ps.executeQuery();

			while (rs.next()) {
				Event event = new Event();
				ModelMapperUtilities.mapRsToEvent(rs, event);

				events.add(event);
			}

		} catch (SQLException e) {
			LogUtilities.error("Error. getEventsPendingOfBenefitsCoordinatorApproval." + e.getMessage());
		} finally {
			closeResources(rs, ps, null);
		}

		return events;
	}

	private void setEventToPreparedStatement(Event event, PreparedStatement ps) throws SQLException {
		ps.setDate(1, event.getDateOfEvent()); // date_of_event
		ps.setTime(2, event.getTimeOfEvent()); // time_of_event
		ps.setString(3, event.getLocation()); // location
		ps.setString(4, event.getDescription()); // description
		ps.setDouble(5, event.getCost()); // cost
		ps.setString(6, event.getWorkJustification()); // work_justification
		ps.setInt(7, event.getWorkTimeMissed()); // work_time_miss
		ps.setInt(8, event.getPassingGradeProvided().getValue()); // passing grade (benco)
		ps.setInt(9, event.getSuccessfulPresentationProvided().getValue()); // presentations_succ (benco / DS)
		ps.setDouble(10, event.getProjectedAmountReimbused()); // projected_amt_reimbursed
		ps.setDouble(11, event.getAcceptedAmountReimbursed()); // accepted_amt_reimbursed
		ps.setBoolean(12, event.isUrgent()); // urgent
		ps.setBoolean(13, event.isExceedsAvaliableFunds()); // exceeds_aval_funds
		ps.setInt(14, event.getEventTypeId());// event_type_id
		ps.setInt(15, event.getGradingFormatId()); // grading_format_id
		ps.setInt(16, event.getEmployeeId()); // employee_id
		ps.setBoolean(17, event.isRequiredPresentation()); // required_presentation
		ps.setString(18, event.getGradeCutoff()); // grade_cutoff
		ps.setInt(19, event.getDsEventStatus().getValue()); // ds_event_status_id
		ps.setInt(20, event.getHdEventStatus().getValue()); // hd_event_status_id
		ps.setInt(21, event.getBencoEventStatus().getValue()); // benco_event_status_id
		ps.setBoolean(22, event.isCanceledByEmployee()); // canceled_by_employee
		ps.setString(23, event.getFinalGrade()); // final_grade
		ps.setBoolean(24, event.isPresentationUploaded()); // presentation_up
		ps.setInt(25, event.getReimbursementStatus().getValue()); // reimbursement_awarded
	}
}
