<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.user.role=='MANAGER' }">
    <%@ include file="../Manager/header.jsp" %>
</c:if>
<c:if test="${sessionScope.user.role=='LAB TECH' }">
    <%@ include file="../Receptionist/header.jsp" %>
</c:if>

<br>

<div class="container mt-5">
    <c:if test="${not empty list}">
        <table class="table">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Qty Left</th>
            </tr>

            <c:forEach var="item" items="${list}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.no}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>