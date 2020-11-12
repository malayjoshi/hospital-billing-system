<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		<div class='row'>
			<h3>New Patient Form</h3><br>
			<div class='col-md-12'>
				<form action="${contextPath }/receptionist/" class='form-group'><br>
					<label>First Name</label>
					<input name="fname" required  class='form-control'/><br>
					<label>Last Name</label>
					<input name="lname" required  class='form-control'/><br>
					<label>Age</label>
					<input type='number' required class='form-control' name='age' min='0' max='120' name='age'/><br>
					
					<label>Sex</label>
					<select required class='form-control' name='sex'>
						<option></option>
						<c:forEach var="sex" items="${sexes}" >
							<option value="${sex }">${sex }</option>
						</c:forEach>
					</select>
					<br>
					<label>Guardian</label>
					<input required class='form-control' name='guardian'/><br>
					
					<label>Address</label>
					<textarea class='form-control' required name='address' maxlength='250'></textarea>
					<br>
					<label>Mobile</label>
					<input required class='form-control' name='mobile' /><br>
					
				
				</form>		
					
			</div>
		</div>
				
	</div>

	</body>
</html>