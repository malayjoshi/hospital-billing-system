<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<div class="container mt-5">
<br><br>
	<div class='alert alert-info'>All the <b>Enabled</b> procedures will be shown below.</div>
	<div class="row">
		<input class="form-control" id="search" type="text" placeholder="Search"><br>
		<table class="col table">
			<thead>
				<tr>
					<th>Procedure</th>
					<th>Enable/Disable Stock Tracking</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody id="table">
				<c:forEach var="procedure"  items="${ procedures}">
					<tr>
						<td>${procedure.procedure}</td>
						<td>
							<a class='btn btn-success' href="${contextPath}/manager/lab-inventory/procedure-stock-tracking/${procedure.id}/enable">Enable</a>
							<a class='btn btn-danger' href="${contextPath}/manager/lab-inventory/procedure-stock-tracking/${procedure.id}/disable">Disable</a>
						</td>
						<td>
							<c:if test="${empty procedure.stockTracking}">
							false
							</c:if>
							<c:if test="${not empty procedure.stockTracking}">
								${procedure.stockTracking}
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<script>
\$(document).ready(function(){
  \$("#search").on("keyup", function() {
    var value = \$(this).val().toLowerCase();
    \$("#table tr").filter(function() {
      \$(this).toggle(\$(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>