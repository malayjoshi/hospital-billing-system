<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	<div class="row">
		<div class="col-md-12 alert alert-info">
		  You are being shown lab bills for the last 24 hours and page will reload after 60 seconds.
		</div>
	</div>
	
	<div class="row">
		<ul class="col-md-12 list-group">
			<c:forEach var="bill" items="${bills}">
	      		<li class="list-group-item" >
	      			<table class="table table-borderless table-hover">
	      				<tr>
	      					<th>TID: ${bill.tid}</th>
	      					<th>Name: ${bill.patient.fname} ${bill.patient.lname}</th>
	      					<th>Age & Sex: ${bill.patient.age} ${bill.patient.sex}</th>
	      					<th>Doctor: ${bill.doctor.name}</th>
	      				</tr>
	      				<tr>
	      					<td colspan="4">
	      						<c:forEach var="item" items="${bill.billItems}">
				      				<span class="badge badge-pill badge-primary">${item.procedure.procedure}</span>
				      				
				      			</c:forEach>			
	      					</td>
	      				</tr>
	      				<tr>
	      					<td colspan="4" class="text-center">
	      						<a class="btn btn-success" href="${contextPath}/lab/add-report/get-tests?tid=${bill.tid}" target="_blank">Add Report</a>
	      					</td>
	      				</tr>
	      			</table>
	      		
	      	</c:forEach>
		  
		</ul> 
	</div>
	
</div>

<script>
window.setTimeout(function () {
	  window.location.reload();
	}, 60000);
</script>