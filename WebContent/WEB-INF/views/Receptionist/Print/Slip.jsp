<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body onload="window.print();">
	
	<h1 style='text-align: center;'>${heading}</h1>
	<p style="text-align: center;margin-top: -20px;">${subHeader}</p>
	
	<div style='height:100px;' >
		
		<table style='width: 100%;'>
			<tr>
				<th style='text-align: left;'>Date: <fmt:formatDate value="${bill.billingDate}" pattern="dd-MM-yyyy " /></th>
				<th style='text-align: left;'>Patient ID: ${bill.patient.id }</th>
				<th style='text-align: left;'>Doctor: ${bill.doctor.name }</th>
			</tr>
			<tr>
				<th colspan="2" style='text-align: left;'>Name: ${bill.patient.fname} ${bill.patient.lname}</th>
				<th style='text-align: left;'>Age and Sex: ${bill.patient.age} ${bill.patient.sex}</th>
			</tr>
			<tr>
				<th colspan="3" style='text-align: left;'>Address: ${bill.patient.address}</th>
			</tr>
			<tr>
				<th colspan="3" style='text-align: left;'>Guardian: ${bill.patient.guardian}</th>
			</tr>	
		</table>
		
	
	</div>
	<hr style='border-top-style:solid black;margin-top:-5px;'>
	<table style="border-right:1px solid black;margin-top: -10px;">
		
		<tr>
			<th style="text-align: left;">
				DOM LMP<br>
				Wt, PR, BP, T<br>
				CL, CY, J, A, LN, PO<br>
				CVS<br>
				RS<br>
				PA<br>
				CNS<br>
				Hemogram<br>
				LFT KFT<br>
				Diabetic Profile<br>
				Lipid Profile<br>
				ECG<br>
				X-Ray<br>
				USG<br>
				ECHO<br>
				EEG<br>
				Spirometry<br>
				ABG<br>
				Colposcopy<br>
				T.M.T<br>
				T.S.H<br>
				I.U.I<br>
			</th>
	
		</tr>
	
	</table>
	<br><br>
	<table>
		<tr>
			<td style="font-size: 13px;">
				${footer }
			</td>
		</tr>
		
	</table>
	
</body>
</html>