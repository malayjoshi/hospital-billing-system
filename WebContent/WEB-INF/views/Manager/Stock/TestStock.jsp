<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<br>
<c:if test="${sessionScope.user.role == 'MANAGER'}">

    <div class="container mt-5">

        <div class="row">
            <div class="col-md-12 text-center">
                <p class="alert alert-info">Only batches with some qty left in them will be shown</p>
            </div>

            <c:if test="${not empty list}">

                <table class="table">
                    <tr>
                        <th>ID</th>
                        <th>Batch</th>
                        <th>Expiry</th>
                        <th>Original Total Qty</th>
                        <th>Qty Left</th>
                    </tr>
                    <c:forEach var="item" items="${list}" >
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.batch}</td>
                            <td><fmt:formatDate value="${item.expiry}" pattern="dd-MM-yyyy " /></td>
                            <td>${item.free+item.qty}</td>
                            <td>${item.qtyLeft}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

        </div>
    </div>

</c:if>