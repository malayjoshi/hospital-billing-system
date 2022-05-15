<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	
	<div class="row">
		
		<form action="${contextPath}/payroll/add-attendance/get-employees" class="col-md-12 form-group form-inline">
		<label>Add Attendance for Date:</label>
			<input type="date" style="margin-left:10px;" required name="date" class="form-control">
			<input type="submit" style="margin-left:20px;" class="btn btn-primary">
		</form>
	</div>
	
	<c:if test="${not empty employees }">
		<br>
		<div class="alert alert-info">Only <b>Enabled</b> employees will be shown.</div>
		<br>
		<form class="row form-group" method="post" action="${contextPath}/payroll/add-attendance-sheet">
			<input type="text" required value="${date}" hidden="true" name="date">
			
			
			<table class="table table-bordered col-md-12">
				<tr>
					<th>EmpID</th>
					<th>Name</th>
					<th>Day</th>
					<th>Night</th>
				</tr>
				
				<c:forEach var="employee" items="${employees }">
					<tr>
						<td>${employee.id}</td>
						<td>${employee.name}</td>
						<td>
							<select name="day_${employee.id}" required class="form-control">
								<c:forEach var="preset" items="${presets}">
									<option value="${preset.id}">${preset.type}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<select name="night_${employee.id}" required class="form-control">
								<c:forEach var="preset" items="${presets}">
									<option value="${preset.id}">${preset.type}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:forEach>
				
			</table>
			<br>
			<div class="text-center">
				<input type="submit" class="btn btn-success">
			</div>
			
		</form>
		
	</c:if>
	
	<c:if test="${not empty success }">
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-success">${success}</div>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty error }">
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-danger">${error}</div>
			</div>
		</div>
	</c:if>

</div>