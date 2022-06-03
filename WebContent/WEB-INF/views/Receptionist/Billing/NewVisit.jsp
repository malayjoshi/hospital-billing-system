<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<br>
	<div class='container mt-5'>
		
		<div class='row'>
			
			<div class='col-md-12'>
				
				<h3>New Visit Form</h3>
				<br><br>
				<div class='card'>
					<div class='card-header'>
						${patient.fname} ${patient.lname}
					</div>
					<div class='card-body'>
						<p class='float-left'>PID: ${patient.id}</p>
						<p class='float-right'>Guardian : ${patient.guardian }</p> 
						
					</div>
					
					
				</div>
			</div>
			<div class='col-md-12'>
				<c:choose>
					<c:when test="${not empty rate }">
						<form class='form-group' action="${contextPath}/receptionist/save-visit/${patient.id}">
					</c:when>
					<c:otherwise>
						<form class='form-group' action="${contextPath}/receptionist/get-visit-rate/${patient.id}">
					</c:otherwise>
				</c:choose>
				
				<br>
					<label>Select Doctor</label>
					<select  name="empId"  class="form-control" required/>
			      		<option></option>
				      	<c:forEach var="doctor" items="${doctors}">
				      		<option value="${doctor.id }" ${doctor.id == currentDoctor  ? 'selected':'' }>${doctor.name }</option>
				      	</c:forEach>
				      </select>
				      <br>
				      <label>Select Visit Type</label>
					  <select  name="visitId"  class="form-control" required/>
			      		<option></option>
				      	<c:forEach var="visit" items="${visitTypes}">
				      		<option value="${visit.id }" ${visit.id ==  currentVisit ? 'selected':'' }>${visit.name }</option>
				      	</c:forEach>
				      </select>
				      <br>
				      
				      <c:choose>
				      	<c:when test="${not empty rate}">
				      		<c:if test="${rate<0 }">
				      			<div class='alert alert-danger'>The combination of visit and doctor with current time isn't covered by manager.
				      			 Contact them to add this entry.</div>
				      		</c:if>
				      		<c:if test="${rate==0 }">
				      			<input name='rate' value='0' style='display:none;'/>
				      			<div class='alert alert-success'>No need for payment</div>
				      			<button class='btn btn-success'>Add bill</button>
				      		</c:if>
				      		
				      		<c:if test="${rate>0 }">
				      			<input name='rate' value='${rate}' style='display:none;'/>
				      			<div class='alert alert-success text-center'>Pay <strong>Rs. ${rate}</strong> </div>
				      			<button class='btn btn-success'>Add bill</button>
				      		</c:if>
				      		
					      		
				      	</c:when>
				      	<c:otherwise>
				      		<button class='btn btn-success'>Get Rates</button>	
				      	</c:otherwise>
				      </c:choose>
				      
				      
				</form>
			</div>
			
			
		</div>
				
	<c:if test='${not empty errorMessage }'>
		<div class='alert alert-danger'>
			${errorMessage}
		</div>
	</c:if>
	
	<c:if test='${not empty tid }'>
		<div class='alert alert-success text-center'>
			Please note <strong>TID:${tid}</strong>
		</div>
		<div class='text-center'>
			<a target="_blank" class="btn btn-success"
			 href="${contextPath}/receptionist/print-slip/${tid}">Print Slip</a>
			
			<a target="_blank" class="btn btn-success"
			 href="${contextPath}/receptionist/print-bill/visit/${tid}">Print Bill</a>
		</div>
	</c:if>	
						
	
	</div>
	</body>
</html>