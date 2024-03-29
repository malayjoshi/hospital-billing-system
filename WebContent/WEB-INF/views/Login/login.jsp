
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>     


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<title>JMH</title>
	 <!-- Latest compiled and minified CSS -->
	<link href="<c:url value="/resources/bootstrap.min.css" />" rel="stylesheet">
	<!-- jQuery library -->
	<script type="text/javascript" src="<c:url value="/resources/jquery.min.js" />"></script>
	<!-- Popper JS -->
	<script type="text/javascript" src="<c:url value="/resources/popper.min.js" />"></script>
	  
	<script type="text/javascript" src="<c:url value="/resources/bootstrap.min.js" />"></script>
</head>
<body>
<div class="container">
 
	<h3 class="text-center">Login</h3>
	
	<form action="${contextPath}/authenticate" class="form-group row" method="post">

 			<div class="col-md-12">
 			

				      <input type='text' name='mobile' placeholder="Mobile" required="required" maxlength="10"
				      minlength="10" class="form-control" />
				      <br>
							      				
	 			
	 			</div>

	 		<div class="col-md-12">
	 				
			      
			      <input type='password' maxlength="20" minlength="6" name="password"  placeholder="Password" class="form-control" required="required"/>
			      <br>
	 					
	 			</div>
	 			

	 		
	 		<div class="col-md-12">

			      
			      <select  name="roleId"   class="form-control" required="required"/>
			      	<option></option>
			      	<c:forEach var="role" items="${roles}">
			      		<option value="${role.id }">${role.role }</option>
			      	</c:forEach>
			      </select>
	 					
	 			</div>
	 			

	 		<div class="col-md-12">
	 			<div class="text-center">
	 				<input type="submit" value="Login" class="btn btn-primary"/>			
	 			</div>
	 			<br/>

	 			<c:if test = "${not empty  errorMessage}">
			         <div class="alert alert-danger">
		 				${errorMessage}
		 			</div>
			      </c:if>
			      
			    
		 		</div>

		
		</form>

	<div class="row">
		<div class="col">
			<div class="alert alert-info">
				<b>What's new: (V.13.08.23.1)</b>
				Option to add marital status. S-single,M-married,D-divorced,W-widowed,SP-separated
			</div>
		</div>
	</div>


			
 	</div>
	
</body>
</html>