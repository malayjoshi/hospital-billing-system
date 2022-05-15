<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

	<body>
		
		<div class='container mt-5'>
			<div class='alert alert-info text-center' style='margin-top:100px;'>
					If billing groups exists then it will be shown below.
				</div>
				<br><br>
			<div class="row">
				
				<br>
				<form class='col-md-12 form-group form-inline' method='post'
				 action="${contextPath }/manager/bills/add-bill-group">
					<input name="name" required class='form-control' placeholder='Group Name'>
					<input type='submit' class='btn btn-primary ml-5' >
				</form>
				
				<c:if test="${not empty groups }">
					<div class='col-md-12'>
					<br><br>
					<h3 class='text-center'>Billing groups</h3>
					<br><br>
					<table class='table'>
						<tr>
							<th>Group name</th>
							<th>Status</th>
							<th>Enable/Disabled</th>
						</tr>
						
						<c:forEach var="group" items="${groups }">
							<tr>
								<td>${group.name }</td>
								<td>
									<c:choose>
										<c:when test="${group.enabled}">enabled</c:when>
										<c:otherwise>disabled</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a class='btn btn-success' href="${contextPath }/manager/bills/bill-group/${group.id}/enable">Enable</a>
									<a class='btn btn-danger' href="${contextPath }/manager/bills/bill-group/${group.id}/disable">Disable</a>
								</td>
							</tr>			
						</c:forEach>
						
					
					</table>
					</div>
				</c:if>
			</div>
		</div>
	
	</body>
	
</html>