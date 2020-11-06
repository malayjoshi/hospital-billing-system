
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../HeaderFooter/Header.jsp" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		<br><br>
		
		<c:if test="${not empty ErrorMessage}">
			<div class="alert alert-danger">
				${errorMessage}
			</div>
		</c:if>
		
		<c:if test="${not empty successMessage}">
			<div class="alert alert-success">
				${successMessage}
			</div>
		</c:if>
		
		<h3>Home Page</h3>
				
				<c:choose>
         
			         <c:when test = "${totalPages==0}">
				     	<div class="row">
							
							<div class="col-md-12">
						         <div class="alert alert-info text-center">
					 				No threads are created right now.
					 			</div>
					 		</div>
					 	</div>
				     </c:when>
			         
			         <c:when test = "${totalPages > 0}">
			            <div class="row">
							
							<div class="col-md-12">
						
					            <ul class="pagination justify-content-center">
					             	<c:if test = "${currentPage > 1}">
								  		<li class="page-item"><a class="page-link" href="${contextPath}/user/home-page/${currentPage-1}">Prev</a></li>       
								    </c:if>
								    
								    
					            	<li class="page-item disabled">
					            		<p class="page-link">
					            			Page: ${currentPage } out of ${totalPages }
					            		</p>
					            	</li>
								    
								    <c:if test = "${totalPages != currentPage}">
								  		<li class="page-item"><a class="page-link" href="${contextPath}/user/home-page/${currentPage+1}">Next</a></li>       
								    </c:if>
								    
								</ul>
					            
			           		</div>
			           	</div>
			           	
			           	<c:forEach var="threadInfo" items="${listOfThreads}">
							<div class='row mt-5'>
								
								<div class='col-md-12'>
									
									<a href="${contextPath}/user/thread/${threadInfo.threadId}" style='text-decoration: none;color:black;'>
									
										<div class="card">
										  <div class="card-body">
										    <h4 class="card-title">${threadInfo.threadHeading }</h4>
										    <p class="card-text">Author: ${threadInfo.author}</p>
										    <p class="card-text float-right">
										    	Posts: <span class="badge badge-info ">${threadInfo.numberOfPosts}</span>	
										    </p>
										    
										  </div>
										  
										  <div class="card-footer">
										  	
										  	<c:if test="${threadInfo.closed}">
										  		<span class="badge badge-pill badge-danger float-left">Thread Closed</span>
										  	</c:if>
										  	
										  	<span class="badge badge-light float-right">Created:
										  		<fmt:formatDate type = "both" value = "${threadInfo.startDate}" />
										  	</span>
										  </div>
										  
										</div>
										
									</a>
										
								</div>								
							</div>		
						</c:forEach>
						           	
			           	
			           	
			           </div>
			            
			         </c:when>
			         
			      </c:choose>
				
			</div>
		
	
<%@ include file="../HeaderFooter/Footer.jsp" %>