<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		<h3>Add Doctor Rates</h3>
		<br>
		<form class='form-group' action="${contextPath }/manager/staff-management/save-doctor-rate">
			<label>Select Visit Type </label>
			<select name='visitId' class='form-control ml-1' required>
				<option></option>
				<c:forEach var="visit" items="${ visitTypes}">
					<option value='${visit.id}'>${visit.visit }</option>
				</c:forEach>
			</select>
			
			<label class='ml-2'>Select Doctor</label>
			<select name='empId' class='form-control ml-1' required>
				<option></option>
				<c:forEach var="doctor" items="${ doctors}">
					<option value='${doctor.id}'>${doctor.name }</option>
				</c:forEach>
			</select>
			
			<label class='ml-2'>Start Time</label>
			<input type='time' name='startTime' required placeholder='Start Time' class='form-control ml-2'/>
			<label class='ml-2'>End Time</label>
			<input type='time' name='endTime' required  class='form-control ml-1'/>
			<input required name='rate' type='number' class='form-control ml-1' placeholder="Rate"/>
			<br><br>
			<div class='text-center'>
				<button class='btn btn-primary'>Add Rate</button>
			</div>
			
		</form>
			
	</div>
	