<%-- 
    Document   : index
    Created on : Mar 7, 2025, 9:44:10 PM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../../fragments/styles.jspf" %>
        <title>Home</title>
    </head>
    <body>
        <!--Navbar-->
        <%@include file="../public/publicHeader.jspf" %>
        </br>
        <div class="container-fluid">
            <!-- Search Form -->
            <div class="container-fluid">
                <form action="${pageContext.request.contextPath}/PublicController" method="get" class="sort-filter-form">
                    <!-- 🔹 Search by Name -->
                    <input type="hidden" name="action" value="search">
                    <input type="text" name="searchBar" id="searchBar" class="form-control" placeholder="Nhập tên sản phẩm">
                    <!-- 🔹 Submit Button -->
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                </form>
            </div>

        </div>

        </br>

        <!--Some example product--> 
        <h2>Sample Products</h2>

        <div class="product-container">
            <div class="product-card">
                <img src="images/sanPham/boNoiInoxSunhouse.jpg" alt="Bộ nồi Inox 3 đáy SUNHOUSE">
                <div class="product-name">Bộ nồi Inox 3 đáy SUNHOUSE</div>
                <div class="product-price">399,000 VNĐ</div>
                <a href="${pageContext.request.contextPath}/PublicController?action=productDetail&productId=SHG2303MRA" class="view-detail">View</a>
            </div>

            <div class="product-card">
                <img src="images/sanPham/tuLanhELECTROLUX.png" alt="Nồi Áp Suất Cơ Nagakawa">
                <div class="product-name">Tủ Lạnh ELECTROLUX Inverter 524 Lít</div>
                <div class="product-price">22,590,000 VNĐ</div>
                <a href="${pageContext.request.contextPath}/PublicController?action=productDetail&productId=EHE5224B-A" class="view-detail">View</a>
            </div>

            <div class="product-card">
                <img src="images/sanPham/gheThuGian.jpg" alt="Ghế thư giãn">
                <div class="product-name">Ghế thư giãn</div>
                <div class="product-price">699,000 VNĐ</div>
                <a href="${pageContext.request.contextPath}/PublicController?action=productDetail&productId=4062373305" class="view-detail">View</a>
            </div>

            <div class="product-card">
                <img src="images/sanPham/taDeoChanCaoCap.jpg" alt="Tạ đeo chân cao cấp">
                <div class="product-name">Tạ đeo chân cao cấp</div>
                <div class="product-price">315,000 VNĐ</div>
                <a href="${pageContext.request.contextPath}/PublicController?action=productDetail&productId=7823080768" class="view-detail">View</a>
            </div>

            <div class="product-card">
                <img src="images/sanPham/samsungGalaxyNote10Plus.jpg" alt="Bàn Trà Sofa IGEA">
                <div class="product-name">Samsung Galaxy Note 10 Plus</div>
                <div class="product-price">25,450,000 VNĐ</div>
                <a href="${pageContext.request.contextPath}/PublicController?action=productDetail&productId=10NOTEP256" class="view-detail">View</a>
            </div>

            <div class="product-card">
                <img src="images/sanPham/aoSoMiNamNganTay.jpg" alt="Tủ lạnh Toshiba Inverter">
                <div class="product-name">Áo Sơ Mi Nam Trơn Ngắn Tay</div>
                <div class="product-price">99,000 VNĐ</div>
                <a href="${pageContext.request.contextPath}/PublicController?action=productDetail&productId=1688993802" class="view-detail">View</a>
            </div>
        </div>

        <!--Recently viewed product's using cookie-->
        <%@include file="../../fragments/recentProducts.jspf" %>

        <!--Suggested Products based on Cookie-->
        <%@include file="../../fragments/suggestedProducts.jspf" %>

    </body>
</html>