<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
	
	
	<div class="container" style="margin-top: 100px;">
	
			<h3>All Employee</h3>
			
			<c:choose>
         
			         <c:when test = "${info.totalPages==0}">
				     	<div class="row">
							
							<div class="col-md-12">
						         <div class="alert alert-info text-center">
					 				No Employees are added right now.
					 			</div>
					 		</div>
					 	</div>
				     </c:when>
			         
			         <c:when test = "${info.totalPages > 0}">
			            <div class="row">
							
							<div class="col-md-12">
						
					            <ul class="pagination justify-content-center">
					             	<c:if test = "${info.currentPage > 1}">
								  		<li class="page-item"><a class="page-link" 
								  		href="${contextPath}/manager/staff-management/show-all-employees/${info.currentPage-1}">Prev</a></li>       
								    </c:if>
								    
								    
					            	<li class="page-item disabled">
					            		<p class="page-link">
					            			Page: ${info.currentPage } out of ${info.totalPages }
					            		</p>
					            	</li>
								    
								    <c:if test = "${info.totalPages != info.currentPage}">
								  		<li class="page-item"><a class="page-link" 
								  		href="${contextPath}/manager/staff-management/show-all-employees/${info.currentPage+1}">Next</a></li>       
								    </c:if>
								    
								</ul>
					            
			           		</div>
			           	</div>
			           	
			           	<c:forEach var="employee" items="${employees}">
							<div class='row mt-5'>
								
								<div class='col-md-12'>
									
									
										<div class="card">
										  <div class="card-body">
										  	
										    <h5 class="card-text float-left ml-5">${employee.mobile }</h5>
										    <h5 class="card-text float-left ml-5">
										  		${employee.name}
										    </h5>
										    <h5 class="card-text float-left ml-5">
										  		${employee.role}
										    </h5>
										    <a href="${contextPath}/manager/staff-management/delete-employee/${info.currentPage}/${employee.id}"
										     class="card-text float-right btn btn-warning" style="color:white;">
										    	Delete
										    </a>
										    
										    
										  </div>
										  
										  
										</div>
										
								</div>								
							</div>		
						</c:forEach>
						           	
			           	
			           	
			           </div>
			            
			         </c:when>
			         
			      </c:choose>
			
		
				
	</div>
	
	