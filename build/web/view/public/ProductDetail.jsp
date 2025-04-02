<%-- 
    Document   : ProductsDetail
    Created on : Mar 6, 2025, 3:53:47 PM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
        <style>
            .product {
                display: flex;
                flex-wrap: wrap;
                gap: 30px;
                padding: 40px;
                background: white;
                border-radius: 15px;
                border: 3px solid #ddd;
                box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
                max-width: 1350px;
                margin: 30px auto;
                align-items: center;
                position: relative;
            }

            .col-left-custom {
                flex: 1;
                min-width: 450px;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 30px;
                position: relative;
                border-left: 1px solid #ddd;
            }

            /* 🔹 Black separator line */
            .col-left-custom::after {
                content: "";
                position: absolute;
                top: 5%;
                right: -15px; /* Ensures visibility */
                width: 5px;
                height: 90%;
                background: black;
            }

            /* 🔹 Image Styling */
            .col-left-custom img {
                width: 100%;
                max-width: 450px;
                border-radius: 15px;
                transition: transform 0.3s ease-in-out;
                border: 2px solid #ccc;
            }

            .col-left-custom img:hover {
                transform: scale(1.1);
            }

            .col-right-custom {
                flex: 2;
                display: flex;
                flex-direction: column;
                justify-content: center;
                padding: 30px;
            }

            /* 🔹 Bigger product name */
            .col-right-custom h3 {
                font-size: 36px;
                margin-bottom: 20px;
                color: #333;
            }

            /* 🔹 Bigger product description */
            .col-right-custom p {
                font-size: 32px;
                color: #555;
                line-height: 1.8;
            }

            /* 🔹 Price */
            .product-price {
                font-size: 46px;
                font-weight: bold;
                color: #e91e63;
                margin-top: 20px;
            }

            /* 🔹 Discount */
            .product-discount {
                font-size: 36px;
                font-weight: bold;
                color: #28a745;
                margin-top: 15px;
            }

        </style>
        <%@include file="../../fragments/styles.jspf" %>
    </head>
    <body>
        <%@include file="../public/publicHeader.jspf" %>
        <div class="container-fluid">
            <div class="product">
                <div class="col-lg-4">
                    <img src=".${p.productImage}" alt="${p.productName}">
                </div>
                <div style="margin-left: 30px" class="col-lg-6">
                    <div><h3 style="font-size: 34px">${p.productName}</h3></div>
                    <div><p style="font-size: 20px">${p.brief}</p></div>
                    <div class="product-price"><p style="font-size: 34px; color: red;">Giá: ${p.priceToString} VNĐ</p></div>
                    <div class="product-discount">Giảm giá: ${p.discount}%</div>
                </div>
            </div>
        </div>

        <!--Recently viewed product's using cookie-->
        <%@include file="../../fragments/recentProducts.jspf" %>
        
        <!--Suggested Products based on Cookie-->
        <%@include file="../../fragments/suggestedProducts.jspf" %>
    </body>
</html>
