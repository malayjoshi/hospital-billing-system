<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:if test="${sessionScope.user.role=='LAB TECH' }">
		<%@ include file="../Lab/header.jsp" %>
	</c:if>
	<c:if test="${sessionScope.user.role=='RECEPTIONIST' }">
		<%@ include file="../Receptionist/header.jsp" %>
	</c:if>

    <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		
	<div class='alert alert-info'>
		Give patient's details then click on search.
	</div>	
	
	<div class='row'>
			<form class='form-group col-md-6' action='${contextPath }/${sessionScope.user.role=='RECEPTIONIST'?'receptionist/get-patient-by-id':'lab/find-report-by-pid'}'>
				<input type='number' placeholder='PID' name="id" id='id' class='form-control' required/>
				<br>
				<div>
					<button class='btn btn-primary'>Get Patient</button>
				</div>
			</form>
			
			<form class='form-group col-md-6' action='${contextPath }/${sessionScope.user.role=='RECEPTIONIST'?'/receptionist/get-patient-by-mobile':'lab/find-report-by-mobile'}'>
				
				<input placeholder='Mobile' name="mobile" class='form-control ' required />
				<br>
				<div>
					<button class='btn btn-primary'>Get Patient</button>
				</div>
			</form>	
			
			<form class='form-group col-md-12 mt-5' action='${contextPath }/${sessionScope.user.role=='RECEPTIONIST'?'/receptionist/get-patient-by-name':'lab/find-report-by-name'}'>
				
						<input placeholder='First name' required name="fname" class='form-control'/>
						<br><input placeholder='Last name' required name="lname" class='form-control ' />
						
					<br>
					<div>
						<button class='btn btn-primary'>Get Patient</button>
					</div>
			</form>
				
	</div>
</div>

<br>
<div class='container'>
	<c:if test="${not empty patients && sessionScope.user.role=='RECEPTIONIST'}">
		
		<div class='row'>
			<div class='col-md-12'>
				<table class='table'>
					<tr>
						<th>Patient ID</th>
						<th>Full Name</th>
						<th>Mobile</th>
						<th>Guardian</th>
						<th>Address</th>
						<th>Sex</th>
						<th>Age</th>
						<th>Edit</th>
					</tr>
					
					<c:forEach var="patient" items="${ patients}">
						<tr>
							<td>${patient.id}</td>
							<td>${patient.fname } ${patient.lname }</td>
							<td>${patient.mobile }</td>
							<td>${patient.guardian}</td>
							<td>${patient.address }</td>
							<td>${patient.sex }</td>
							<td>${patient.age }</td>
							<td>
								<div class="dropdown">
								  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
								    Options
								  </button>
								  <div class="dropdown-menu">
										<a class='dropdown-item' href='${contextPath }/receptionist/edit-patient/${patient.id}'>Edit</a>
						
										<a class='dropdown-item' href='${contextPath }/receptionist/new-visit/${patient.id}'>New Visit</a>
										
										<a class='dropdown-item'  href='${contextPath }/common/bills/get-bills-pid/visit?pid=${patient.id}'>Get Visit Bills</a>
										
										<a class='dropdown-item'  href='${contextPath }/common/bills/get-bills-pid/procedures?pid=${patient.id}'>Get Procedure Bills</a>
								  
								  </div>
								</div>
										
							</td>
						</tr>	
					</c:forEach>
					
				</table>
			</div>
		</div>


		
	</c:if>


	<c:if test="${not empty bills && sessionScope.user.role=='LAB TECH'}">
		<div class="row">
			<div class="col-md-12">
				<br><br>
				<ul class="list-group">
					<c:forEach var="bill" items="${bills}">
						<li class="list-group-item">
							<table class="table table-borderless" >
								<tr>
									<th>TID: ${bill.tid }</th>
									<th>Name: ${bill.patientDTO.fname } ${bill.patientDTO.lname }</th>
									<th>Sex: ${bill.patientDTO.sex}</th>
									<th>Date: <fmt:formatDate value="${bill.billingDate}" pattern="dd-MM-yyyy " /></th>
									<th>Doctor: ${bill.doctor}</th>
								</tr>
								<tr>
									<td colspan="5">
										<c:forEach var="item" items="${bill.billItems}">

											<span class="badge badge-pill badge-primary">${item.name}</span>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td colspan="5" class="text-center">
										<a href="${contextPath}/lab/edit-report/get-tests?tid=${bill.tid}" class="btn btn-warning">Edit Report</a>
										<a href="${contextPath}/lab/print-report/${bill.tid}" class="btn btn-success" style="margin-left:10px;" target="_blank">Print Report</a>
									</td>

								</tr>
							</table>
						</li>
					</c:forEach>

				</ul>
			</div>
		</div>
	</c:if>


	<c:if test='${not empty errorMessage }'>
		<div class='alert alert-danger'>
				${errorMessage}
		</div>
	</c:if>
		
						
	
	</div>