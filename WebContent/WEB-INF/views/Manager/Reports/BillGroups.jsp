<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<br>

<div class="container mt-5">
	<h3>Report by bill group</h3>
	<div class="alert alert-info">Only procedures where frequency > 0 will be shown.</div>
	<form class="col-md-12 mt-3 form-group" action="${contextPath}/manager/reports/get-bill-group-report" >
		<div class="row">
			<div class="col-md-6">
				
				<label>Select Billing Group</label>
				<select name="group_id" class='form-control ml-2' required="required">
					<option></option>
					<c:forEach var="group" items="${groups }">
						<option value='${group.id }'>${group.name}</option>
					</c:forEach>
				</select>
				
				<label>Select Doctor</label>
				<select name="doctor_id" class='form-control ml-2' required="required">
					<option></option>
					<c:forEach var="doctor" items="${doctors }">
						<option value='${doctor.id }'>${doctor.name}</option>
					</c:forEach>
				</select>
				
				
			</div>
			
			<div class="col-md-6">
				
				<label>Select Report type</label>
				<select name="type" class='form-control ml-2' required="required">
					<option></option>
					<c:forEach var="option" items="${ summaryType }">
						<option value='${option}'>${option}</option>
					</c:forEach>
				</select>
				
							
				<label>Date:</label>
				<input type="date" name="date" class="form-control ml-2" required>
				
			</div>
			<div class="col-md-12 text-center mt-5">
				<input type="submit" class="btn btn-primary ml-5" >
			</div>
			
			
		</div>
	</form>
	
	<c:if test="${not empty rows }">
		<div class="row">
			<c:set var="count" value="${0}"></c:set>
			<c:set var="total" value="${0}"></c:set>
			
			<table class="col-md-12 table">
				<tr>
					<th>Procedure</th>
					<th>Frequency</th>
					<th>Total</th>
				</tr>
				
				<c:forEach var="item" items="${rows}">
					<tr>
						<td>${item.procedure.procedure }</td>
						<td>${item.count}</td>
					    <td>${item.total }</td>
					</tr>
					<c:set var="count" value="${count + item.count}"/>
					<c:set var="total" value="${total + item.total}"/>
				</c:forEach>		
			</table>
			
			<div class="alert alert-success text-center col-md-12" ><p>Total earning- ${ total } & total frequency- ${count }</p></div>
			
		</div>
		
	</c:if>

</div>