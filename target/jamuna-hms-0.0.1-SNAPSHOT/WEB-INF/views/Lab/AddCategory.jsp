<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	<div class="row">
		<form class="form-group col-md-12" method="post" action="${contextPath}/lab/add-category">
			<label>Enter Category:</label>
			<input type="text" name="category" required="required" class="form-control">
			<br>
			<input type="submit" class="btn btn-primary" value="Add">
		</form>
		
	</div>
	
	<br><br>
	<c:if test="${not empty categories}">
		
		<div class="row">
			<div class="col-md-12">
			<h4>Categories:</h4>
			<br>
				 <ul class="list-group">
				 	<c:forEach var="category" items="${categories }">
				 		<li class="list-group-item list-group-item-action">${category.name }</li>	
				 	</c:forEach>
				  
				</ul> 
			</div>
		</div>
	
	</c:if>

</div>