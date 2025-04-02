<%-- 
    Document   : UpdateAccount
    Created on : Feb 27, 2025, 9:10:44 PM
    Author     : beu29
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Account</title>
        <%@include file="../../../fragments/styles.jspf" %>
    </head>
    <body>
        <%@include file="../admin/adminHeader.jspf" %>
        <div class="container">
            <div class="jumbotron alert alert-custom text-center">
                <h1>Update Account</h1>
            </div>
            <div>
                <form class="form-horizontal" action="${pageContext.request.contextPath}/AdminController?action=updateAccount" method="post">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="account">Account:</label>
                        <div class="col-sm-10">
                            <input readonly value="${acc.account}" type="text" class="form-control" name="account">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="pass">Password:</label>
                        <div class="col-sm-10">
                            <input type="password" value="${acc.pass}" class="form-control" name="pass" placeholder="Enter Password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="lastName">Last Name:</label>
                        <div class="col-sm-10">
                            <input type="text" value="${acc.lastName}" class="form-control" name="lastName" placeholder="Enter Last Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="firstName">First Name:</label>
                        <div class="col-sm-10">
                            <input type="text" value="${acc.firstName}" class="form-control" name="firstName" placeholder="Enter First Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="birthday">Birthday:</label>
                        <div class="col-sm-10">
                            <input type="date" value="${acc.birthday}" class="form-control" name="birthday" placeholder="Enter Birthday">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="gender">Gender:</label>
                        <div class="col-sm-10">
                            <input type="radio" class="" name="gender" value="Male" ${acc.gender==true?"checked":""}>
                            <label class="radion-inline" for="Male">Male</label>
                            <input type="radio" class="" name="gender" value="Female" ${acc.gender==false?"checked":""}>
                            <label class="radion-inline" for="Female">Female</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="phone">Phone:</label>
                        <div class="col-sm-10">
                            <input type="text" value="${acc.phone}" class="form-control" name="phone" placeholder="Enter Phone number">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="roleInSystem">Role in System:</label>
                        <div class="col-sm-10">
                            <select name="roleInSystem" class="form-control">
                                <option value="1">Administrator</option>
                                <option value="2">Manager</option>
                                <option value="3">Staff</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="isUse">is Use:</label>
                        <div class="col-sm-10">
                            <label class="checkbox-inline"><input type="checkbox" name="isUse" value="true" ${acc.isUse==true?"checked":""}>Is Active</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-success">UPDATE</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
