<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	
	<div class="row">
		<form class="col-md-12 form-group" action="${contextPath}/lab/add-report/get-tests">
			<label>Enter TID:</label>
			<input type="number" name="tid" class="form-control" required>
			<br>
			<input type="submit" class="btn btn-primary">
		</form>
	</div>
	<br><br>
	<c:if test="${not empty tests}">
		
		<form class="row form-group" action="${contextPath}/lab/add-report" method="post">
			<input type="number" name="tid" value="${tid}" hidden>
			
			<table class="col-md-12 table table-borderless table-hover">
				<c:forEach var="test" items="${tests}">
					<tr><th colspan="4"></th></tr>
					<tr>
						<th colspan="4">
							<h4 class="text-center">${test.procedure}</h4>
						</th>
						
					</tr>
					<c:forEach var="parameter" items="${test.parameters}">
						<tr>
							<td>${parameter.name}</td>
							<td>
								<c:if test="${not empty parameter.unit }">
									<input type="number" required step="0.01" class="form-control" name="value_${parameter.id}">
								</c:if>
								<c:if test="${empty parameter.unit }">
									<input type="text" required class="form-control" name="value_${parameter.id}">
								</c:if>
							</td>
							<td>
								<c:if test="${not empty parameter.unit}">
								${parameter.unit}
								</c:if>
							</td>
							<td>
								<c:if test="${not empty parameter.unit}">
									${parameter.lowerRange } - ${parameter.upperRange }
								</c:if>
							</td>
						</tr>
					</c:forEach>
				
				</c:forEach>
			</table>
			
			<input type="submit" class="btn btn-primary">
			
			
		</form>	
		
	</c:if>
	
	<br><br>
		<c:if test="${not empty successMessage }">
			<div class="text-center">
				<div class="alert alert-success">${successMessage}</div>
				<br>
				<a class="btn btn-success" target="_blank" href="${contextPath}/lab/print-report/${tid}">Print Report</a>
			</div>
		</c:if>
		<c:if test="${not empty errorMessage }">
			<div class="text-center">
				<div class="alert alert-danger">${errorMessage}</div>
			</div>
		</c:if>

</div>