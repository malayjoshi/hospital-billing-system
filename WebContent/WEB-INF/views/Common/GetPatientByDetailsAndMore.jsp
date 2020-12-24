<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="../Receptionist/header.jsp" %>
    
    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		
	<div class='alert alert-info'>
		Give patient's details then click on search.
	</div>	
	
	<div class='row'>
			<form class='form-group col-md-6' action='${contextPath }/receptionist/get-patient-by-id'>
				<input type='number' placeholder='PID' name="id" id='id' class='form-control' required/>
				<br>
				<div>
					<button class='btn btn-primary'>Get Patient</button>
				</div>
			</form>
			
			<form class='form-group col-md-6' action='${contextPath }/receptionist/get-patient-by-mobile'>		
				
				<input placeholder='Mobile' name="mobile" class='form-control ' required />
				<br>
				<div>
					<button class='btn btn-primary'>Get Patient</button>
				</div>
			</form>	
			
			<form class='form-group col-md-12 mt-5' action='${contextPath}/receptionist/get-patient-by-name'>		
				
						<input placeholder='First name' required name="fname" class='form-control'/>
						<br><input placeholder='Last name' required name="lname" class='form-control ' />
						
					<br>
					<div>
						<button class='btn btn-primary'>Get Patient</button>
					</div>
			</form>
				
	</div>
</div>

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
						<th>Address</th>
						<th>Sex</th>
						<th>Age</th>
						<th>Edit</th>
					</tr>
					
					<c:forEach var="patient" items="${ patients}">
						<tr>
							<td>${patient.id}</td>
							<td>${patient.fname } ${patient.lname }</td>
							<td>${patient.mobile }</td>
							<td>${patient.guardian}</td>
							<td>${patient.address }</td>
							<td>${patient.sex }</td>
							<td>${patient.age }</td>
							<td>
								
								<a class='btn btn-warning' href='${contextPath }/receptionist/edit-patient/${patient.id}'>Edit</a>
						
								<a class='btn btn-success' href='${contextPath }/receptionist/new-visit/${patient.id}'>New Visit</a>
								
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