<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<br>
<div class='container mt-5'>
  <h3>Consolidated Bill By PID</h3>
  <form class="row form-group" action="${contextPath}/common/bills/check-consoldated-bill-req">
    <input placeholder="Enter PID" required name="pid" type="number" class="form-control col-md-4">

    <input placeholder="Start Date" required name="startDate" type="date" class="form-control col-md-4">

    <input placeholder="End Date" required name="endDate" type="date" class="form-control col-md-4">

    <br>

    <input type="submit" class="btn btn-primary">
  </form>
  <c:if test="${not empty bills}">

  <div class="row" id="print-content" style="display: none;width: 100%;">

    <h1 style='text-align: center;'>${heading}</h1>
    <p style="text-align: center;margin-top: -20px;">${subHeader}</p>
    <h3 style="text-align: center">Consolidated Bill</h3>
    <table style="width: 100%;">
      <tr style="text-align: left">
        <td>PID:${bills[0].patientDTO.id }</td>
        <td>Name:${bills[0].patient}</td>
        <td>Age & Sex: ${bills[0].patientDTO.age} & ${bills[0].patientDTO.sex}</td>

      </tr>

      <tr style="text-align: left">
        <td>Guardian: ${bills[0].patientDTO.guardian }</td>
        <td colspan="2">Address:${bills[0].patientDTO.address}</td>

      </tr>

      <tr><td colspan="3"><hr></td></tr>
      <tr style="text-align: left;">
        <th colspan="2">Service</th>
        <th>Amount</th>
      </tr>
      <c:set var="total" value="0"/>
      <c:set var = "discount" value="0"/>
      <c:forEach var="bill" items="${bills}">
        <c:if test="${not empty bill.billItems}">

          <tr><td colspan="3"><hr></td></tr>
          <tr>
            <td colspan="3"><b>TID:${bill.tid}, Date:
              <fmt:formatDate value="${bill.billingDate}" pattern="dd-MM-yyyy" />
            </b>
            </td>
          </tr>

          <c:forEach var="item" items="${bill.billItems }">
            <tr>
              <td colspan="2">${item.name }</td>
              <td>${item.rate }</td>
            </tr>
          </c:forEach>

          <c:set var="total" value="${total+bill.fees }" />

        </c:if>

        <c:if test="${empty bill.billItems}">
          <c:if test="${bill.fees > 0}">

            <tr><td colspan="3"><hr></td></tr>
            <tr>
              <td colspan="3"><b>Date:
                <fmt:formatDate value="${bill.billingDate}" pattern="dd-MM-yyyy" />
              </b>
              </td>
            </tr>

            <tr>
              <td colspan="2">Visit Charges (${bill.doctor})</td>
              <td>${ bill.fees }</td>
            </tr>

            <c:set var="total" value="${total+bill.fees }" />
          </c:if>


          <c:if test="${bill.fees < 0 }">
            <c:set var="discount" value="${discount+bill.fees }" />
          </c:if>

        </c:if>




    </c:forEach>

      <tr><td colspan="3"><hr></td></tr>
      <tr>
        <td colspan="2">Total</td>
        <td>${total}</td>
      </tr>
      <tr>
        <td colspan="2">Discount</td>
        <td>${discount}</td>
      </tr>

      <tr><td colspan="3"><hr></td></tr>

      <tr>
        <td colspan="2"><b>Net Total</b></td>
        <td><b>${total+discount}</b></td>
      </tr>
      <tr><td colspan="3"></td></tr>

      <tr><td colspan="3"></td></tr>
      <tr><td colspan="3"></td></tr>
      <tr>
        <td colspan="3" style="text-align: right;">
          <b>Doctor's Signature</b>
        </td>
      </tr>
    </table>

  </div>

  <button onclick="printDiv('print-content')" class="btn btn-primary">Print Bill</button>
  <script type="text/javascript">
    function printDiv(divName) {
      var printContents = document.getElementById(divName).innerHTML;
      w=window.open("_blank");
      w.document.write(printContents);
      w.print();
      w.close();
    }
  </script>
  </c:if>
