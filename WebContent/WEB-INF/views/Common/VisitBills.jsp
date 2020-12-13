<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
	<c:if test="${sessionScope.user.role=='MANAGER' }">
		<%@ include file="../Manager/header.jsp" %>
	</c:if>
	<c:if test="${sessionScope.user.role=='RECEPTIONIST' }">
		<%@ include file="../Receptionist/header.jsp" %>
	</c:if>

<br>
	<div class='container mt-5'>
		<h3>Visit Bills Page</h3>
		<div class='row'>
			<form class='col-md-12 form-group form-inline mt-3'
			 action="${contextPath }/common/bills/get-bills/visit">
				<label>Select Doctor:</label>
				<select name="doctor_id" class="form-control ml-2">
					<option></option>
					<c:forEach var="doctor" items="${ doctors }">
						<option value="${doctor.id}">${doctor.name }</option>
					</c:forEach>
				</select>
				
				<label class="ml-5">Select Visit:</label>
				<select name="visit_id" class="form-control ml-2">
					<option></option>
					<c:forEach var="visit" items="${ visitTypes }">
						<option value="${visit.id}">${visit.visit }</option>
					</c:forEach>
				</select>
				
				<label class='ml-5'>Date:</label>
				<input type="date" name="date" class="form-control ml-2">
				
				<input type="submit" class="btn btn-primary ml-5" >
			</form>
		</div>
		
		<c:if test="${not empty bills }">
			
			
			
				<div class='col-md-12 alert alert-success text-center' >
					Total:<strong>Rs. ${total }</strong>		
				</div>
				
				
			 	<c:forEach var="bill" items="${bills }">
			 	
					<div class='row mt-3' >
					
						<div class='col-md-12'>
						
							<div class="card">
							
							  <div class="card-header">TID:${bill.tid}</div>
							  
							  <div class="card-body">
							  
							  	<p class='float-right'>Doctor: ${bill.doctor.name }</p>
							  	<p class='float-left'>Patient:${bill.patient.fname} ${bill.patient.lname}</p>
							  		
							  </div>	
							  
							  <div class='card-footer'>
							  	
							  	<c:if test="${not empty bill.refundBill }">
								
					 				<p class='badge badge-danger float-right'>
					 					Refunded (TID: ${bill.refundBill.tid })
					 				</p>
					 				
					 			</c:if>
							  	
							  	<p class='float-left'>Fees: ${bill.fees}</p>
							  	<c:if test="${sessionScope.user.role=='MANAGER' }">
							  		<a class='btn btn-warning float-left ml-5' target="_blank" href="${contextPath}/common/bills/edit-bill-page/visit/${bill.tid}">Edit</a>
							  	</c:if>
						  			 
					 			 	
							  </div>
							  
							</div>
							
						</div>
						
					</div>
				</c:forEach>
			  		
				
				
				</div>
				
			
			
		</c:if>
		
		
	</div>
	</body>
</html>