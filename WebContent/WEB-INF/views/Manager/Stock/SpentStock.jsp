<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
    <br>
    <div class="container mt-5">
        <h4>Stock Spent Summary</h4><br>
        <div class="row">

        </div>
        <form class="col form-group form-inline" action="${contextPath}/manager/stock/get-spent-stock">
            <label>Type:</label>
            <select class="form-control ml-3" name="type" required>
                <c:forEach var="item" items="${type}">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </select>

            <label class="ml-5">Date:</label>
            <input type="date" name="date" class="form-control ml-3" required>
            <input type="submit" class="btn btn-light ml-5">
        </form>

        <c:if test="${not empty list}">
            <div class="row mt-5">
                <table class="col table table-borderless">
                    <tr>
                        <th>ID</th><th>Product</th><th>Qty spent</th>
                    </tr>
                    <c:forEach var="item" items="${list}">
                        <tr>
                            <td>${item.id}</td>

                            <td>${item.name}</td>


                            <td>${item.no}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </c:if>

    </div>

</body>
</html>