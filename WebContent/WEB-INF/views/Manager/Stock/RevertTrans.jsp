<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
<br><br>

<c:if test="${sessionScope.user.role=='MANAGER'}">

<div class="container mt-5">
    <h4>Revert Stock</h4>

    <div class="row">
        <div class="col-md-12">
            <div class='alert alert-info '>
                <ul>
                    <li>Revert option is only for Procedures for which stock has been reduced.</li>
                    <li><a target="_blank" href="https://icons8.com/icon/sf3OWsyCR9fh/reverse" class="mb-2">Reverse icon by Icons8</a></li>
                </ul>
            </div>

        </div>

    </div>

    <div class="row">
        <form class="form-group form-inline col" action="${contextPath}/manager/stock/get-spent-stock-by-tid">
            <label>TID:</label>
            <input type="number" required name="tid" class="form-control ml-3">
            <input type="submit" class="btn btn-outline-dark ml-3">
        </form>
    </div>

    <c:if test="${not empty list}">
        <div class="list-group">

            <c:forEach items="${list}" var="item">
                <div class="row mt-3 list-inline-item border">
                    <div class="col-md-6">${item.name}

                    </div>
                    <div class="col-md-6 text-center">
                        <c:if test="${ item.enabled }">
                            <a href="${contextPath}/manager/stock/revert-transaction/${tid}/${item.id}">
                                <img title="Revert" src="https://img.icons8.com/external-febrian-hidayat-flat-febrian-hidayat/30/000000/external-Reverse-user-interface-febrian-hidayat-flat-febrian-hidayat.png"/>

                            </a>
                        </c:if>



                    </div>
                </div>
            </c:forEach>

            <div class="row mt-3 list-group-item text-center" style="border: 0px;">
                <a href="${contextPath}/manager/stock/revert-transaction/${tid}" class="btn btn-light" >Revert All</a>
            </div>

        </div>

    </c:if>

</div>


</c:if>
