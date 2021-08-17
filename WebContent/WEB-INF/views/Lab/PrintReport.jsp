<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	
	<div class="row">
		<form class="col-md-12 form-group" action="${contextPath}/lab/print-report/check-tid">
			<label>Enter TID:</label>
			<input type="number" name="tid" class="form-control" required>
			<br>
			<input type="submit" class="btn btn-primary">
		</form>
		
		<c:if test="${not empty tid}">
			<div class="col-md-12 text-center">
				<a target="_blank" href="${contextPath}/lab/print-report/${tid}" class="btn btn-success">Print</a>
			</div>	
		</c:if>
		
	</div>
	
	
	

</div>