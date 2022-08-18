<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
    <br><br>
    <div class="container mt-2">
        <h4>Stock Summary</h4>

        <div class="row">
            <div class="col-md-12">
                <div class='alert alert-info '>
                    <ul>
                        <li>Use the adjustment option with caution.</li>

                        <li>Total Spent also includes adjusted stock.</li>
                    </ul>
                </div>

            </div>

        </div>

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

                                        <tr>
                                            <td>Allocated: ${item.orgAllocated}</td>
                                            <td>Total Spent (Including Adj): <span id="spent_${item.id}">${item.spent}</span></td>
                                            <td>Allocated Left: <span id="left_${item.id}">${item.allocatedLeft}</span></td>
                                            <td>
                                                <c:if test="${sessionScope.user.role == 'MANAGER'}">
                                                    <button class="btn btn-outline-secondary input-group-append" onclick="adjust( ${item.id}, ${item.allocatedLeft}, ${item.spent} )">Adjust to 0.0</button>

                                                </c:if>
                                                </td>
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

<script>

    function adjust(id, adj, spent){

        if(window.confirm("This will set the allocate left stock to 0.0 !")){
            const xhttp = new XMLHttpRequest();
            xhttp.onload = function() {

                var results = JSON.parse(this.responseText);

                if(results.message=="ok"){
                    document.getElementById(`spent_\${id}`).innerHTML = `\${spent + adj}`;
                    document.getElementById(`left_\${id}`).innerHTML = '0.0';

                }else {
                    window.alert("An Error occurred !");
                }

            }
            xhttp.open("GET", "${contextPath}/manager/stock/allocated-stock/truncate-left?id="+id);
            xhttp.send();
        }


    }

</script>

</body>

</html>