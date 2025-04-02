<%-- 
    Document   : ProductsList
    Created on : Feb 25, 2025, 2:53:48 PM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products List</title>
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
            <c:choose>
                <c:when test="${ErrorMessage != null}">
                    <div class="jumbotron alert alert-danger text-center">
                        <h3 style="color: red">
                            ${ErrorMessage}
                        </h3>
                    </div>
                </c:when>
            </c:choose>
            <div class="jumbotron alert alert-custom text-center">
                <h1>List of Products</h1>
            </div>
            <!-- Sorting & Filtering Form -->
            <div class="container-fluid">
                <form action="${pageContext.request.contextPath}/StaffController" method="get" class="sort-filter-form">
                    <input type="hidden" name="action" value="sortProduct">
                    <!-- 🔹 Sort by Price -->
                    <label for="priceRange">Sắp xếp theo giá:</label>
                    <select name="priceRange" id="sortPrice" class="">
                        <option value="">-- Chọn --</option>
                        <option value="high">Giá cao → thấp</option>
                        <option value="low">Giá thấp → cao</option>
                    </select>

                    <!-- 🔹 Filter by Price Range -->
                    <label>Khoảng giá:</label>
                    <input type="number" name="minPrice" id="minPrice" class="" placeholder="Từ">
                    <input type="number" name="maxPrice" id="maxPrice" class="" placeholder="Đến">

                    <!-- 🔹 Filter by Name -->
                    <input type="text" name="productName" class="" placeholder="Tên sản phẩm">

                    <!-- 🔹 Filter by Discount -->
                    <label for="hasDiscount">Giảm giá:</label>
                    <select name="hasDiscount" id="hasDiscount" class="">
                        <option value="">-- Chọn --</option>
                        <option value="yes">Có giảm giá</option>
                        <option value="no">Không giảm giá</option>
                    </select>

                    <!-- 🔹 Filter by Category -->
                    <label for="category">Danh mục:</label>
                    <select name="category" id="category" class="">
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach var="c" items="${categoriesList}">
                            <option value="${c.typeId}">${c.categoryName}</option>
                        </c:forEach>
                    </select>

                    <!-- 🔹 Submit Button -->
                    <button type="submit" class="btn btn-primary">Lọc sản phẩm</button>
                </form>
            </div>
            <br>
            <table class="table table-bordered table-hover">
                <tr class="warning">
                    <td>Product ID</td>
                    <td>Product Name</td>
                    <td>Product Image</td>
                    <td>Brief</td>
                    <td>Posted Date</td>
                    <td>Type</td>
                    <td>Account</td>
                    <td>Unit</td>
                    <td>Price</td>
                    <td>Discount</td>
                    <td>Tools</td>
                </tr>
                <c:forEach var="pro" items="${productsList}">
                    <tr>
                        <td>${pro.productId}</td>
                        <td>${pro.productName}</td>
                        <td><img id="img-custom" src=".${pro.productImage}" alt=""></td>
                        <td>${pro.brief}</td>
                        <td>${pro.postedDate}</td>
                        <td>${pro.type.categoryName}</td>
                        <td>${pro.account.account}</td>
                        <td>${pro.unit}</td>
                        <td>${pro.price}</td>
                        <td>${pro.discount}</td>
                        <td>
                            <c:url var="urlDelete" value="/StaffController?action=deleteProduct&deleteId=${pro.productId}"></c:url>
                            <a href="${urlDelete}" class="btn btn-custom danger">
                                <img class="icon" src="images/iconDelete.png" alt=""/>
                            </a>
                            <c:url var="urlUpdate" value="/StaffController?action=updateProduct&updateId=${pro.productId}"></c:url>
                            <a href="${urlUpdate}" class="btn btn-custom success">
                                <img class="icon" src="images/iconUpdate.png" alt=""/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="11">
                        <c:url var="urlAddNew" value="/StaffController?action=addProduct"></c:url>
                        <a href="${urlAddNew}" class="btn btn-custom success">
                            <img class="icon" src="images/iconAddNew.png" alt=""/>
                        </a>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>

