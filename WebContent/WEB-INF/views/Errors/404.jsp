<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>     


<!DOCTYPE html>
<html  xml:lang="en">
<head>
<meta charset="UTF-8">
<title>Not found</title>
</head>
<body>
	<div style="text-align:center;">
		<h3>
		Sorry, the resource you were looking for isn't with us.
		</h3>
		<a href="${contextPath}/login">Go to login page.</a>
	</div>
	
</body>
</html>