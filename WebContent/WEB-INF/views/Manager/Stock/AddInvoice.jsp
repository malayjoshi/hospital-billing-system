<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>
<br>
<c:if test="${sessionScope.user.role == 'MANAGER'}">
  <div class="container mt-5">
    <h4>Add Stock</h4><br>
    <form class="form-group " autocomplete="off" action="${contextPath}/manager/stock/add-invoice-stock" method="post">

      <div class="row">
        <div class="col-md-4">

          <label>Select Supplier:</label>
          <input type="text" id="supplier-search" class="form-control" onkeyup="fetchResults('supplier-search','supplier-list','supplier','supplier')">
          <input type="number" hidden="true" id="supplier" name="supplier" required>
          <div class=" list-group" id="supplier-list"></div>
        </div>


        <div class="col-md-4">
          <label>Date:</label>
          <input required type="date" name="date" class="form-control">
        </div>

        <div class="col-md-4">
          <label>Invoice No:</label>
          <input required type="text" name="invoice" class="form-control">
        </div>

      </div>

      <div class="text-center mt-3">

        <button type="button" class="btn-info btn" onclick="addProduct()">Add Row</button>
      </div>
      <div class="row mt-3">
        <input type="number" hidden="true" id="rows" name="rows" value="0" required>
        <div class="col-md-12">
          <table class="table" id="table">

          </table>
        </div>
      </div>

      <div class="text-center" id="submit-btn">

      </div>

    </form>

      <c:if test="${added}">
        <div class="text-center alert alert-success" >
          Added!
        </div>
      </c:if>
      <c:if test="${!added && not empty added}">
        <div class="text-center alert alert-danger" >
          Not Added!
        </div>
      </c:if>


  </div>

  <script>
    function addProduct() {
      var rows = document.getElementById('rows');

    document.getElementById(`table`).innerHTML += `
      <tr id='\${Number(rows.value)+1}'>
      <td>
    <input type="text" id="product-search-\${Number(rows.value)+1}" class="form-control" placeholder="Search Product"
        onkeyup="fetchResults('product-search-\${Number(rows.value)+1}','product-list-\${Number(rows.value)+1}','product','product_\${Number(rows.value)+1}')">
          <input type="number" hidden="true" id="product_\${Number(rows.value)+1}" name="product_\${Number(rows.value)+1}" required>
          <div class=" list-group" id="product-list-\${Number(rows.value)+1}"></div>
</td>

<td>
<input type="string"  required class="form-control" name="batch_\${Number(rows.value)+1}" placeholder="Batch">
</td>


<td>
<input type="date"  min="3000-01-01" onfocus="this.min=new Date().toISOString().split('T')[0]" required class="form-control" name="expiry_\${Number(rows.value)+1}" placeholder="Expiry">
</td>

<td>
<input type="number" min="0"  class="form-control"  name="free_\${Number(rows.value)+1}" placeholder="Free">
</td>

<td>
<input type="number" min="1" required class="form-control"  name="qty_\${Number(rows.value)+1}" placeholder="Quantity">
</td>




</tr>
    `;

    rows.value=Number(rows.value)+1;

      if(Number(rows.value)==1){
        document.getElementById("submit-btn").innerHTML = `<input class="btn btn-primary" type="submit">`;
      }

  }


  function fetchResults(id,idOfCollapse,type,targetId){
      var doc = document.getElementById(id);

      if(doc.value!='') {
        const xhttp = new XMLHttpRequest();
        xhttp.onload = function() {
          document.getElementById(idOfCollapse).innerHTML=``;
          var results = JSON.parse(this.responseText);

          for(var i=0;i<results.length;i++){
            if(results[i].enabled){
              var mappings = ``;
              for(var j=0;j<results[i].mappings;j++){

              }
              var company= '';
              if(results[i].company!=null)
                company = results[i].company;
              document.getElementById(idOfCollapse).innerHTML+=`
              <button class='list-group-item button button-light'
              onclick="setAndCollapse(\${results[i].id},'\${targetId}','\${idOfCollapse}','\${id}','\${results[i].name}')">
                \${results[i].name} / \${company}
                </button>
                        `;

            }


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

    $(document).ready(function(){
      $('[data-toggle="tooltip"]').tooltip();
    });

  </script>

</c:if>