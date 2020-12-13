<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
	<%@ include file="../Manager/header.jsp" %>

	<br>
	<div class='container mt-5'>
		<div class='row'>
			<div class='col-md-12'>
				<c:if test="${type=='visit' }">
					<form class='form-group form-inline' 
					action="${contextPath}/common/bills/edit-visit-bill/${bill.tid}">
						<label>Change fees:</label>
						<input type="number" required name="fees" class='form-control' value="${bill.fees }">
						<input type="submit" class="btn btn-success">
					</form>
				</c:if>
				
				
				<c:if test="${not empty successMessage}">
					<div class='alert alert-success'>
						Success!
					</div>
				</c:if>
				
				<c:if test="${not empty errorMessage}">
					<div class='alert alert-danger'>Error!</div>
				</c:if>
			</div>
		</div>
	</div>
	</body>
</html>