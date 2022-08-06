<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<br>
<c:if test="${sessionScope.user.role == 'MANAGER'}">
    <div class="container mt-5">
        <h4>Allocate Stock</h4>
        <form class="form-group row" method="get" action="${contextPath}/manager/stock/get-batches-allocate">
            <div class="col-md-4">

                <input type="text" id="product-search" placeholder="Search Product" class="form-control" onkeyup="fetchResults('product-search','product-list','product','product')">
                <input type="number" hidden="true" id="product" name="product" required>
                <div class=" list-group" id="product-list"></div>
            </div>


            <div class="col-md-4">

                <input placeholder="Enter Qty" type="number" min="1" class="form-control" name="qty" required>
            </div>

            <div class="col-md-4">
                <input type="submit" class="btn btn-light">
            </div>


        </form>

        <c:if test="${not empty list}">
            <div class="row">
                <table class="col-md-12 table">
                    <tr>
                        <th>Batch</th><th>Qty</th>
                    </tr>
                    <c:set var="sum" value="0"/>
                    <c:forEach items="${list}" var="item">
                        <tr>
                            <td>${item.name}</td>
                            <td>
                                    ${item.no}
                                <c:set var="sum" value="${sum+item.no}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${sum < qty}">
                    <div class="col-md-12 alert alert-danger">
                        Not Enough Stock! You can still continue though with the allocation.
                    </div>
                </c:if>
                <div class="text-center col-md-12">
                    <a href="${contextPath}/manager/stock/allocate-stock?id=${id}&qty=${sum<qty ? sum : qty}" class="btn btn-primary">Allocate</a>
                </div>
            </div>
        </c:if>

        <c:if test="${not empty allocated && !allocated}">
            <div class="row alert alert-danger">
                Uh-oh something happened !
            </div>
        </c:if>

        <c:if test="${allocated}">
            <div class="row alert alert-success">
                Stock Allocated!
            </div>
        </c:if>
    </div>

    <script>

        function fetchResults(id,idOfCollapse,type,targetId){
            var doc = document.getElementById(id);

            if(doc.value!='') {
                const xhttp = new XMLHttpRequest();
                xhttp.onload = function() {
                    document.getElementById(idOfCollapse).innerHTML=``;
                    var results = JSON.parse(this.responseText);

                    for(var i=0;i<results.length;i++){
                        if(results[i].enabled)
                            document.getElementById(idOfCollapse).innerHTML+=`
              <button class='list-group-item button button-light'
              onclick="setAndCollapse(\${results[i].id},'\${targetId}','\${idOfCollapse}','\${id}','\${results[i].name}')">\${results[i].name}</button>
                        `;

                    }
                }
                xhttp.open("GET", "${contextPath}/manager/stock/"+type+"?term="+doc.value);
                xhttp.send();
            }
        }

        function setAndCollapse(value,targetId, idOfCollapse, inputId, inputValue){
            document.getElementById(targetId).value = value;
            document.getElementById(idOfCollapse).innerHTML = ``;
            document.getElementById(inputId).value = inputValue;
        }

    </script>
</c:if>