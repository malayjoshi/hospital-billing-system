<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<br>

<div class="container mt-5">
	<h3>Report by Consultations</h3>
	<div class="alert alert-info"></div>
	
		<div class="row">
			<div class="col-md-12">
				<form class=" mt-3 form-group form-inline" action="${contextPath}/manager/reports/get-visits-report" >			
					<label>Select Doctor</label>
					<select name="doctor_id" class='form-control ml-2' required="required">
						<option></option>
						<c:forEach var="doctor" items="${doctors }">
							<option value='${doctor.id }'>${doctor.name}</option>
						</c:forEach>
					</select>
					
					<label class='ml-4'>Select Report type</label>
					<select name="type" class='form-control ml-2' required="required">
						<option></option>
						<c:forEach var="option" items="${ summaryType }">
							<option value='${option}'>${option}</option>
						</c:forEach>
					</select>	
					
					<label class='ml-4'>Date:</label>
					<input type="date" name="date" class="form-control ml-2" required>
					<input type="submit" class="btn btn-primary ml-5" >		
				</form>
			</div>
			
			
			
		</div>
	
	
	<c:if test="${not empty report }">
		<div class="row mt-5">
			
			<div class="col-md-12">
				<table class="table">
					<tr>
						<td>Total OPDs: ${report.opds }</td>
						<td>Total Emergencies: ${report.emergencies }</td>
						<td>Total Follow-ups: ${report.followUps }</td>
					</tr>
				</table>
			</div>
		</div>
		
	</c:if>

</div>