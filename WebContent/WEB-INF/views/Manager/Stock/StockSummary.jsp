<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
    <br><br>
    <div class="container mt-2">
        <h4>Stock Summary</h4>

        <div class="row mt-3">
            <div class="col-md-12">
                <div class='alert alert-info '>
                    <ul>
                        <li>Try not to get report of more than 1 year. Server may crash!</li>
                    </ul>
                </div>

            </div>
        </div>

        <div class="row mt-3">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <form class="form-group form-inline" action="${contextPath}/manager/stock/get-total-stock">
                            <label>Start Date:</label>
                            <input type="date" name="startDate" class="form-control ml-3" required>

                            <label class="ml-5">Start Date:</label>
                            <input type="date" name="endDate" class="form-control ml-3" required>
                            <input type="submit" class="btn btn-light ml-5">
                        </form>
                    </div>
                </div>


            </div>

        </div>
            <c:if test="${not empty error}">

                <div class="row mt-3">
                    <div class="col">
                        <div class="alert alert-danger">${error}</div>
                    </div>
                </div>
            </c:if>

        <c:choose>


            <c:when test = "${info.totalPages > 0}">
                <div class="row">

                    <div class="col-md-12">

                        <ul class="pagination justify-content-center">
                            <c:if test = "${info.currentPage > 1}">
                                <li class="page-item"><a class="page-link"
                                                         href="${contextPath}/manager/stock/get-total-stock/${info.currentPage-1}?startDate=${startDate}&endDate=${endDate}">Prev</a></li>
                            </c:if>


                            <li class="page-item disabled">
                                <p class="page-link">
                                    Page: ${info.currentPage } out of ${info.totalPages }
                                </p>
                            </li>

                            <c:if test = "${info.totalPages != info.currentPage}">
                                <li class="page-item"><a class="page-link"
                                                         href="${contextPath}/manager/stock/get-total-stock/${info.currentPage+1}?startDate=${startDate}&endDate=${endDate}">Next</a></li>
                            </c:if>

                        </ul>

                    </div>
                </div>

                <div class="row">
                    <div class="col list-group">

                    <c:forEach items="${list}" var="item">
                        <div class="list-group-item">
                            <div class="card">
                                <div class="card-header">
                                    <p>
                                            ${item.id}/${item.product}</p>
                                </div>
                                <div class="card-body">
                                    <div>

                                        <p class="float-left">Opening Stock: ${item.openingStock}</p>

                                        <p class="float-right">Closing Stock: ${item.closingStock}</p>
                                    </div>
                                    <p class="text-center">Expired:
                                    <c:if test="${item.expired > 0}">
                                        <span class="badge badge-danger">${item.expired}</span>
                                    </c:if>
                                    </p>
                                    <div>

                                        <p class="float-left">Allocated Opening Stock: ${item.allocatedOpening}</p>

                                        <p class="float-right">Allocated Closing Stock: ${item.allocatedClosing}</p>
                                    </div>
                                    <p class="text-center">Total Spent: ${item.spent}</p>
                                    <c:if test="${not empty item.mapping}">
                                        <table class="table table-borderless">
                                            <tr>
                                                <th>Test Name</th>
                                                <th>Bill Count</th>
                                                <th>Spent Count</th>
                                                <th>Avg Ratio</th>
                                                <th>Total Spent</th>
                                            </tr>
                                            <c:forEach var="map" items="${item.mapping}">
                                                <tr>
                                                    <td>${map.name}</td>
                                                    <td>${map.no1}</td>
                                                    <td>${map.no2}</td>
                                                    <td>${map.no3}</td>
                                                    <td>${map.no4}</td>

                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </c:if>
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