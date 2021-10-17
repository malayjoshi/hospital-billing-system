<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">

	<div class="row">
		<form action="${contextPath}/payroll/get-attendance" class="col-md-12 form-group form-inline">
			<label>Edit Attendance by Date:</label>
			<input type="date" style="margin-left:10px;" required name="date" class="form-control">
			<input type="submit" style="margin-left:20px;" class="btn btn-primary">
		</form>
	</div>
	
	<c:if test="${not empty attendances}">
		<div class="row">
			<div class="col-md-12">
				<input class="form-control" id="search" type="text" placeholder="Search Name"><br>
				<table class="table table-bordered">
					<thead>
						<th>Name</th>
						<th>Day & Night</th>
					</thead>
					<tbody id="table">
						<c:forEach var="attendance" items="${attendances}">
							<tr>
								<td>${ attendance.employee.name}</td>
								
								<td>
								<form class="form-group form-inline" action="${contextPath}/payroll/edit-attendance/${attendance.id}">
									<select class="form-control" required name="day">
									    <c:forEach var="preset" items="${presets}">
									    	<c:set var="selected" value=""/>
									    	
									    	<c:if test="${not empty attendance.day.id }">
										    	<c:if test="${preset.id == attendance.day.id }">
										    		<c:set var="selected" value="selected"/>
										    	</c:if>
										    </c:if>
									    	
									    	<option value="${preset.id}" ${selected }>${preset.type}</option>
									    </c:forEach>
									  </select>
									   
									  <select class="form-control" style="margin-left:10px;" required name="night">
									    <c:forEach var="preset" items="${presets}">
									    	<c:set var="selected" value=""/>
									    	
									    	<c:if test="${not empty attendance.night.id }">
										    	<c:if test="${preset.id == attendance.night.id }">
										    		<c:set var="selected" value="selected"/>
										    	</c:if>
										    </c:if>
									    	
									    	<option value="${preset.id}" ${selected }>${preset.type}</option>
									    </c:forEach>
									  </select>
									  <input type="submit" value="Change" style="margin-left:10px;" class="btn btn-warning">
								</form>
							</td>
								
								
							</tr>
							
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>

</div>

<script>
\$(document).ready(function(){
  \$("#search").on("keyup", function() {
    var value = \$(this).val().toLowerCase();
    \$("#table tr").filter(function() {
      \$(this).toggle(\$(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>