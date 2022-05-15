<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="header.jsp" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="container" style="margin-top: 100px;">
	
	<div class="row">
		<div class="col-md-12 form-group">
			<label>Enter the number of parameters in the test</label>
			 <input type="number" id="numOfRows"  class="form-control"><br>
			 <button onclick="updateRows()" class="btn btn-secondary">Add Rows</button>
		</div>
	</div>
	<br><br>
	<form method="post" action="${contextPath}/lab/add-test">
		<div class="row form-group">
			<div class="col-md-12 ">
				<label>Select Test:</label>
				  <select class="form-control" required name="test">
				    <c:forEach var="test" items="${tests}">
				    	<option value="${test.id }">${test.procedure }</option>
				    </c:forEach>
				  </select>
				  <input type="number" name="rows" id="numRows" hidden required>
			</div>
			<br><br>
			<div class="col-md-12">
				<table class="table table-borderless" id="parameters">
					
				</table>
				
				<input type="submit" class="btn btn-primary">
				<br><br>
				<c:if test = "${not empty  message}">
			         <div class="alert alert-success">
		 				${message}
		 			</div>
			      </c:if>
				
			</div>
			
		</div>
	</form>
	

</div>

<script>
	function updateRows(){
		
		document.getElementById("parameters").innerHTML="";
		
		var rows=document.getElementById("numOfRows").value;
		if( rows=="" || rows<0)
			window.alert("Please enter a positive number!");
		else{
			document.getElementById("numRows").value=rows;
			
			var add=
			`<tr>
				<th>Parameter</th>
				<th>Unit</th>
				<th>Lower Range</th>
				<th>Upper Range</th>
			</tr>`;
			
			for(var i=0;i<rows;i++){
				add+=`<tr>
					<td>
					<input type="text" name="para_\${i}" class="form-control" required>
					</td>
					
					<td>
					<input type="text" name="unit_\${i}" class="form-control">
					</td>
					
					<td>
					<input type="number" name="low_\${i}" class="form-control" step="0.01">
					</td>
					
					<td>
					<input type="number" name="high_\${i}" class="form-control" step="0.01">
					</td>
					
				</tr>`;
					
			}
			
			document.getElementById("parameters").innerHTML=add;
		}
		
	}
</script>