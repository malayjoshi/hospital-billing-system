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
		<h3>Procedure Bills Page</h3>
		<div class='row'>
			<form class='col-md-12 form-group form-inline mt-3'
			 action="${contextPath }/common/bills/get-bills/procedures">
				<label>Select Doctor:</label>
				<select name="doctor_id" class="form-control ml-2">
					<option></option>
					<c:forEach var="doctor" items="${ doctors}">
						<option value="${doctor.id}">${doctor.name }</option>
					</c:forEach>
				</select>
				
				<label class='ml-5'>Date:</label>
				<input type="date" name="date" class="form-control ml-2">
				
				<input type="submit" class="btn btn-primary ml-5" >
			</form>
		</div>
		
		<c:if test="${not empty bills }">
			
			<div class='alert alert-success text-center' >
				Total:<strong>Rs. ${total }</strong>		
			</div>
			
			<c:forEach var="bill" items="${bills }">
				
				<div class='row'>
					<div class="col-md-12" style="cursor: pointer;" onclick="showHide(${bill.tid})">
						<div class="card">
							<div class='card-header'>TID: ${bill.tid }</div>
							<div class='card-body'>
								<p class='float-left'>Patient: ${bill.patient.fname }</p>
								<p class='float-right'>Doctor: ${bill.doctor.name }</p>
								<br><br>
							  	<p >Guardian: ${bill.patient.guardian}</p>
								<br>
								<table id="items_${bill.tid}" class='table' style='display: none;'>
									<thead class="thead-light">
										<tr>
											<th>Bill Item</th>
											<th>Rate</th>
										</tr>
									</thead>
									<c:forEach var="item" items="${bill.billItems }">
										<tr>
											<td>${item.procedure.procedure }</td>
											<td>${item.rate }</td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<div class='card-footer'>
							
								<c:if test="${not empty bill.refundBill }">
								
					 				<p class='badge badge-danger float-right'>
					 					Refunded (TID: ${bill.refundBill.tid },  
					 					Date: <fmt:formatDate value="${bill.refundBill.date}" pattern="dd-MM-yyyy " />,
					 					Amount:${bill.refundBill.total } )
					 				</p>
					 				
					 			</c:if>
					 			
					 			<p class='float-left'>Bill Total: ${bill.total }</p>								
								
								<c:if test="${sessionScope.user.role=='MANAGER' }">
							  		<a class='btn btn-warning  ml-5'
							  		 target="_blank" href="${contextPath}/common/bills/edit-bill-page/procedure/${bill.tid}">Edit</a>
							  	</c:if>
							</div>
						</div>
					
					</div>
				</div>
				
			</c:forEach>
			
			
			
		</c:if>
		
		
	</div>
	
	<script>
		function showHide(tid){
			$('#items_'+tid).toggle();
		}
	</script>
	
	</body>
</html>