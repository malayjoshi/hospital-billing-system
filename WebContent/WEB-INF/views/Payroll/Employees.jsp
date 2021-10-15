<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	
	<div class="row">
		<div class="col-md-12">
			<form action="${contextPath}/payroll/add-employee" method="post" class="form-group form-inline">
				<label>Full Name:</label>
				<input type="text" style="margin-left: 10px;" name="name" required class="form-control">
				
				<label style="margin-left: 20px;">Mobile:</label>
				<input type='text' name='mobile' style="margin-left: 10px;" required="required" maxlength="10"
				      minlength="10" class="form-control">
				   
				<input type="submit" value="Add" class="btn btn-primary" style="margin-left: 20px;">
				
			</form>
		</div>
	</div>
	<br><br>
	<c:if test="${not empty employees}">
		<div class="row">
			<div class="col-md-12">
				<table class="table table-borderless">
					<tr>
						<th>Name</th>
						<th>Mobile</th>
						<th>Enable/Disable</th>
					</tr>
					<c:forEach var="employee" items="${employees}">
						<tr>
							<td>${employee.name}</td>
							<td>${employee.mobile}</td>
							<td>
								<c:choose>
									<c:when test="${employee.enabled}">
										<a class="btn btn-warning" href="${contextPath}/payroll/disable-employee/${employee.id}">Disable</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-success" href="${contextPath}/payroll/enable-employee/${employee.id}">Enable</a>
									</c:otherwise>
								</c:choose>
								
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</c:if>
	
	
	
</div>