<!-- Header -->
<jsp:include page="../header.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Main content -->
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header with-border">
					<div class="btn-toolbar">
						<div class="btn-group">
							<a href="${pageContext.request.contextPath}/admin/AddEmployee"
								class="btn btn-primary btn-sm"><i class="fa fa-plus"></i>
								Create Employee</a>
						</div>
					</div>
				</div>
				<div class="box-body">
					<table id="professors"
						class="table table-bordered table-hover table-striped dataTable"
						role="grid">
						<thead>
							<tr>
								<td>Id</td>
								<td>Username</td>
								<td>First Name</td>
								<td>Last Name</td>
								<td>Supervisor</td>
								<td></td>
								<td></td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="employee" items="${employeeList }">
								<tr>
									<td><c:out value="${employee.employeeId}" /></td>
									<td><c:out value="${employee.username}" /></td>
									<td><c:out value="${employee.firstName}" /></td>
									<td><c:out value="${employee.lastName}" /></td>
									<td><c:out value="${employee.supervisorId}" /></td>
									<td>
										<form
											action="${pageContext.request.contextPath}/admin/EditEmployee?id=${employee.employeeId}"
											method="get">
											<input type="hidden" name="employeeId" value="${employee.employeeId}">
											<button class="btn btn-primary">Update</button>
										</form>
									</td>
									<td>
										<form
											action="${pageContext.request.contextPath}/admin/DeleteEmployee?id=${employee.employeeId}"
											method="get">
											<input type="hidden" name="employeeId" value="${employee.employeeId}">
											<button class="btn btn-danger">Delete</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

</section>
<!-- /.content -->

<!-- Footer -->
<jsp:include page="../footer.jsp" />