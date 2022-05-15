<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
		<br>
		<div class='container mt-5'>
		<h3>Print Bills & Slips</h3>
			<div class='row'>
				<div class='col-md-6'>
					<h5>Visit Bills</h5>
					<form class='form-group' action="${contextPath }/receptionist/check-tid/visit">
						<br>
						<label>TID:</label>
						<input required name="tid" class='form-control' type='number' min='1'/>
						<br>
						<input type="submit" class="btn btn-primary"  value='Check'>
					</form>
				</div>
				<div class='col-md-6'>
					<h5>Procedure Bills</h5>
					<form class='form-group' action="${contextPath }/receptionist/check-tid/procedure">
						<br>
						<label>TID:</label>
						<input required name="tid" class='form-control' type='number' min='1'/>
						<br>
						<input type="submit" class="btn btn-primary" value='Check'>
					</form>
				</div>
				
				<c:if test="${not empty tid}">
					<div class='col-md-12 text-center'>
						<br><br>
						<c:if test="${type=='visit' }">
							<a target="_blank" class="btn btn-success"
							 href="${contextPath}/receptionist/print-slip/${tid}">Print Slip</a>
							
							<a target="_blank" class="btn btn-success"
							 href="${contextPath}/receptionist/print-bill/visit/${tid}">Print Bill</a>
							
						</c:if>
						
						<c:if test="${type=='procedure'}">
							<a target="_blank" class="btn btn-success"
							 href="${contextPath}/receptionist/print-bill/procedure/${tid}">Print Bill</a>
						</c:if>
						
						
					</div>
				</c:if>
				
				<c:if test="${not empty errorMessage}">
					<div class='col-md-12 alert alert-danger'>
						${errorMessage}
					</div>
				</c:if>
				
				
			</div>
		</div>
	</body>
</html>