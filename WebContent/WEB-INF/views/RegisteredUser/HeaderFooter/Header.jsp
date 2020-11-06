<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>     

     
<!DOCTYPE html>
<html xml:lang="en">
<head>
<meta charset="UTF-8">
<title>Vforum</title>

	 <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	<!-- Popper JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	
	 
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 
</head>
<body>

	<nav class="navbar navbar-expand-sm bg-secondary navbar-dark fixed-top">
	  <ul class="navbar-nav">
	    
	    <li class="nav-item" >
	      <a class="nav-link active" href="${contextPath}/user/home-page/">Home</a>
	    </li>
	    
	    <li class="nav-item" >
	      <a class="nav-link active" href="${contextPath}/user/new-thread-form">Ask a question</a>
	    </li>
	    
	    <li class="nav-item" >
	      <a class="nav-link active" href="${contextPath}/user/all-posts">My Posts</a>
	    </li>
	    
	    <li class="nav-item" >
	      <a class="nav-link active" href="${contextPath}/user/all-thread">My Threads</a>
	    </li>
	    
	  </ul>
	  
	  <ul class="navbar-nav ml-auto">
	    <li class="nav-item px-3 active">
	  		<form class="form-inline" action="${contextPath}/user/search/all-posts" >
			    <input class="form-control mr-sm-2" name="keyword" type="text" placeholder="Search" required>
			     
			    <button class="btn btn-success" type="submit">Search</button>
			     
			  </form>
	    </li>
	    
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