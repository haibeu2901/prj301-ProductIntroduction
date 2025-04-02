<%-- 
    Document   : AddCategory
    Created on : Feb 25, 2025, 8:29:26 PM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Category</title>
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
                <h1>Add New Category</h1>
            </div>
            <div>
                <form class="form-horizontal" action="${pageContext.request.contextPath}/StaffController?action=addCategory" method="post">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="categoryName">Category Name:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" placeholder="Enter Category Name" name="categoryName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="memo">Memo:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="memo" placeholder="Enter memo">
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
