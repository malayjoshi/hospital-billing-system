<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">

	<div class="row">
		<form class="col-md-12 form-group" action="${contextPath}/lab/edit-test/get-test">
			<select required name="test" class="form-control">
				<c:forEach var="test" items="${tests}">
					<option value="${test.id}">${test.name}</option>
				</c:forEach>
			</select>
			<br>
			<input type="submit" class="btn btn-primary" value="Get Parameters">
		</form>
	</div>
	<br><br>
	<c:if test="${not empty test}">
		<h3 style="text-align:center;">${test.name}</h3>
		<br>
		<div class="row">
			<table class="table table-borderless col-md-12">
				<tr>
					<th>Parameter</th>
					<th>Unit</th>
					<th>Lower Range</th>
					<th>Upper Range</th>
					
				</tr>
				<c:forEach var="parameter" items="${parameters}">
					<tr>
						<td>
							<form class="form-group input-group" method="post" action="${contextPath}/lab/edit-test/parameter/${test.id}/${parameter.id}">
								<div class="input-group-append">
									<input type="submit" class="btn btn-secondary" value="Save">
								</div>
								<input type="text" class="form-control" name="parameter" required value="${parameter.name}">
							</form>
						</td>
						
						<c:if test="${not empty parameter.unit }">
							<td>
								<form class="form-group input-group" method="post" action="${contextPath}/lab/edit-test/unit/${test.id}/${parameter.id}">
									<div class="input-group-append">
										<input type="submit" class="btn btn-secondary" value="Save">
									</div>
									<input type="text" class="form-control" name="unit" required value="${parameter.unit}">
								</form>
							</td>
							
							<td>
								<form class="form-group input-group" method="post" action="${contextPath}/lab/edit-test/low/${test.id}/${parameter.id}">
									<div class="input-group-append">
										<input type="submit" class="btn btn-secondary" value="Change">
									</div>
									<input type="number" class="form-control" name="low" step="0.01" required value="${parameter.lowerRange}">
								</form>
							</td>
							
							<td>
								<form class="form-group input-group" method="post" action="${contextPath}/lab/edit-test/high/${test.id}/${parameter.id}">
									<div class="input-group-append">
										<input type="submit" class="btn btn-secondary" value="Change">
									</div>
									<input type="number" class="form-control" name="high" step="0.01" required value="${parameter.upperRange}">
								</form>
							</td>
							
						</c:if>
						<c:if test="${empty parameter.unit }">
							<td></td>
							<td></td>
							<td></td>
						</c:if>
						
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>

</div>