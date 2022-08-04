<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
	
	<br><br><br><br>
	<div class='container'>
		<div class='alert alert-info'>All the procedures will be shown below. Rates can also be zero.</div>
		<div class='alert alert-danger'>A new procedure will only be added if there is no enabled procedure with the same name !</div>
		<div class='row'>
			
			<form class='col-md-12 form-group form-inline' action="${contextPath}/manager/bills/add-procedure">
				<label>Select Billing Group</label>
				<select name="billGroup" class='form-control ml-1' required="required">
					<option></option>
					<c:forEach var="group" items="${groups }">
						<option value='${group.id }'>${group.name}</option>
					</c:forEach>
				</select>
				
				<label class='ml-3'>Procedure Name</label>
				<input required name="procedure" class='form-control ml-1'/>
				
				<label class='ml-3'>Rate</label>
				<input required type='number' min="0" name="rate" class='form-control ml-1'/>
				
				<input type="submit" class='btn btn-success ml-3'/>
				
			</form>
			
			<c:if test="${not empty procedures }">
				<br>
				<input class="form-control" id="term" type="text" placeholder="Search.." />
				<br>
				<table class="col-md-12 table table-borderless mt-5">
					<tr>
						<th>Procedure</th>
						<th>Rate</th>
						<th>Options</th>
					</tr>
					<tbody id="tbody">
						<c:forEach var="procedure" items="${procedures }">
							<tr>
								<td>
										${procedure.name }

										<c:if test="${!procedure.enabled}">
											<span class="badge badge-danger">Billing Disabled</span>
										</c:if>

										<c:if test="${procedure.stockTracking}">
											<span class="badge badge-info">Stock Tracking Enabled</span>
										</c:if>



								</td>
								<td>
									<form class='form-group form-inline' action="${contextPath }/manager/bills/edit-procedure/${procedure.id}">
										<input type='number' required min='0'
											   class='form-control' value="${procedure.rate }" name="rate"/>
										<button class='btn btn-light ml-3'>Change</button>
									</form>
								</td>

								<td>

									<div class="dropdown">
										<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
											Options
										</button>
										<div class="dropdown-menu">
											<c:if test="${procedure.enabled}">

												<a class='dropdown-item' href="${contextPath}/manager/bills/procedure/${procedure.id}/disable">Disable</a>
											</c:if>
											<c:if test="${!procedure.enabled}">
												<a class='dropdown-item' href="${contextPath}/manager/bills/procedure/${procedure.id}/enable">Enable</a>
											</c:if>

											<c:if test="${procedure.stockTracking}">

												<a class='dropdown-item' href="${contextPath}/manager/bills/procedure/${procedure.id}/stock-disable">Disable Stock Tracking</a>

											</c:if>
											<c:if test="${!procedure.stockTracking}">
												<a class='dropdown-item' href="${contextPath}/manager/bills/procedure/${procedure.id}/stock-enable">Enable Stock Tracking</a>
											</c:if>

											<a class='dropdown-item' href="${contextPath}/manager/stock/procedure/${procedure.id}/stock-mapping">Procedure & Product Mapping</a>

										</div>
									</div>



								</td>

							</tr>
						</c:forEach>
					</tbody>

				</table>
			</c:if>
			
			
		</div>
		
	</div>

</body>

<script>
	$(document).ready(function(){
		$("#term").on("keyup", function() {
			var value = $(this).val().toLowerCase();
			$("#tbody tr").filter(function() {
				$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
			});
		});
	});
</script>

</html>