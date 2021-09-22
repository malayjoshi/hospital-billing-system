<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	
	<div class="row">
		<form class="col-md-12 form-group" action="${contextPath}/lab/edit-report/get-tests">
			<label>Enter TID:</label>
			<input type="number" name="tid" class="form-control" required>
			<br>
			<input type="submit" class="btn btn-primary">
		</form>
	</div>
	<br><br>
	<c:if test="${not empty tests}">
		
		<div class="row form-group">
			
			  
			
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
								
								<c:forEach var="testValue" items="${values}">
									
									<fmt:parseNumber var="id" value="${testValue.parameter.id}" />
									<fmt:parseNumber var="paraId" value="${parameter.id}" />
									
									<c:if test="${ id == paraId }">
										
										<form method="post" action="${contextPath}/lab/edit-report/change-${testValue.id}/tid-${tid}">
										
											<c:if test="${not empty parameter.unit }">
												<input type="number" step="0.01" class="form-control" name="value" value="${testValue.value}">
											</c:if>
											<c:if test="${empty parameter.unit }">
												<input type="text" class="form-control" name="value" value="${testValue.value}">
											</c:if>
										
											<br><br>
											<input type="submit" value="Change" class="btn btn-success">
										</form>
										
									</c:if>
										
										
								</c:forEach>
								  
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
			
			
			
		</div>	
		
	</c:if>
	
	<br><br>
		<c:if test="${not empty successMessage }">
			<div class="text-center">
				<div class="alert alert-success">${successMessage}</div>
			</div>
		</c:if>
		<c:if test="${not empty errorMessage }">
			<div class="text-center">
				<div class="alert alert-danger">${errorMessage}</div>
			</div>
		</c:if>

</div>