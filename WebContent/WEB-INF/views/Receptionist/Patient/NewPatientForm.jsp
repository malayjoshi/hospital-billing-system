<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		
		<div class='alert alert-danger'>
				Please as a good practice check if patient is already in the data-base.
			</div>
		<br>
		<h3>New Patient Form</h3>
		<br>
		
		<form action="${contextPath }/receptionist/save-patient" class='form-group'><br>
			
		<div class='row'>
			
			<div class='col-md-6'>
				
				<label>First Name</label>
				<input name="fname" required  class='form-control'/><br>
			</div>
			
			<div class='col-md-6'>
				<label>Last Name</label>
				<input name="lname" required  class='form-control'/><br>
			</div>
		
		
				
			<div class='col-md-6'>
				<label>Age</label>
				<input type='number' required class='form-control' name='age' min='0' max='120' name='age'/><br>
			</div>
			
			<div class='col-md-6'>
				<label>Sex</label>
				<select required class='form-control' name='sex'>
					<option></option>
					<c:forEach var="sex" items="${sexes}" >
						<option value="${sex }">${sex }</option>
					</c:forEach>
				</select>
			</div>
			
			<div class='col-md-6'>
				
				<label>Guardian</label>
				<input required class='form-control' name='guardian'/><br>
			</div>
			
			<div class='col-md-6'>
				<label>Address</label>
				<textarea class='form-control' required name='address' maxlength='250'></textarea>
			</div>
			<div class='col-md-6'>
				<label>Mobile</label>
				<input required class='form-control' name='mobile' minlength="10" maxlength="10"/><br>
			</div>	
			
				<div class='text-center mt-5 col-md-12'>
					<button class='btn btn-success'>Add Patient</button>
				</div>	
			
				<c:if test="${not empty id }">
						<div class='alert alert-success col-md-12 mt-5 text-center'>
							<p>Patient Added. PID: ${id}</p>
						</div>
					</c:if>
			
					
			</div>
			
					
		</div>
			
			</form>		
						
	</div>

	</body>
</html>