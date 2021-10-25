<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">

	<div class="row">
		<div class="alert alert-info col-md-12">Select date to get attendance for that month & year.</div>
		
		<form action="${contextPath}/payroll/get-attendance-for-payroll" class="col-md-12 form-group form-inline">
			
			<label>Choose Date:</label>
			<input type="date" style="margin-left:10px;" required name="date" class="form-control">
			<input type="submit" style="margin-left:20px;" class="btn btn-primary">
		</form>
	</div>
	
	<c:if test="${not empty attendances}">
	<div class="row form-group">
		
		<input type="number" id="dutyDays" class="form-control col-md-12" placeholder="Enter Number of duty days this month...">
		
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Employee Name</th>
					<th>Mobile</th>
					<th>Salary</th>
					<th>Full Duty</th>
					<th>Half Duty</th>
					<th>Final Amount</th>
				</tr>
				
			</thead>
			<tbody>
				<c:forEach var="attendance" items="${attendances}" >
					<tr>
						<td>${attendance.employee.name}</td>
						<td>${attendance.employee.mobile}</td>
						<td>
							<input type="number" onkeyup="updateTotal(${attendance.employee.id},${attendance.fulls},${attendance.halfs})"
							 id="salary_${attendance.employee.id}" class="form-control">
						</td>
						<td>${attendance.fulls}</td>
						<td>${attendance.halfs}</td>
						<td id="total_${attendance.employee.id}"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
	
	</c:if>
	
</div>

<script>
	function updateTotal(id,fulls,halfs){
		try {
		  var daysDuty=document.getElementById("dutyDays").value;
		  var salary=document.getElementById(`salary_\${id}`).value;
		  var perDay= salary/daysDuty ;
		  var total=perDay*( fulls + (halfs*0.5) );
		  
		  document.getElementById(`total_\${id}`).innerHTML=total.toFixed(2);
		}
		catch(err) {
		  window.alert(err);
		}
	}
</script>