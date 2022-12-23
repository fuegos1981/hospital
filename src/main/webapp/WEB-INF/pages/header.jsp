<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<html>
    <head>
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
    <div class="form-group">
        <input type="submit" name="submit_us" class="btn btn-info btn-md" value='<fmt:message key="en"/>'/>
        <input type="submit" name="submit_ua" class="btn btn-info btn-md" value='<fmt:message key="ua"/>'/>

            <a class="btn btn-info btn-md" href="/hospital/main?command=admin"><fmt:message key="main"/></a>
            <a class="btn btn-info btn-md" href="controller?command=logout"><fmt:message key="logout"/></a>

    </div>
    </body>
</html>