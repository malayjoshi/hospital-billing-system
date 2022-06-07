<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	
	<div class="row">
		<form class="col-md-12 form-group" action="${contextPath}/lab/find-report">
			<h5>Enter details to find reports</h5><br>
			<input type="text" required class="form-control" name="fname" placeholder="First Name">
			<br>
			<input type="text" required class="form-control" name="lname" placeholder="Last Name">
			<br>
			<input type="number" required class="form-control" name="age" placeholder="Age">
			<br>
			<input type="submit" value="Search" class="btn btn-primary">
		</form>
	</div>
	
	<c:if test="${not empty bills}">
		
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

</div>