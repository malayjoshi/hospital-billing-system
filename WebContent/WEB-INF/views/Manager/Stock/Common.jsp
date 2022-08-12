<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<br>
<c:if test="${sessionScope.user.role == 'MANAGER'}">
    <div class="container mt-5">

        <c:choose>


            <c:when test = "${type == 'product'}">
                <form class='col-md-12 form-group form-inline' method='post'
                      action="${contextPath }/manager/stock/add-test-${type}">
                    <label>Add Product:</label>
                    <button class="btn btn-secondary ml-3" data-toggle="modal" data-target="#company-modal" id="company-name">Select Company</button>
                    <input type="number" required hidden="true" name="id" id="id">
                    <input name="name" required class='form-control ml-3' placeholder='${type} name'>
                    <input type='submit' class='btn btn-primary ml-5' >
                </form>

                <div class="modal" id="company-modal">
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

            </c:when>

            <c:when test = "${type == 'supplier' || type == 'company'}">
                <form class='col-md-12 form-group form-inline' method='post'
                      action="${contextPath }/manager/stock/add-${type}">
                    <input name="name" required class='form-control' placeholder='${type} name'>
                    <input type='submit' class='btn btn-primary ml-5' >
                </form>
            </c:when>

        </c:choose>


        <c:choose>


            <c:when test = "${info.totalPages > 0}">
                <div class="row">

                    <div class="col-md-12">

                        <ul class="pagination justify-content-center">
                            <c:if test = "${info.currentPage > 1}">
                                <li class="page-item"><a class="page-link"
                                                         href="${contextPath}/manager/stock/${type}-page/${info.currentPage-1}">Prev</a></li>
                            </c:if>


                            <li class="page-item disabled">
                                <p class="page-link">
                                    Page: ${info.currentPage } out of ${info.totalPages }
                                </p>
                            </li>

                            <c:if test = "${info.totalPages != info.currentPage}">
                                <li class="page-item"><a class="page-link"
                                                         href="${contextPath}/manager/stock/${type}-page/${info.currentPage+1}">Next</a></li>
                            </c:if>

                        </ul>

                    </div>
                </div>

                <table class='table'>
                    <tr>
                        <th>Name</th>
                        <c:if test="${type=='product'}">
                            <th>Company</th>
                        </c:if>
                        <th>Actions</th>
                    </tr>

                    <c:forEach var="item" items="${list }">
                        <tr>
                            <td>${item.name }</td>

                            <c:if test="${type=='product'}">
                                <td class="form-group">

                                        <input type="text" id="company-search-${item.id}" class="form-control" value="${item.company}"
                                               onkeyup="fetchResultsParams('company-search-${item.id}','company-list-${item.id}','company','company_${item.id}')">
                                        <input type="number" hidden="true" id="company_${item.id}" name="company_${item.id}" required>

                                    <div class=" list-group" id="company-list-${item.id}"></div><br>
                                    <button class="btn btn-outline-secondary" onclick="changeCompany(`company-search-${item.id}`,`company_${item.id}`,'company-list-${item.id}',${item.id})">Change</button>




                                        </td>
                            </c:if>

                            <td>

                                <div class="dropdown">
                                    <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                                        Options
                                    </button>
                                    <div class="dropdown-menu">
                                        <c:choose>
                                            <c:when test="${item.enabled}">
                                                <a class='dropdown-item' href="${contextPath }/manager/stock/${type}/${info.currentPage}/${item.id}/disable">Disable</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class='dropdown-item' href="${contextPath }/manager/stock/${type}/${info.currentPage}/${item.id}/enable">Enable</a>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${type== 'product'}">
                                                <a class='dropdown-item' href="${contextPath }/manager/stock/${item.id}/view-stock">View Stock</a>
                                            </c:when>
                                        </c:choose>


                                    </div>
                                </div>

                            </td>
                        </tr>
                    </c:forEach>


                </table>



            </c:when>

        </c:choose>




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
                        onclick="setFieldAndCloseModal(\${results[i].id},'id','company-name','\${results[i].name}','company-modal')">
                            \${results[i].name}</button>

                        `;

                    }
                }
                xhttp.open("GET", "${contextPath}/manager/stock/company?term="+doc);
                xhttp.send();
            }
        }
        function  setFieldAndCloseModal(valueForFieldToBeSet,idOfFieldToBeSet,idOfButtonToSetName,valueOfButtonToSetName,modalId){
            document.getElementById(idOfFieldToBeSet).value = valueForFieldToBeSet;
            document.getElementById(idOfButtonToSetName).innerHTML=valueOfButtonToSetName;
            $("#"+modalId).modal("hide");
        }

        function fetchResultsParams(id,idOfCollapse,type,targetId){
            var doc = document.getElementById(id);

            if(doc.value!='') {
                const xhttp = new XMLHttpRequest();
                xhttp.onload = function() {
                    document.getElementById(idOfCollapse).innerHTML=``;
                    var results = JSON.parse(this.responseText);

                    for(var i=0;i<results.length;i++){
                            document.getElementById(idOfCollapse).innerHTML+=`
              <button class='list-group-item button button-light'
              onclick="setAndClear(\${results[i].id},'\${targetId}','\${idOfCollapse}','\${id}','\${results[i].name}')">
                \${results[i].name}
                </button>
                        `;




                    }
                }
                xhttp.open("GET", "${contextPath}/manager/stock/"+type+"?term="+doc.value);
                xhttp.send();
            }

            }

        function setAndClear(value,targetId, idOfCollapse, inputId, inputValue){
            document.getElementById(targetId).value = value;
            document.getElementById(idOfCollapse).innerHTML = ``;
            document.getElementById(inputId).value = inputValue;
        }

        function changeCompany(searchId,id,idOfList,idItem){
            var val = document.getElementById(id);

            if(val.value!='' || val.value>0) {
                const xhttp = new XMLHttpRequest();
                xhttp.onload = function() {
                    document.getElementById(idOfList).innerHTML=``;
                    var result = JSON.parse(this.responseText);
                    if(result.message=='ok'){
                        window.alert("Company Changed!");
                    }

                }
                xhttp.open("GET", "${contextPath}/manager/stock/set-company?id="+val.value+"&item="+idItem);
                xhttp.send();
            }

        }


    </script>

</c:if>