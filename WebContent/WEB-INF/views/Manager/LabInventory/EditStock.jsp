<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<div class="container mt-5">
	
	<div class="row">
		
		<div class="col mt-5">
			<form class="form-group form-inline" action="${contextPath}/manager/lab-inventory/add-stock">
				<label>Select Procedure:</label>
				<select name="procedure" required class="form-control">
					<c:forEach var="proc" items="${procedures}">
						<option value="${ proc.id }">${proc.procedure}</option>
					</c:forEach>
				</select>
				<br>
				<label class="ml-3">Qty:</label>
				<input type="number" required min="1" name="qty" class="form-control">
				<br>
				<input type="submit" class="btn btn-primary ml-3">
			</form>
		</div>
		
		
		
	</div>
	
	<c:if test="${not empty batchId }">
			<div class="row">
				<div class="col alert alert-success">Batch ID- ${batchId}</div>
			</div>
	</c:if>
	
</div>