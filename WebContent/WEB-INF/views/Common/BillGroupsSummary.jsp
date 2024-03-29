<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
	<c:if test="${sessionScope.user.role=='MANAGER' }">
		<%@ include file="../Manager/header.jsp" %>
	</c:if>
	<c:if test="${sessionScope.user.role=='RECEPTIONIST' }">
		<%@ include file="../Receptionist/header.jsp" %>
	</c:if>
	<br>
	<div class="container mt-5">
		<div class="row ">
			<h3>Bill Groups Summary</h3>
			
			<div class="col-md-12">
				<br>
				<form class="form-group form-inline" 
				action="${contextPath}/common/bills/get-bill-group-summary">
					
					<label>Select Doctor:</label>
					<select name="doctor_id" class="form-control ml-2">
						<option></option>
						<c:forEach var="doctor" items="${ doctors}">
							<option value="${doctor.id}">${doctor.name }</option>
						</c:forEach>
					</select>
					
					<label class="ml-5">Select Billing Group</label>
					<select name="group_id" class='form-control ml-2' required="required">
						<option></option>
						<c:forEach var="group" items="${groups }">
							<option value='${group.id }'>${group.name}</option>
						</c:forEach>
					</select>
					
					<label class='ml-5'>Date:</label>
					<input type="date" name="date" class="form-control ml-2">
					
					<input type="submit" class="btn btn-primary ml-5" >
					
				</form>
				
				<c:if test="${not empty total}">
					<div class="alert alert-success">
						Rs. ${total}
					</div>
				</c:if>
					
						
			</div>
			
			<c:if test="${not empty items }">
				<div class='col-md-12'>
					<br><br>
					<c:set var="serial" value="${1}" />
					<table class="table">
						<tr>
							<th>Serial</th>
							<th>TID</th>
							<th>Patient Name</th>
							<th>Guardian</th>
							<th>Procedure</th>
							<th>Rate</th>
						</tr>
						<c:forEach var="item" items="${items }">
							<tr>
								<td>${serial}</td>
								<td>${item.tid }</td>
								<td>${item.patient}</td>
								<td>${item.guardian}</td>
								<td>${item.name }</td>
								<td>${item.rate}</td>
							</tr>
							<c:set var="serial" value="${1+serial}" />
						</c:forEach>
					</table>
				</div>
			</c:if>
			
		</div>
	</div>
	
	
	</body>
</html>