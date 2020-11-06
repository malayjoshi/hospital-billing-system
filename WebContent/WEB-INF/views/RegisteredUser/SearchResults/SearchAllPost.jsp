<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../HeaderFooter/Header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="container" style="margin-top: 100px;">

<div class="row">
	
	<c:choose>
		
		<c:when test="${not empty listPost }">
			
			<h5>Showing ${perPage} search results.</h5>
			
			<c:forEach var="post" items="${listPost }">
		
				<div class="col-md-12 mt-5">
					<a href="${contextPath}/redirect-to-thread/post-${post.id}" style='text-decoration: none;color:black;'>
											
						<div class="card">
						  <div class="card-body">
						    <h4 class="card-title">${post.text }</h4>
						    <p class="card-text">Author: ${post.author}</p>
						    
						  </div>
						  
						  <div class="card-footer">
						  	
						  	<span class="badge badge-light float-right">Created:
						  		<fmt:formatDate type = "both" value = "${post.date}" />
						  	</span>
						  </div>
						  
						</div>
						
					</a>
				</div>
				
			</c:forEach>
					
				
		</c:when>
		
		<c:otherwise>
			<div class='col-md-12'>
				<div class='alert alert-warning text-center'>
					Sorry, no results where found. Please try again using different keywords.
			</div>
			
		</c:otherwise>
		
	</c:choose>
	
		
</div>


<%@ include file="../HeaderFooter/Footer.jsp"%>
