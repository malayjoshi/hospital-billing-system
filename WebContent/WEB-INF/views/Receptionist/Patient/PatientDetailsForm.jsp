<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		
		<div class='alert alert-danger'>
				For adding patient - As a good practice check if patient is already in the data-base.
			</div>
		<br>
		<h3>${heading}</h3>
		<br>
		
		<c:if test="${ operation == 'add' }">
			<form action="${contextPath }/receptionist/save-patient"
		 method="post" class='form-group'>
		</c:if>
		<c:if test="${operation == 'edit' }">
			<form action="${contextPath }/receptionist/edit-patient-save/${patient.id}"
		 method="post" class='form-group'>
		</c:if>
		
		
		<br>
			
		<div class='row'>
			
			<div class='col-md-6'>
				
				<label>First Name</label>
				<input name="fname" required  class='form-control' value="${not empty patient.lname?patient.fname:'' }"/><br>
			</div>
			
			<div class='col-md-6'>
				<label>Last Name</label>
				<input name="lname" required  class='form-control' value="${not empty patient.lname?patient.lname:''}"/><br>
			</div>
				
			<div class='col-md-6'>
				<c:if test="${not empty patient}">
					<div class="alert alert-info">Enter patient's age as of ${patient.firstDateOfVisit}</div>
				</c:if>
				<label>Age</label>
				<input type='number' required class='form-control' name='age' min='0' max='120' name='age' value="${not empty patient.age?patient.age:''}"/><br>
			</div>
			
			<div class='col-md-6'>
				<label>Sex</label>
				<select required class='form-control' name='sex'>
					<option></option>
					<c:forEach var="sex" items="${sexes}" >
						<option value="${sex }" ${sex ==  patient.sex ? 'selected':'' }>${sex }</option>
					</c:forEach>
				</select>
			</div>
			
			<div class='col-md-6'>
				
				<label>Guardian</label>
				<input required class='form-control' name='guardian' value="${not empty patient.guardian?patient.guardian:''}"/><br>
			</div>
			
			<div class='col-md-6'>
				<label>Address</label>
				<textarea class='form-control' required name='address' maxlength='250'>${not empty patient.address?patient.address:''}</textarea>
			</div>
			<div class='col-md-6'>
				<label>Mobile</label>
				<input required class='form-control' name='mobile' minlength="10" maxlength="10" value="${not empty patient.mobile?patient.mobile:''}"/><br>
			</div>	
			
				<div class='text-center mt-5 col-md-12'>
					<button class='btn btn-success'> ${ operation=='add' ? 'Add Patient':'Save Changes' }</button>
				</div>	
			
				<c:if test="${not empty successMessage }">
						<div class='alert alert-success col-md-12 mt-5 text-center'>
							<p>${successMessage}</p>
						</div>
					</c:if>
			
					
			</div>
			
					
		</div>
			
			</form>		
						
	</div>

	</body>
</html>