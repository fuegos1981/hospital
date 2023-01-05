<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!DOCTYPE html>
<html>
    <head>
        <title>Login hospital</title>
        <style>
            <%@include file="/WEB-INF/styles/main.css"%>
        </style>
    </head>
    <body>
        <fmt:requestEncoding value="UTF-8" />
        <c:if test="${not empty locale }">
            <fmt:setLocale value="${locale}" scope="session" />
        </c:if>
        <fmt:setBundle basename="pagecontent"/>
        <form id="base-form" class="form" action="login" method="post">
        <c:import url="/WEB-INF/pages/header.jsp" />
        <div id="base">
            <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
            <div class="container">
                <div id="base-row" class="row justify-content-center align-items-center">
                    <div id="base-column" class="col-md-6">
                        <div id="base-box" class="col-md-12">
                            <input type="hidden" name="command" value="login" />
                            <br>
                            <h3 class="text-center text-info"><fmt:message key="authentication"/></h3>
                            <div class="form-group">
                                <label for="username" class="text-info"><fmt:message key="username"/>:</label><br>
                                <input type="text" name="username" id="username" class="form-control" value="${username}">
                            </div>
                            <div class="form-group">
                                <label for="password" class="text-info"><fmt:message key="password"/>:</label><br>
                                <input type="password" name="password" id="password" class="form-control" value="${password}"/>
                            </div>
                            <tf:errortag text="${message}"/>
                            <div class="form-group">
                                <input type="submit" name="submit" class="btn btn-info btn-md" value="<fmt:message key='login_submit'/>"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </body>
</html>
