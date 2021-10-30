<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>     

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

     
<!DOCTYPE html>
<html xml:lang="en">
<head>
<meta charset="UTF-8">

	 <!-- Latest compiled and minified CSS -->
	<link href="<c:url value="/resources/bootstrap.min.css" />" rel="stylesheet">
	<!-- jQuery library -->
	<script type="text/javascript" src="<c:url value="/resources/jquery.min.js" />"></script>
	<!-- Popper JS -->
	<script type="text/javascript" src="<c:url value="/resources/popper.min.js" />"></script>
	  
	<script type="text/javascript" src="<c:url value="/resources/bootstrap.min.js" />"></script>
</head>
<body>

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
	  <ul class="navbar-nav">
	    
	    
	    <li class="nav-item dropdown" >
			  
	       <a class="nav-link active dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
		        Staff Management
		   </a>
			<div class=" dropdown-menu" >
			  <a class="dropdown-item" href="${contextPath}/manager/staff-management/add-employee-page">Add Employee</a>
			  <a class="dropdown-item" href="${contextPath}/manager/staff-management/show-all-employees">Employees List</a>
			 <!--  <a class='dropdown-item' href='${contextPath}/manager/staff-management/edit-visit-rules'>Edit Rules of Visit</a>
				 -->
			  <a class='dropdown-item' href='${contextPath}/manager/staff-management/edit-doctor-rate-page'>Edit Doctor Rates</a>
			</div>   
		 </li>
	    
	     <li class="nav-item dropdown" >
			  
	       <a class="nav-link active dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
		        Billing
		   </a>
			<div class=" dropdown-menu" >
			  <a class="dropdown-item" href="${contextPath}/common/bills/visit-bills-page">Patient Visit Bills</a>
			  <a class="dropdown-item" href="${contextPath}/common/bills/procedure-bills-page">Procedure Bills</a>
			  <a class="dropdown-item" href="${contextPath}/common/bills/bill-groups-summary-page">Bill Groups Summary</a>
			   <a class="dropdown-item" href="${contextPath}/manager/bills/bill-groups-page">Billing Groups</a>
			   <a class="dropdown-item" href="${contextPath}/manager/bills/procedures-page">Procedures</a>
			  <a class="dropdown-item" href="${contextPath}/common/bills/bills-by-procedure-page">Bills by Procedure</a>
			</div>   
		 </li>
	    
	  </ul>
	  
	  <ul class="navbar-nav ml-auto">
	    
	    
	    <li class="nav-item px-3 active" >
	      <a class="nav-link" href="">Hello, ${sessionScope.user.name}</a>
	    </li>
	   	
	   	
	   	<li class="nav-item px-3" >
	      <a href="${contextPath }/logout" class='btn active btn-secondary shadow nav-link' >
	      	Logout
	      </a>
	    </li>
	   	
	    
	    
	  </ul>
	  
	  
	</nav>