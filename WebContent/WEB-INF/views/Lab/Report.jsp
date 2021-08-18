<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<title>Print Report</title>
</head>
<body onload="window.print();">
	
	<h1 style='text-align:center;'>${heading}</h1>
	<p style='text-align:center;'>${subHeading}</p>
	
	<table style='width:100%;'>
		<tr style='text-align:left;'>
			<th>PID: ${bill.patient.id }</th>
			<th>TID: ${bill.tid }</th>
			<th>Date: <fmt:formatDate value="${bill.date}" pattern="dd-MM-yyyy" /></th>
		</tr>
		<tr style='text-align:left;'>
			<th>Name: ${bill.patient.fname} ${bill.patient.lname }</th>
			<th>Age And Sex: ${bill.patient.age} ${bill.patient.sex}</th>
			<th>Doctor: ${bill.doctor.name}</th>
		</tr>
	</table>
	<br><br>
	
	<br>
	<p style='text-align:center;border-top:1px solid black;'>H-High, L-Low</p>
	
	<c:set var="i" value="${0}"/>  
	
	<table style='width:100%;'>
		<c:forEach var="cat" items="${categories}">
			<tr><th colspan="5"></th></tr>
			<tr>
				<th colspan="5">
					<h3 style='text-align:center;'>${cat.name}</h3>
				</th>
				
			</tr>
			<c:forEach var="test" items="${tests}">
				<c:if test="${not empty test.category}">
					<c:if test="${test.category.id==cat.id}">
						
						<c:forEach var="parameter" items="${test.parameters}">
							<c:set var="hl" value=""/>
							<tr>
								<td style='width:45%;'>${parameter.name}</td>
								
								<td style='width:11%;'>
									
									<c:if test="${not empty parameter.unit}">
										<fmt:parseNumber var="value" value="${values[i].value}" />
										<fmt:parseNumber var="low" value="${parameter.lowerRange}" />
										<fmt:parseNumber var="high" value="${parameter.upperRange}" />
										
										<c:choose>
										  <c:when test="${value<low}">
										    <u><b>${value}</b></u>
											<c:set var="hl" value="L"/>
										  </c:when>
										  <c:when test="${value>high}">
											<u><b>${value}</b></u>
											<c:set var="hl" value="H"/>		    
										  </c:when>
										  <c:otherwise>
										    ${value}
										  </c:otherwise>
										</c:choose>
								
									</c:if>
									<c:if test="${empty parameter.unit }">
										${values[i].value}
									</c:if>	
								
									<c:set var="i" value="${i+1}"/>  
								</td>
								
								<td style='width:11%;'>${hl}</td>
								
								<td style='width:11%;'>
									<c:if test="${not empty parameter.unit}">
									${parameter.unit}
									</c:if>
								</td>
								<td style='width:20%;'>
									<c:if test="${not empty parameter.unit}">
										${parameter.lowerRange } - ${parameter.upperRange }
									</c:if>
								</td>
							</tr>
						</c:forEach>				
					</c:if>
				</c:if>
			
			</c:forEach>
		
				
		</c:forEach>
		
	</table>
	
	<br><br>
	<h4 style="text-align:center;">xxxxxxxxxxxxxxxxxxxxxxxxxxxx End Of Report xxxxxxxxxxxxxxxxxxxxxxxxxxxx</h4>
	
</body>

</html>