<%-- 
    Document   : AddProduct
    Created on : Feb 25, 2025, 8:56:33 PM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product</title>
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
        <div class="container">
            <div class="jumbotron alert alert-custom text-center">
                <h1>Add New Product</h1>
            </div>
            <div>
                <form class="form-horizontal" action="${pageContext.request.contextPath}/StaffController?action=addProduct" method="post">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="productId">Product ID:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" placeholder="Enter Product ID" name="productId">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="productName">Product Name:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="productName" placeholder="Enter Product Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="productImage">Product Image:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="productImage" placeholder="Enter Product Image Link">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="brief">Brief:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="brief" placeholder="Enter Brief">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="postedDate">Posted Date:</label>
                        <div class="col-sm-10">
                            <input type="date" class="form-control" name="postedDate" placeholder="Enter Posted Date ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="typeID">Type ID:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="typeID" placeholder="Enter Type ID">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="account">Account:</label>
                        <div class="col-sm-10">
                            <input readonly type="text" value="${user.account}" class="form-control" name="account" placeholder="Enter Account">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="unit">Unit:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="unit" placeholder="Enter Unit">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="price">Price:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="price" placeholder="Enter Price">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="discount">Discount:</label>
                        <div class="col-sm-10">
                            <input type="number" class="form-control" name="discount" placeholder="Enter Discount">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-success">ADD new</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
