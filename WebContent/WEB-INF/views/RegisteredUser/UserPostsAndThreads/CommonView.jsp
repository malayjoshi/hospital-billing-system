<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../HeaderFooter/Header.jsp" %>


<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

	<div class="container" style="margin-top: 100px;">
		<br><br>
		
		
		<h3>My ${view}</h3>
				
				<c:choose>
         
			         <c:when test = "${totalPages==0}">
				     	<div class="row">
							
							<div class="col-md-12">
						         <br>
						         <div class="alert alert-info text-center">
					 				<c:if test="${view=='posts' }">
					 					No Posts are created right now.
					 				</c:if>
					 				
					 				<c:if test="${view=='threads' }">
					 					No Threads are created right now.
					 				</c:if>
					 				
					 			</div>
					 		</div>
					 	</div>
				     </c:when>
			         
			         <c:when test = "${totalPages > 0}">
			            <div class="row">
							
							<div class="col-md-12">
						
					            <ul class="pagination justify-content-center">
					             	<c:if test = "${currentPage > 1}">
								  		<li class="page-item"><a class="page-link" href="${contextPath}/user/all-${view}/${currentPage-1}">Prev</a></li>       
								    </c:if>
								    
								    
					            	<li class="page-item disabled">
					            		<p class="page-link">
					            			Page: ${currentPage } out of ${totalPages }
					            		</p>
					            	</li>
								    
								    <c:if test = "${totalPages != currentPage}">
								  		<li class="page-item"><a class="page-link" href="${contextPath}/user/all-${view}/${currentPage+1}">Next</a></li>       
								    </c:if>
								    
								</ul>
					            
			           		</div>
			           	</div>
			           	
			           	<c:choose>
			           		<c:when test="${view=='posts' }">
			           		
			           			<c:forEach var="post" items="${postList}">
									
									<div class='row mt-5'>
										
										<div class='col-md-12'>
											
											
												<div class="card">
												  <div class="card-body" id='existing_post_${post.id}'>
												  	<a href="${contextPath}/redirect-to-thread/post-${post.id}" style='text-decoration: none;color:black;'>
											
													    <h4 class="card-title">${post.text }</h4>
												    	<p class="card-text">Author: ${post.author}</p>
													</a>    
											  	  </div>
												  
											      <div class="card-body" style='display:none;' id='post_update_form_${post.id}'>
											    	
										    		<form action="${contextPath}/user/${currentPage}/update-post-${post.id}" method="post">
    
													       <div class="alert alert-warning">Maximum of 400 characters allowed.</div>
													       
													       <div class="form-group">
													       	<textarea name="post" required="required" class="form-control" maxlength="400">${post.text}</textarea>
													       </div>
													       
													     	<div class="text-center">
														       <button type="submit" class="btn btn-success">Submit</button>
														     </div>
														    	 
													</form>
												  </div>
												
												  
												  <div class="card-footer">
												  	<span class='float-left' data-toggle="tooltip" style='cursor:pointer;' title="Edit" >
												  		<img src="https://img.icons8.com/metro/20/000000/edit.png" onclick='showHideEditForm(${post.id})' alt="edit"/>
												  	</span>
												  	
												  	<a class='float-left ml-5' data-toggle="tooltip" style='cursor:pointer;' title="Delete" 
												  	href="${contextPath}/user/${currentPage}/delete/post-${post.id}">
												  		<img src="https://img.icons8.com/material-sharp/24/000000/delete-forever.png" alt="delete"/>
												  	</a>
												  	
												  	<span class="badge badge-light float-right">Created:
												  		<fmt:formatDate type = "both" value = "${post.date}" />
												  	</span>
												  </div>
												  
												</div>
												
												
										</div>								
									</div>		
								</c:forEach>
								
							</c:when>
			           		
			           		<c:otherwise>
			           			<c:forEach var="threadInfo" items="${threadList}">
									<div class='row mt-5'>
										
										<div class='col-md-12'>
											
											<a href="${contextPath}/user/thread/${threadInfo.threadId}" style='text-decoration: none;color:black;'>
											
												<div class="card">
												  <div class="card-body">
												    <h4 class="card-title">${threadInfo.threadHeading }</h4>
												    <p class="card-text float-left">Author: ${threadInfo.author}</p>
												    <p class="card-text float-right">
												    	Posts: <span class="badge badge-info ">${threadInfo.numberOfPosts}</span>	
												    </p>
												    
												  </div>
												  
												  <div class="card-footer">
												  	
												  	<c:if test="${threadInfo.closed}">
												  		
													  		<a href="${contextPath}/user/${currentPage}/0/thread-${threadInfo.threadId}">
													  			<span class='float-left badge badge-pill  badge-success'>
													  			Open this thread
												  				</span>
													  		</a>
													  			
												  		
												  	</c:if>
												  	
												  	<c:if test="${!threadInfo.closed}">
												  		
													  		<a href="${contextPath}/user/${currentPage}/1/thread-${threadInfo.threadId}">
													  			<span class='float-left badge badge-pill  badge-danger'>Close this thread</span>
													  		</a>
												  		
												  		
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
			           		
			           		</c:otherwise>
			           		
			           		
			           	</c:choose>
			           	<!--
			           	
						           	
			           	-->
			           	
			           </div>
			            
			         </c:when>
			         
			      </c:choose>
				
			</div>
	
	<script>
		
		function showHideEditForm(postId){
			
			$("#existing_post_"+postId).toggle();
			
			$("#post_update_form_"+postId).toggle();	
		}
	
		$(document).ready(function(){
			  $('[data-toggle="tooltip"]').tooltip();
			});
			
	</script>	
	
<%@ include file="../HeaderFooter/Footer.jsp" %>