<%-- 
    Document   : staffHome
    Created on : Mar 8, 2025, 8:32:51 PM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Home Page</title>
        <%@include file="../../../fragments/styles.jspf" %>
    </head>
    <body>
        <%@include file="staffHeader.jspf" %>
        <div class="container-fluid">
            <div class="container-fluid">
                <div class="jumbotron alert alert-custom text-center">
                    <h1>Welcome, Staff ${sessionScope.user.lastName} ${sessionScope.user.firstName}</h1>
                    <p>This is the staff home page. You can manage products or perform staff-specific tasks here.</p>
                </div>
            </div>
            <div class="container">
                <div class="row list-group-item list-group-item-success">
                    <div class="col-sm-4">
                        <h2>Manage Category</h2>
                    </div>
                    <div class="col-sm-8">
                        <div class="list-group">
                            <c:url var="addCategoryUrl" value="/StaffController?action=addCategory"/>
                            <a href="${addCategoryUrl}" class="list-group-item">Add new Category</a>
                            <c:url var="categoriesListUrl" value="/StaffController?action=categoriesList"/>
                            <a href="${categoriesListUrl}" class="list-group-item">Categories List</a>
                        </div>
                    </div>
                </div>
                <div class="row list-group-item list-group-item-success">
                    <div class="col-sm-4">
                        <h2>Manage Product</h2>
                    </div>
                    <div class="col-sm-8">
                        <div class="list-group">
                            <c:url var="addProductUrl" value="/StaffController?action=addProduct"/>
                            <a href="${addProductUrl}" class="list-group-item">Add new Product</a>
                            <c:url var="productsListUrl" value="/StaffController?action=productsList"/>
                            <a href="${productsListUrl}" class="list-group-item">Products List</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
