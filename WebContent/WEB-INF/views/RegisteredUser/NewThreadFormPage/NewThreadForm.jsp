
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../HeaderFooter/Header.jsp" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<div class='container'  style="margin-top: 100px;">
		<div class='row'>
			<div class='col-md-12'>
	
				<form:form action="${contextPath}/user/create-new-thread" method="post" modelAttribute="NewThread">
	    
				       <div class="alert alert-warning">Maximum of 400 characters allowed.</div>
				       
				       <div class="form-group">
				       	<form:textarea path="post" required="required" cssClass="form-control" maxlength="400"/>
				       </div>
				       
				       <form:errors path="post" cssClass="alert alert-danger"/>
				     	
				     	<div class="text-center">
					       <button type="submit" class="btn btn-success">Submit</button>
					     </div>
					    	 
				</form:form>
					   
			   </div>
				
			   <div class='col-md-12 text-center'>
			   		<br>
			   		<c:if test = "${not empty successMessage}">
					         <div class="alert alert-success">
				 				${successMessage}
				 			</div>
					      </c:if>
					      
					      <c:if test = "${not empty errorMessage}">
					         <div class="alert alert-success">
				 				${errorMessage}
				 			</div>
					      </c:if>
			   </div>	     
				    
					
				     	
					
			</div>
		</div>
	

	 
	
	
<%@ include file="../HeaderFooter/Footer.jsp" %>