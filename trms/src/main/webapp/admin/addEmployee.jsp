<!-- Header -->
<jsp:include page="../header.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Main content -->
<section class="content">

	<form action="AddEmployee" method="post">
		<div class="box box-primary">

			<div class="box-header with-border">
				<h3 class="box-title">Employee Info</h3>
			</div>
			<div class="box-body">
				<input type="hidden" name="employeeId" value="${param.employeeId}" />


				<div class="form-group">
					<label for="username">Username</label> <input type="text"
						class="form-control" name="username" value="${param.username }" />
				</div>

				<div class="form-group">
					<label for="firstName">First Name</label> <input type="text"
						class="form-control" name="firstName" value="${param.firstName }" />
				</div>

				<div class="form-group">
					<label for="lastName">Last Name</label> <input type="text"
						class="form-control" name="lastName" value="${param.lastName }" />
				</div>

				<div class="form-group">
					<label for="password">Password</label> <input type="password"
						class="form-control" name="password" value="${param.password }" />
				</div>

				<div class="form-group">
					<label>Supervisor:</label> <select name="supervisorid" class="form-control">
						<c:forEach var="supervisor" items="${supervisors}">
							<option value="${supervisor.employeeId}">${supervisor.firstName}  ${supervisor.lastName}</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-group">
					<label>Employee Roles</label> <select name="employeeTypes" multiple
						size="5" class="form-control">

						<c:forEach var="empType" items="${employeeTypeList}">
							<option value="${empType.value}">${empType }</option>
						</c:forEach>

					</select>
				</div>

			</div>

			<div class="box-footer">
				<a href="#" class="btn btn-default">Cancel</a>
				<button class="btn btn-primary" type="submit">Add Employee</button>
			</div>
		</div>
	</form>
</section>
<!-- /.content -->

<!-- Footer -->
<jsp:include page="../footer.jsp" />