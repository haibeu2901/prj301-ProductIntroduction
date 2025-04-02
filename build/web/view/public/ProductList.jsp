<%-- 
    Document   : ProductList
    Created on : Mar 6, 2025, 4:45:07 PM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <%@include file="../../fragments/styles.jspf" %>
    </head>
    <body>
        <%@include file="../public/publicHeader.jspf" %>
        <div class="container text-center">
            <div class="jumbotron alert alert-custom text-center">
                <h3 style="color: red">
                    ${ErrorMessage}
                </h3>
                <h1>List of Products</h1>
            </div>
            <!-- Sorting & Filtering Form -->
            <div class="container-fluid">
                <form action="${pageContext.request.contextPath}/PublicController" method="get" class="sort-filter-form">
                    <input type="hidden" name="action" value="sort">
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
                    <td>Product Name</td>
                    <td>Product Image</td>
                    <td>Brief</td>
                    <td>Price</td>
                    <td>Discount</td>
                    <td>View</td>
                </tr>
                <c:forEach var="pro" items="${productsList}">
                    <tr>
                        <td>${pro.productName}</td>
                        <td><img id="img-custom" src=".${pro.productImage}" alt=""></td>
                        <td>${pro.brief}</td>
                        <td>${pro.price} VNĐ</td>
                        <td>${pro.discount}%</td>
                        <td>
                            <c:url var="urlView" value="/PublicController?action=productDetail&productId=${pro.productId}"></c:url>
                            <a href="${urlView}" class="">
                                <button type="button" class="btn btn-primary">View</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
