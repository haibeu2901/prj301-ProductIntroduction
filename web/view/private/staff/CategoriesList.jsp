<%-- 
    Document   : CategoriesList
    Created on : Feb 25, 2025, 9:02:39 AM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Categories List</title>
        <%@include file="../../../fragments/styles.jspf" %>
    </head>
    <body>
        <c:choose>
            <c:when test="${user.roleInSystem == 1}">
                <%@include file="../admin/adminHeader.jspf" %>
            </c:when>
            <c:otherwise>
                <%@include file="../staff/staffHeader.jspf" %>
            </c:otherwise>
        </c:choose>
        <div class="container text-center">
            <c:if test="${not empty sessionScope.ErrorMessage}">
                <div class="jumbotron alert alert-danger text-center">
                    <h3 style="color: red">
                        <c:out value="${sessionScope.ErrorMessage}"/>
                    </h3>
                </div>
            </c:if>
            <div class="jumbotron alert alert-custom text-center">
                <h1>List of Categories</h1>
            </div>
            <!-- Sorting & Filtering Form -->
            <div class="container-fluid">
                <form action="${pageContext.request.contextPath}/StaffController" method="get" class="sort-filter-form">
                    <input type="hidden" name="action" value="sortCategory">

                    <!-- 🔹 Filter by Category ID -->
                    <label>Category ID:</label>
                    <input type="number" name="catId" class="" placeholder="ID Danh mục">

                    <!-- 🔹 Filter by Category Name -->
                    <label>Tên Danh mục:</label>
                    <input type="text" name="catName" class="" placeholder="Tên Danh mục">

                    <!-- 🔹 Submit Button -->
                    <button type="submit" class="btn btn-primary">Lọc Danh mục</button>
                </form>
            </div>
            <br>
            <table class="table table-bordered table-hover">
                <tr class="warning">
                    <td>Type ID</td>
                    <td>Category Name</td>
                    <td>Memo</td>
                    <td>Tools</td>
                </tr>
                <c:forEach var="cat" items="${categoriesList}">
                    <tr>
                        <td>${cat.typeId}</td>
                        <td>${cat.categoryName}</td>
                        <td>${cat.memo}</td>
                        <td>
                            <c:url var="urlDelete" value="/StaffController?action=deleteCategory&deleteId=${cat.typeId}"></c:url>
                            <a href="${urlDelete}" class="btn btn-custom danger">
                                <img class="icon" src="images/iconDelete.png" alt=""/>
                            </a>
                            <c:url var="urlUpdate" value="/StaffController?action=updateCategory&updateId=${cat.typeId}"></c:url>
                            <a href="${urlUpdate}" class="btn btn-custom success">
                                <img class="icon" src="images/iconUpdate.png" alt=""/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="4">
                        <c:url var="urlAddNew" value="/StaffController?action=addCategory"></c:url>
                        <a href="${urlAddNew}" class="btn btn-custom success">
                            <img class="icon" src="images/iconAddNew.png" alt=""/>
                        </a>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
