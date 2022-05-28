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
	
	<div class="row">
		<div class="col-md-12">
			<h3>Bill By Procedures</h3>
		
			<div class="alert alert-info">Only enabled procedures will be shown.</div>
			
			<form class="form-group form-inline" action="${contextPath}/common/bills/get-bills-by-procedure">
			
				<label>Select Doctor:</label>
				<select name="doctor_id" class="form-control ml-2">
					<option></option>
					<c:forEach var="doctor" items="${ doctors}">
						<option value="${doctor.id}">${doctor.name }</option>
					</c:forEach>
				</select>
				
				<label class='ml-5'>Date:</label>
					<input type="date" name="date" class="form-control ml-2">
				
				<label class="ml-5">Select Procedure:</label>
				 <input list="procedures" name="procedure" class="form-control ml-2">
				  <datalist id="procedures" >
					  <c:forEach var="procedure" items="${procedures}">
					  	<option value="${procedure.procedure}">
					  </c:forEach>
				  </datalist>
				<input type="submit" class="btn btn-primary ml-5">
			</form>
				
		</div>
		
		<c:if test="${not empty items }">
			<div class="col-md-12">
				<c:set var="serial" value="${1}" />
				<table class="table table-borderless">
					<tr>
						<th>Serial</th>
						<th>TID</th>
						<th>Patient</th>
						<th>Procedure</th>
						<th>Rate</th>
						
					</tr>
					<c:forEach var="item" items="${items}">
						<tr>
							<td>${serial}</td>
							<td>${item.bill.tid}</td>
							<td>${item.bill.patient.fname} ${item.bill.patient.lname}</td>
							<td>${item.procedure.procedure }</td>
							<td>${item.rate}</td>
						</tr>
						<c:set var="serial" value="${1+serial}" />		
					</c:forEach>
				</table>
			</div>
		</c:if>
		
</div>