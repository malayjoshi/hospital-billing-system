<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ include file="../../Common/GetPatientByDetails.jsp" %>
	<br>
	<div class='container'>
	<c:if test="${not empty patients }">
		
		<div class='row'>
			<div class='col-md-12'>
				<table class='table'>
					<tr>
						<th>Patient ID</th>
						<th>Full Name</th>
						<th>Mobile</th>
						<th>Guardian</th>
						<th>Sex</th>
						<th>Age</th>
						<th>Edit</th>
					</tr>
					
					<c:forEach var="patient" items="${ patients}">
						<tr>
							<td>${patient.id}</td>
							<td>${patient.fname } ${patient.lname }</td>
							<td>${patient.mobile }</td>
							<td>${patient.guardian }</td>
							<td>${patient.sex }</td>
							<td>${patient.age }</td>
							<td>
								<a class='btn btn-warning' href='${contextPath }/receptionist/edit-patient/${patient.id}'>Edit</a>
							</td>
						</tr>	
					</c:forEach>
					
				</table>
			</div>
		</div>
		
	</c:if>
		
	<c:if test='${not empty errorMessage }'>
		<div class='alert alert-danger'>
			${errorMessage}
		</div>
	</c:if>
		
						
	
	</div>
	</body>
</html>