<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">

	<div class="row">
		<div class="col-md-12">
			<table class="table table-borderless">
				<tr>
					<th>Test</th>
					<th>Category</th>
				</tr>
				
				<c:forEach var="test" items="${tests }">
					<tr>
						<td>${test.procedure}</td>
						<td>
							<form class="form-group" method="post" action="${contextPath}/lab/add-category-to-test/${test.id}">
							
								<select class="form-control" required name="category">
								    <option></option>
								    <c:forEach var="cat" items="${categories}">
								    	<c:set var="selected" value=""/>
								    	
								    	<c:if test="${not empty test.category.id }">
									    	<c:if test="${cat.id == test.category.id }">
									    		<c:set var="selected" value="selected"/>
									    	
									    	</c:if>
									    </c:if>
								    	
								    	<option value="${cat.id}" ${selected }>${cat.name}</option>
								    </c:forEach>
								  </select>
								  <br>
								  <input type="submit" value="Change" class="btn btn-warning">
							</form>
						</td>
					</tr>
				</c:forEach>
				
			</table>
		</div>
	</div>

</div>