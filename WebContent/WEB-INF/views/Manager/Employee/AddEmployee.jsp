<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		<div class='row'>
			<div class='col-md-12'>
				<h3>Add Employee</h3>
				<br>
				<form class='form-group justified-center' action='${contextPath }/manager/staff-management/add-employee' 
				method="POST" onsubmit='return checkPassword()'>
					<label>Full Name</label>
					<input name='name' required class='form-control'>
					<br>
					
					<label>Mobile</label>
					<input name='mobile' required class='form-control' minlength="10" maxlength="10">
					<br>
					
					<label>Password</label>
					<input name='password' id='password' type='password' required class='form-control' minlength='6' maxlength='20'>
					<br>
					<label>Confirm Password</label>
					<input id='confPassword' type='password' required class='form-control' minlength='6' maxlength='20'>
					<br>
					<label>Choose a role</label>
					<select  name="roleId"  class="form-control" required/>
			      	<option></option>
				      	<c:forEach var="role" items="${roles}">
				      		<option value="${role.id }">${role.role }</option>
				      	</c:forEach>
				      </select>
				      
				      <br>
					<div class='text-center'>
					<c:if test="${sessionScope.user.role == 'MANAGER'}">
						<input type='submit' class='btn btn-primary'/>
					</c:if>
					</div>
					<br>
					<div class='text-center'>
						<c:if test="${not empty errorMessage }">
							<div class='alert alert-danger'>
								${errorMessage }
							</div>
						</c:if>
						<c:if test="${not empty successMessage }">
							<div class='alert alert-success'>
								${successMessage }
							</div>
						</c:if>
					</div>
					
				</form>
				
			</div>
		</div>
				
	</div>
	
	<script>
		function checkPassword() {
	        var password = document.getElementById("password").value;
	        var confirmPassword = document.getElementById("confPassword").value;
	        if (password != confirmPassword) {
	            alert("Password and Confirm password does not match.");
	            return false;
	        }
	        return true;
	    }
	</script>
	