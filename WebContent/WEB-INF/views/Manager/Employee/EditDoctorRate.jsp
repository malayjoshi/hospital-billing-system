<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		<h3>Add Doctor Rates</h3>
		<br>
		<form class='form-group' action="${contextPath }/manager/staff-management/save-doctor-rate">
		 	<div class='row'>
				<div class='col-md-6'>
					<label>Select Visit Type </label>
					<select name='visitId' class='form-control ml-1' required>
						<option></option>
						<c:forEach var="visit" items="${ visitTypes}">
							<option value='${visit.id}'>${visit.name }</option>
						</c:forEach>
					</select>
				</div>
				
				<div class='col-md-6'>
					<label class='ml-2'>Select Doctor</label>
					<select name='empId' class='form-control ml-1' required>
						<option></option>
						<c:forEach var="doctor" items="${ doctors}">
							<option value='${doctor.id}'>${doctor.name }</option>
						</c:forEach>
					</select>
				</div>
				
				<div class='col-md-4 mt-5'>
					<label class='ml-2'>Start Time</label>
					<input type='time' name='startTime' required placeholder='Start Time' class='form-control ml-2'/>
				</div>
				
				<div class='col-md-4 mt-5'>
				<label class='ml-2'>End Time</label>
				<input type='time' name='endTime' required  class='form-control ml-1'/>
				</div>
				<div class='col-md-4 mt-5'>
					<label class='ml-2'>Rate</label>
					<input required name='rate' type='number' class='form-control ml-1' placeholder="Rate" min="${minRate }"/>
				</div>
				
				<div class='text-center col-md-12 mt-5'>
					
					<button class='btn btn-primary'>Add Rate</button>
				</div>
				
			
			</div>
		</form>
		
		<div class='row'>
			<div class='alert alert-info mx-auto mt-5'>
				All the entered rates will be displayed below.
			</div>
			<div class='col-md-12'>
			<c:if test="${not empty doctorRates}">
				<ul class="list-group">
					<c:forEach var='rate' items='${doctorRates }'>
							 
						<li class="list-group-item">
							<p class='float-left ml-5'>Doctor: ${rate.doctor}</p>
							<p class='float-left ml-5'>Rate: ${rate.rate}</p>
							<p class='float-left ml-5'>Visit: ${rate.visit}</p>
							<a href='${contextPath }/manager/staff-management/delete-doctor-rate/${rate.id}'
							 class='btn btn-danger float-right '>Delete</a>
							<p class='float-right mr-5'>end: ${rate.endTime}</p>
							<p class='float-right mr-5'>start: ${rate.startTime}</p>
							
						</li>
					 
					</c:forEach>
				</ul>
			</c:if>
			</div>
		</div>
		
		
		
	</div>
	