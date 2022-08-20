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

            <div class="row mt-3" id="list">
                <c:forEach var="item" items="${list}">
                    <div class="col-md-12 mt-2" style="cursor: pointer;" onclick="showHide(${item.id})" >
                    <div class="card" >
                        <div class="card-header">
                            <span class="float-left">ID: ${item.id}/${item.name}</span>

                            <span class="float-right">Spent: ${item.qty}</span>
                        </div>
                        <div class="card-body" id="items_${item.id}" style='display: none;'>
                            <c:if test="${not empty item.list }">
                                <table class="table table-borderless">
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Count</th>
                                        <th>Avg Ratio</th>
                                        <th>Spent Count</th>
                                    </tr>
                                    <c:forEach var="proc" items="${item.list}">
                                        <tr>
                                            <td>${proc.id}</td>
                                            <td>${proc.name}</td>
                                            <c:forEach var="value" items="${proc.values}">
                                                <td>${value}</td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </div>

                    </div>
                    </div>
                </c:forEach>
            </div>

        </c:if>

    </div>

    <script>
        function showHide(tid){
            $('#items_'+tid).toggle();
        }



    </script>

</body>
</html>