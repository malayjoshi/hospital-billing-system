
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>     


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login form</title>

	 <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	<!-- Popper JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	
	 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 

</head>
<body>
<div class="container">
 
	<h3 class="text-center">Login</h3>
	
	<form action="login" method="post">
	
 	<div class="row">
 		
 		
 			<div class="col-md-12">
 			
 		
	 			<div class="form-group">
	 				
				      <input type='text' name='number' placeholder="Mobile" required="required" maxlength="10"
				      minlength="10" class="form-control" />
				      <br>
							      				
	 			
	 			</div>
	 			
	 		</div>
	 		<div class="col-md-12">
	 			<div class="form-group">
	 				
			      
			      <input type='password' maxlength="20" minlength="6" name="password"  placeholder="Password" class="form-control" required="required"/>
			      <br>
	 					
	 			</div>
	 			
	 			
	 		</div>
	 		
	 		<div class="col-md-12">
	 			<div class="form-group">
	 				
			      
			      <select  name="roleId"   class="form-control" required="required"/>
			      	<option></option>
			      	<c:forEach var="role" items="${roles}">
			      		<option value="${role.roleId }">${role.role }</option>
			      	</c:forEach>
			      </select>
	 					
	 			</div>
	 			
	 			
	 		</div>
	 		<div class="col-md-12">
	 			<div class="text-center">
	 				<input type="submit" value="Login" class="btn btn-primary"/>			
	 			</div>
	 			<br/>

	 			<c:if test = "${not empty  messageError}">
			         <div class="alert alert-danger">
		 				${messageError}
		 			</div>
			      </c:if>
			      
			    
		 		</div>
		 			
	 		</div>
	 			    		
    	
	
		</div>
		
		</form>
			
 	</div>
	
</body>
</html>