<%--
  Created by IntelliJ IDEA.
  User: malayjoshi
  Date: 8/3/22
  Time: 2:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<body>
<br><br><br>
<div class="container mt-5">

    <div class="row">

        <form class='col-md-12 form-group form-inline' method='post'
              action="${contextPath }/manager/stock/add-procedure-product-mapping">
            <h4>Test: ${test.name}</h4>
            <button class="btn btn-secondary ml-3" data-toggle="modal" data-target="#product-modal" id="product-name">Select Product</button>
            <input type="number" required hidden="true" name="productId" id="id">
            <input type="number" required min="1"  class="form-control ml-3" name="ratio" placeholder="Enter how many tests can be done with one quantity of the product" >
            <input name="procedureId" required class='form-control ml-3' hidden="true" value="${test.id}">

            <input type='submit' class='btn btn-primary ml-5' >
        </form>


        <div class="modal" id="product-modal">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body form-group">
                        <input type="text" class="form-control" placeholder="Search" id="term" onkeyup="fetchResults()">
                        <br>
                        <div id="results">

                        </div>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>

                </div>
            </div>
        </div>



    </div>

    <c:if test="${not empty list }">
        <br>

        <table class="col-md-12 table table-borderless mt-5">
            <tr>
                <th>Product</th>
                <th>Ratio</th>
                <th>Options</th>
            </tr>
            <tbody id="tbody">
            <c:forEach var="item" items="${list }">
                <tr>
                    <td>
                            ${item.name }

                    </td>
                    <td>
                        <form class='form-group form-inline' action="${contextPath }/manager/stock/${test.id}/mapping/change-ratio/${item.id}">
                            <input type='number' required min='1'
                                   class='form-control' value="${item.rate }" name="ratio"/>
                            <button class='btn btn-light ml-3'>Change</button>
                        </form>
                    </td>

                    <td>

                        <div class="dropdown">
                            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                                Options
                            </button>
                            <div class="dropdown-menu">
                                <a class='dropdown-item' href="${contextPath}/manager/stock/${test.id}/mapping/delete/${item.id}">Delete</a>
                            </div>
                        </div>



                    </td>

                </tr>
            </c:forEach>
            </tbody>

        </table>
    </c:if>

</div>

<script>
    function fetchResults(){
        var doc = document.getElementById(`term`).value;

        if(doc!='') {

            const xhttp = new XMLHttpRequest();
            xhttp.onload = function() {
                document.getElementById("results").innerHTML=``;
                var results = JSON.parse(this.responseText);
                console.log(results);
                for(var i=0;i<results.length;i++){
                    document.getElementById("results").innerHTML+=`
                        <button type="button" class="btn btn-primary btn-block"
                        onclick="setFieldAndCloseModal(\${results[i].id},'id','product-name','\${results[i].name}','product-modal')">
                            \${results[i].name}</button>

                        `;

                }
            }
            xhttp.open("GET", "${contextPath}/manager/stock/product?term="+doc);
            xhttp.send();
        }
    }
    function  setFieldAndCloseModal(valueForFieldToBeSet,idOfFieldToBeSet,idOfButtonToSetName,valueOfButtonToSetName,modalId){
        document.getElementById(idOfFieldToBeSet).value = valueForFieldToBeSet;
        document.getElementById(idOfButtonToSetName).innerHTML=valueOfButtonToSetName;
        $("#"+modalId).modal("hide");
    }
</script>


</body>
</html>