<%-- 
    Document   : AccountsList
    Created on : Feb 25, 2025, 9:11:21 AM
    Author     : beu29
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accounts List</title>
        <%@include file="../../../fragments/styles.jspf" %>
    </head>
    <body>
        <%@include file="../admin/adminHeader.jspf" %>
        <div class="container text-center">
            <c:if test="${not empty sessionScope.ErrorMessage}">
                <div class="jumbotron alert alert-danger text-center">
                    <h3 style="color: red">
                        <c:out value="${sessionScope.ErrorMessage}"/>
                    </h3>
                </div>
            </c:if>
            <div class="jumbotron alert alert-custom text-center">
                <h1>List of Accounts</h1>
            </div>
            <!-- Sorting & Filtering Form -->
            <div class="container-fluid">
                <form action="${pageContext.request.contextPath}/AdminController" method="get" class="sort-filter-form">
                    <input type="hidden" name="action" value="sortAccount">
                    <!-- 🔹 Filter by Account ID -->
                    <label>Mã tài khoản:</label>
                    <input type="text" name="accountId" class="" placeholder="Account ID">

                    <!-- 🔹 Filter by Name -->
                    <label>Tên nhân sự:</label>
                    <input type="text" name="fullName" class="" placeholder="Nhập tên nhân sự">

                    <!-- 🔹 Filter by Gender -->
                    <label for="gender">Giới tính:</label>
                    <select name="gender" class="">
                        <option value="">-- Chọn --</option>
                        <option value="male">Nam</option>
                        <option value="female">Nữ</option>
                    </select>

                    <!-- 🔹 Filter by Phone -->
                    <label>Số điện thoại:</label>
                    <input type="text" name="phone" class="" placeholder="Số điện thoại">

                    <!-- 🔹 Filter by is Use -->
                    <label for="isUse">Có đang hoạt động:</label>
                    <select name="isUse" id="hasDiscount" class="">
                        <option value="">-- Chọn --</option>
                        <option value="true">Có</option>
                        <option value="false">Không</option>
                    </select>

                    <!-- 🔹 Filter by Role In System -->
                    <label for="roleInSystem">Vai trò:</label>
                    <select name="roleInSystem" id="hasDiscount" class="">
                        <option value="">-- Chọn --</option>
                        <option value="1">Administrator</option>
                        <option value="2">Nhân viên</option>
                    </select>

                    <!-- 🔹 Submit Button -->
                    <button type="submit" class="btn btn-primary">Lọc tài khoản</button>
                </form>
            </div>
            <br>
            <table class="table table-bordered table-hover">
                <tr class="warning">
                    <td>Account</td>
                    <td>Full Name</td>
                    <td>Birthday</td>
                    <td>Gender</td>
                    <td>Phone</td>
                    <td>is Used</td>
                    <td>Role in System</td>
                    <td>Tools</td>
                </tr>
                <c:forEach var="acc" items="${accountsList}">
                    <tr>
                        <td>${acc.account}</td>
                        <td>${acc.lastName} ${acc.firstName}</td>
                        <td>${acc.birthday}</td>
                        <td>${acc.gender?"Male":"Female"}</td>
                        <td>${acc.phone}</td>
                        <td>${acc.isUse}</td>
                        <td>${acc.roleInSystem == 1 ? "Administrator" : "Staff"}</td>
                        <td>
                            <c:url var="urlDelete" value="/AdminController?action=deleteAccount&deleteId=${acc.account}"></c:url>
                            <a href="${urlDelete}" class="btn btn-custom danger">
                                <img class="icon" src="images/iconDelete.png" alt=""/>
                            </a>
                            <c:url var="urlUpdate" value="/AdminController?action=updateAccount&updateId=${acc.account}"></c:url>
                            <a href="${urlUpdate}" class="btn btn-custom success">
                                <img class="icon" src="images/iconUpdate.png" alt=""/>
                            </a>
                            <c:url var="urlActivate" value="/AdminController?action=activateAccount&activeId=${acc.account}"></c:url>
                            <a href="${urlActivate}" class="">
                                <button type="button" class="btn btn-primary">${acc.isUse ? "Dea" : "A"}ctive</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="8">
                        <c:url var="urlAddNew" value="/AdminController?action=addAccount"></c:url>
                        <a href="${urlAddNew}" class="btn btn-custom success">
                            <img class="icon" src="images/iconAddNew.png" alt=""/>
                        </a>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
