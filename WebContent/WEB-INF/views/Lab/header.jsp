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
	    
	    <li class="nav-item active">
	      <a class="nav-link" href="${contextPath}/lab/view-tests">View Bills Last 24hrs</a>
	    </li>
	    <li class="nav-item active">
	      <a class="nav-link" href="${contextPath}/lab/add-test-page">Add Test</a>
	    </li>
	    <li class="nav-item active">
	      <a class="nav-link" href="${contextPath}/lab/add-report-page">Add Report</a>
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