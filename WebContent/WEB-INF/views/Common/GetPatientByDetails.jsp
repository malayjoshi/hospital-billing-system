<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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