<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<br>
	<div class='container mt-5'>
	
		<h3>Procedures Billing Page</h3>
		<br>	
		<div class='alert alert-info'>
			To search procedures, first enter the procedure name then click on search to fetch results.
		</div>
		<div class='row ' >
			<div class='col-md-6'>
				
				<form class='form-group' action="${contextPath}/receptionist/check-pid">
				
					<label>Enter PID</label>
					<input placeholder="PID" type="number" min="0"
					 name="pid" required class='form-control'/>
					 <br>
					<input type="submit" value="Check PID" class="btn btn-primary"/>
				</form>
				<c:if test="${not empty patientNotFound }">
					<div class='alert alert-warning'>
						Patient not found.
					</div>
				</c:if>
				
				<c:if test="${not empty pid }">
					
					<form class='form-group'  action="${contextPath }/receptionist/search-procedure/${pid}">
						<br><br>
						<label>Procedure name</label>
						<input placeholder="Procedure"  name="procedure" required class='form-control'/>
						<br>
						<input type='submit' class='btn btn-primary' value='Search'/>
					</form>
					
					<c:if test="${not empty procedures}">
						
						<c:forEach var="item" items="${procedures}">
							<a class="btn btn-outline-secondary btn-block" href="${contextPath}/receptionist/add-item/pid-${pid}/item-id-${item.id}">${item.procedure}</a>
						
						</c:forEach>
					
					</c:if>
					
						
				</c:if>
				
			</div>
			
			<c:if test="${not empty doctors}">
				<div class='col-md-6'>
					<div class='alert alert-warning mt-3'><strong>For PID: ${pid}</strong></div>
				
					<form class='form-group' action="${contextPath }/receptionist/save-procedures-bill/${pid}">
						<label>Select Doctor:</label>
						<select name="doctor_id" required class="form-control">
							<option></option>
							<c:forEach var="doctor" items="${ doctors}">
								<option value="${doctor.id }">${doctor.name }</option>
							</c:forEach>
						</select>
						
						<br><br>
						<table class='table' id='bill'>
							<tr>
								<th>Name</th>
								<th>Rate</th>
								<th>Delete</th>
							</tr>
							<c:if test="${not empty items }">
								<c:forEach var="item" items='${items }'>
									<tr>
										<td>${item.name }</td>
										<td>
											<input type="number" value="${item.rate}" name="rate_${item.id}" 
											required min="0" class="form-control"/>
										</td>
										<td>
											<a class="btn btn-danger" href="${contextPath}/receptionist/delete-item/pid-${pid}/item-id-${item.id}">Delete</a>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						
						<c:if test="${not empty items }">
							<div class='text-center'>
								<input type="submit" class='btn btn-success'>
							</div>
							
						</c:if>
						
					</form>
					
					
				</div>
			</c:if>
			
			<c:if test="${not empty tid }">
				<div class='alert alert-success col-md-12 text-center'>
					TID:${tid }
				</div>
			</c:if>
			
		</div >
		
	</div>
	
	</body>
</html>