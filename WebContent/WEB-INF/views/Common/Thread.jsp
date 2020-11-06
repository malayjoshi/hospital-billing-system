<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var = "info"  value = "${threadDetailsByPage}"/>

<c:choose >
	<c:when test="${ sessionScope.user.role == 'USER' }">
		<%@ include file="../RegisteredUser/HeaderFooter/Header.jsp" %>	
	</c:when>
</c:choose>

	
	<div class="container" style="margin-top: 100px;">
		<br><br>
		
		<c:if test="${not empty successMessage}">
			<div class="alert alert-success">
				${successMessage}
			</div>
		</c:if>
		
		<h3>Thread</h3>
		<br>
		
		<div class="row">
			
			<div class="col-md-12">
				
				<c:if test="${info.closed }">
					<div class='alert alert-danger text-center'>
						This thread is closed. You cannot add a new post nor reply to any post.
					</div>
					<br>
				</c:if>
			            
	            <ul class="pagination justify-content-center">
	             	<c:if test = "${info.currentPage > 1}">
				  		<li class="page-item"><a class="page-link" href="${contextPath}/user/thread/${info.threadId }/${info.currentPage-1}">Prev</a></li>       
				    </c:if>
				    
				    
	            	<li class="page-item disabled">
	            		<p class="page-link">
	            			Page: ${info.currentPage } out of ${info.totalPages }
	            		</p>
	            	</li>
				    
				    <c:if test = "${info.totalPages != info.currentPage}">
				  		<li class="page-item"><a class="page-link" href="${contextPath}/user/thread/${info.threadId }/${info.currentPage+1}">Next</a></li>       
				    </c:if>
				    
				</ul>
	         
			</div>
		</div>
		
		<c:set var="actions" value="${info.actionsWithId}"/>
		
		<c:forEach var="post" items="${info.posts}">
			<div class="row mt-5">
				
				<div class="col-md-12">			
							
					<div class="card">
					  <div class="card-body">
					  
					  	<c:if test = "${post.repliedAgainstPost !=null}">
					  		<div class="card bg-light text-dark">
					  			<div class="card-body">
					  				<h5 class="card-title">
										${post.repliedAgainstPost.text}
									</h5>
									
									<p class="card-text">
										${post.repliedAgainstPost.author }
										<span class="badge badge-light float-right">Created:
									  		<fmt:formatDate type = "both" value = "${post.repliedAgainstPost.date}" />
									  	</span>
									</p>
													
					  			</div>
					
								
					  		</div>
					  	
					  	</c:if>
					  	
					  	
					  
					    <h5 class="card-title">${post.text }</h5>
					    <p class="card-text">
					    	${post.author}
					    
					    </p>
					    
					  </div>
					  
					  <div class='card-footer'>
					  	
					  	<span class=" float-right">Created:
					  		<fmt:formatDate type = "both" value = "${post.date}" />
					  	</span>
					  	
					  	<c:if test="${!info.closed }">
					  		<span class="float-right pr-5">
						  		<a class="list-group-item-action" data-toggle="tooltip" title="Reply"
						  		 href="${contextPath}/user/thread/${info.threadId}/reply-against-post/${post.id}">
						  			<img alt="reply" src="https://img.icons8.com/android/24/000000/reply-arrow.png"/>
						  		</a>
						  		
						  	</span>
					  	</c:if>
					  	
					  	
					  	
					  	<c:set var="mapping" value="${post.mappingOfActionWithMappingId}"/>
					  	
					  	<c:choose>
					  		<c:when test="${not empty mapping['FLAGGED'] }">
					  					<!-- /user/thread/{threadId}/{pageNo}/{postId}/{addOrRemove}/{action}/{mappingId} -->
					  			<span class="float-right pr-5">
							  		<a class="list-group-item-action" data-toggle="tooltip" title="Un-Flag"
							  		 href="${contextPath}/user/thread/${info.threadId}/${info.currentPage }/${post.id}/${mapping['FLAGGED']}">
							  			<img src="https://img.icons8.com/ios-filled/25/000000/empty-flag.png" alt="unflag"/>
							  		</a>
							  		
							  	</span>
					  			
					  		</c:when>
					  		
					  		<c:otherwise>
					  			<!-- /user/thread/{threadId}/{pageNo}/{postId}/{addOrRemove}/{action} -->
					  			<span class="float-right pr-5">
							  		<a class="list-group-item-action" data-toggle="tooltip" title="Flag this post"
							  		 href="${contextPath}/user/thread/${info.threadId}/${info.currentPage }/${post.id}/add/FLAGGED">
							  			<img src="https://img.icons8.com/ios/25/000000/empty-flag.png" alt="flag"/>
							  		</a>
							  		
							  	</span>
							  		
					  		</c:otherwise>
					  	</c:choose>
					  	
					  	
					  	<c:choose>
					  		<c:when test="${not empty mapping['LIKED'] }">
					  					<!-- /user/thread/{threadId}/{pageNo}/{postId}/{addOrRemove}/{action}/{mappingId} -->
					  			<span class="float-left">
							  		<a class="list-group-item-action" data-toggle="tooltip" title="Remove Like"
							  		 href="${contextPath}/user/thread/${info.threadId}/${info.currentPage }/${post.id}/${mapping['LIKED']}">
							  			<img src="https://img.icons8.com/material/24/000000/facebook-like--v1.png" alt="remove like"/>
							  		</a>
							  		
							  	</span>
					  			
					  		</c:when>
					  		<c:otherwise>
					  			<!-- /user/thread/{threadId}/{pageNo}/{postId}/{addOrRemove}/{action} -->
					  			<span class="float-left">
							  		<a class="list-group-item-action" data-toggle="tooltip" title="Like"
							  		 href="${contextPath}/user/thread/${info.threadId}/${info.currentPage }/${post.id}/add/LIKED">
							  			<img alt="reply" src="https://img.icons8.com/material-outlined/24/000000/facebook-like.png"/>
							  		</a>
							  		
							  	</span>
							  		
					  		</c:otherwise>
					  	</c:choose>
					  	
					  	
					  	<c:if test="${post.likedCount>0 }">
					  		<span class="float-left pl-5">
						  	  <h6 class='badge badge-info'>${post.likedCount} Likes</h6>
						  	</span>
					  	</c:if>
					  	
					  </div>
					  
					 
					  
					</div>
							
				</div>
			</div>
		</c:forEach>
		
		
		<hr>
		<c:choose >
			<c:when test="${ sessionScope.user.role == 'USER' }">
				<div class='row'>
					<div class='col-md-12 mt-5'>
						
						<c:if test="${!info.closed }">
							
							<form:form action="${contextPath}/user/thread/${info.threadId}/${info.currentPage }/new-post" method="post" modelAttribute="newPost">
    						
						       <h4>Have some thoughts</h4>
						       <div class="form-group">
						       		<form:textarea path="post" required="required" cssClass="form-control" maxlength="400" placeholder="Your post..."/>
						       </div>
						       <div class="alert alert-warning">Maximum of 400 characters allowed.</div>
						       
						       <form:errors path="post" cssClass="alert alert-danger"/>
						     	
					     		<div class="text-center">
						       		<button type="submit" class="btn btn-success">Submit</button>
						     	</div>
							    	 
							</form:form>		
								
						</c:if>
						
						
						
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
					
			</c:when>
		</c:choose>
		
		
		
	</div>
		
	
		

		<div class='text-center'>
			<a href="https://icons8.com/icon/70807/reply-arrow">Reply Arrow icon by Icons8</a><br>
			<a href="https://icons8.com/icon/82788/facebook-like">Facebook Like icon by Icons8</a><br>
			<a href="https://icons8.com/icon/11710/empty-flag">Empty Flag icon by Icons8</a>
		</div>
		
		<script>
			$(document).ready(function(){
			  $('[data-toggle="tooltip"]').tooltip();
			});
		</script>
		
	</body>
</html>