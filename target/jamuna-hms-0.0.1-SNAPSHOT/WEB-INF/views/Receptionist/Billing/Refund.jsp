<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

	<br>
		<div class='container mt-5'>
			<h3>Refund Bills Page</h3><br>
			<div class='alert alert-info'>Only one refund per transaction is allowed.</div>
			<div class='row'>
				<div class='col-md-6'>
				<br>
					<h5>Visit Bill Refund</h5>
					<form class="form-group"
					 action="${contextPath}/receptionist/refund/visit">
						<label>TID:</label>
						<input required min="1" name="tid" type="number" class='form-control'>
						<br>
						<label>Amount:</label>
						<input required min="1" name="amount" type="number" class='form-control'>
						<br>
						<input type="submit" class='btn btn-primary'>
					</form>
				</div>
				
				<div class='col-md-6'>
				<br>
					<h5>Procedure Bill Refund</h5>
					<form class="form-group"
					 action="${contextPath}/receptionist/refund/procedure">
						<label>TID:</label>
						<input required min="1" name="tid" type="number" class='form-control'>
						<br>
						<label>Amount:</label>
						<input required min="1" name="amount" type="number" class='form-control'>
						<br>
						<input type="submit" class='btn btn-primary'>
					</form>
				</div>
				
				<div class='col-md-12'>
					<c:if test="${not empty successMessage }">
					<div class='alert alert-success text-center'>
						${successMessage }
					</div>
				</c:if>
				<c:if test="${not empty errorMessage }">
					<div class='alert alert-danger text-center'>
						${errorMessage }
					</div>
				</c:if>
				</div>
				
			</div>
		</div>
		
	</body>
</html>