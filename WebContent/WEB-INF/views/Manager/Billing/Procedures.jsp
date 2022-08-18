<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
<c:if test="${sessionScope.user.role == 'MANAGER'}">
	<br><br><br><br>
	<div class='container'>
		<div class="row">
			<div class="col-md-12">
				<div class='alert alert-info '>
					<ul>
						<li>
							All the procedures will be shown below.</li>
						<li>Rates can also be zero.</li>
						<li>A new procedure will only be added if there is no enabled procedure with the same name !</li>
						<li>If a row shows in red then it implies that its billing is disabled.</li>
					</ul>
				</div>

			</div>

		</div>
		<div class='row mt-5'>
			<div class="col-md-12">

				<div class="card">
					<div class="card-header">
						<p>Create New Procedure</p>
					</div>
					<div class="card-body">
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
					</div>
				</div>


			</div>


		</div>

			<c:if test="${not empty procedures }">
				<div class="row mt-5">
					<div class="col-md-6 form-inline">
						<label>Search For any Procedure:</label>
						<input class="form-control ml-3" id="term" type="text" placeholder="Search.." />
					</div>
					<div class="col-md-6 btn-group text-center mt-3">

						<a href="${contextPath}/manager/bills/procedures-page" class="btn btn-outline-dark"  >Show All</a>
						<c:forEach var="type" items="${filterTypes}">
							<a href="${contextPath}/manager/bills/procedure/${type}" class="btn btn-outline-dark" id="${type}" >Show ${type}</a>
						</c:forEach>
					</div>
				</div>
				<div class="row">
				<table class="col-md-12 table table-borderless mt-5">
					<tr>
						<th>Procedure</th>
						<th>Rate</th>
						<th>Options</th>
					</tr>
					<tbody id="tbody">
						<c:forEach var="procedure" items="${procedures }">
							<tr class="${ procedure.enabled ? 'procedure-enabled':'table-danger'} ${ procedure.stockTracking ? 'stock-enabled':'stock-disabled'} ">
								<td>
										${procedure.name }


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

											<a class='dropdown-item' target="_blank" href="${contextPath}/manager/stock/procedure/${procedure.id}/stock-mapping">Procedure & Product Mapping</a>

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
</c:if>
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

