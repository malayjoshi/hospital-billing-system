<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body onload="window.print();">
	
	<h3>${heading}</h3>
    <table style="width: 3in;">
      <tr>
        <td>TID:${bill.tid}</td>
        <td>PID:${bill.patient.id}</td>
      </tr>
            
            <tr>
              <td colspan="2">Name:${bill.patient.fname} ${bill.patient.lname}</td>
            </tr>

            <tr>
              <td colspan="2">Doctor:${bill.doctor.name}</td>
            </tr> 
            <tr>
              <td colspan="2">Age & Sex: ${bill.patient.age} & ${bill.patient.sex}</td>
            </tr> 
            
            <c:set var="total" value="0"/>
            
            <tr>
            	<td colspan="2">Date:
	            	<c:if test="${type=='visit' }">
	            		<fmt:formatDate value="${bill.billingDate}" pattern="dd-MM-yyyy" />
	            		<c:set var="total" value="${bill.fees }" />
	            	</c:if>
	            	<c:if test="${type=='procedure' }">
	            		<fmt:formatDate value="${bill.date}" pattern="dd-MM-yyyy" />
	            		<c:set var="total" value="${bill.total }" />
	            	</c:if>
                 </td>
            </tr> 
            

          </table>
          <hr style='border-top-style:solid black;'>
          <table style="width: 3in;">
            <tr style='text-align:left;'>
              <th>Service</th>
              <th>Amount</th>
            </tr>
            
            <c:if test="${type=='visit' }">
           		<tr>
           			<td>Visit Charges</td>
           			<td>${ bill.fees }</td>
           		</tr>
           		<c:if test="${not empty bill.refundBill }">
           			<c:set var="total" value="${total+bill.refundBill.fees }" />
           			<tr>
           				<th style="text-align:left;">Discount</th>
           				<td>${bill.refundBill.fees }</td>
           			</tr>
           		</c:if>
           	</c:if>
           	<c:if test="${type=='procedure' }">
           		<c:forEach var="item" items="${bill.billItems }">
           			<tr>
           				<td>${item.procedure.procedure }</td>
           				<td>${item.rate}</td>
           			</tr>
           		</c:forEach>
           		
           		<c:if test="${not empty bill.refundBill }">
           			<c:set var="total" value="${total+bill.refundBill.total}"/>
           			<tr>
           				<th style="text-align:left;">Discount</th>
           				<td>${bill.refundBill.total }</td>
           			</tr>
           		</c:if>
           		
           	</c:if>
            	
            </table>
            
            
            
          <hr style='border-top-style:solid black;'>
          <h3>Total:${total}</h3>
	
		 
	
</body>
</html>