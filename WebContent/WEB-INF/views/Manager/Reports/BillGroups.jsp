<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<br>

<div class="container mt-5">
	<h3>Report by bill group</h3>
	<div class="row">
		<form class="col-md-12 form-group form-inline" action="${contextPath}/manager/reports/get-bill-group-report" >
			
			<label class="ml-5">Select Billing Group</label>
			<select name="group_id" class='form-control ml-2' required="required">
				<option></option>
				<c:forEach var="group" items="${groups }">
					<option value='${group.id }'>${group.name}</option>
				</c:forEach>
			</select>
			
			<label class='ml-5'>Date:</label>
			<input type="date" name="date" class="form-control ml-2">
			
			<input type="submit" class="btn btn-primary ml-5" >
		</form>
	</div>
	
	<c:if test="${not empty rows }">
		<div class="row">
			
			<table class="col-md-12 table">
				<tr>
					<th>Procedure</th>
					<th>Frequency</th>
					<th>Total</th>
				</tr>
				
				<c:forEach var="item" items="${rows}">
					<tr>
						<td>${item.procedure.procedure }</td>
						<td>${item.count}</td>
					    <td>${item.total }</td>
					</tr>
				</c:forEach>
				
			</table>
		
			
		</div>
		
	</c:if>

</div>