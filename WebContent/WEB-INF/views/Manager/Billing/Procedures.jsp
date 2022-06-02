<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
	
	<br><br><br><br>
	<div class='container'>
		<div class='alert alert-info'>All the procedures will be shown below. Rates can also be zero.</div>
		
		<div class='row'>
			
			<form class='col-md-12 form-group form-inline' action="${contextPath}/manager/bills/add-procedure">
				<label>Select Billing Group</label>
				<select name="billGroup" class='form-control ml-1' required="required">
					<option></option>
					<c:forEach var="group" items="${groups }">
						<option value='${group.id }'>${group.name}</option>
					</c:forEach>
				</select>
				
				<label class='ml-3'>Procedure Name</label>
				<input required name="procedure" class='form-control ml-1'/>
				
				<label class='ml-3'>Rate</label>
				<input required type='number' min="0" name="rate" class='form-control ml-1'/>
				
				<input type="submit" class='btn btn-success ml-3'/>
				
			</form>
			
			<c:if test="${not empty procedures }">
			
				<table class="col-md-12 table mt-5">
					<tr>
						<th>Procedure</th>
						<th>Rate</th>
						<th>Enabled (true/false)</th>
						<th>Enable/Disable</th>
					</tr>
					<c:forEach var="procedure" items="${procedures }">
						<tr>
							<td>${procedure.name }</td>
							<td>
								<form class='form-group form-inline' action="${contextPath }/manager/bills/edit-procedure/${procedure.id}">
									<input type='number' required min='0' 
									class='form-control' value="${procedure.rate }" name="rate"/>
									<button class='btn btn-warning ml-3'>Change</button>
								</form>
							</td>
							<td>${procedure.enabled}</td>
							<td>
								<a class='btn btn-success' href="${contextPath}/manager/bills/procedure/${procedure.id}/enable">Enable</a>
								<a class='btn btn-danger' href="${contextPath}/manager/bills/procedure/${procedure.id}/disable">Disable</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
			
		</div>
		
	</div>

</body>

</html>