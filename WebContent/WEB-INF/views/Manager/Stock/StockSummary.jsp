<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
    <br><br>
    <div class="container mt-2">
        <h4>Stock Summary</h4>


        <c:choose>


            <c:when test = "${info.totalPages > 0}">
                <div class="row mt-3">

                    <div class="col-md-12">

                        <ul class="pagination justify-content-center">
                            <c:if test = "${info.currentPage > 1}">
                                <li class="page-item"><a class="page-link"
                                                         href="${contextPath}/manager/stock/stock-summary-page/${info.currentPage-1}">Prev</a></li>
                            </c:if>


                            <li class="page-item disabled">
                                <p class="page-link">
                                    Page: ${info.currentPage } out of ${info.totalPages }
                                </p>
                            </li>

                            <c:if test = "${info.totalPages != info.currentPage}">
                                <li class="page-item"><a class="page-link"
                                                         href="${contextPath}/manager/stock/stock-summary-page/${info.currentPage+1}">Next</a></li>
                            </c:if>

                        </ul>

                    </div>
                </div>

                <div class="row mt-3">
                    <div class="col list-group list-group-flush">

                    <c:forEach items="${list}" var="item">
                        <div class="list-group-item">
                            <div class="card">
                                <div class="card-header">
                                    <p class="float-left">
                                            ${item.id}/${item.product}</p>
                                    <a target="_blank" class="float-right btn btn-light" href="${contextPath}/manager/stock/${item.id}/view-stock">View Stock</a>
                                </div>
                                <div class="card-body">
                                    <table class="table table-borderless">
                                        <tr>
                                            <td>Original Stock: ${item.orgStock}</td>
                                            <td>Allocated: ${item.orgAllocated}</td>
                                            <td>
                                                Expired:
                                                <c:if test="${item.expired > 0}">
                                                    <span class="badge badge-danger">${item.expired}</span>
                                                </c:if>
                                                <c:if test="${item.expired == 0}">
                                                    0
                                                </c:if>
                                            </td>
                                            <td>Effective Stock Left: ${item.effectiveStock}</td>

                                        </tr>
                                    </table>

                                    <table class="table table-borderless">
                                        <tr>
                                            <td>Allocated: ${item.orgAllocated}</td>
                                            <td>Total Spent: ${item.spent}</td>
                                            <td >Allocated Left: ${item.allocatedLeft}</td>
                                        </tr>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    </div>
                </div>



            </c:when>

        </c:choose>


    </div>

</body>

</html>