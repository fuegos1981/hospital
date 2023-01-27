<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
    <head>
    <title>edit</title>
    <style>
        <%@include file="/WEB-INF/styles/main.css"%>
    </style>
    </head>
    <body>
        <c:if test="${not empty locale }">
            <fmt:setLocale value="${locale}" scope="session" />
        </c:if>
        <fmt:setBundle basename="pagecontent"/>
        <form id="base-form" class="form" action="editSimple" method="post">
            <c:import url="/WEB-INF/pages/header.jsp" />
            <input type="hidden" name="command" value="edit_simple" />
            <input type="hidden" name="name" value="${name}" />
            <input type="hidden" name="id" value="${id}" />
            <div id="base">
                <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
                <div class="container">
                    <div id="base-row" class="row justify-content-center align-items-center">
                        <div id="base-column" class="col-md-6">
                            <div id="base-box" class="col-md-12">
                                <br/><br/>
                                <h3 class="text-center text-info"><fmt:message key="${name}"/></h3>
                                <br/>
                                <div class="form-group">
                                    <label for="simple" class="text-info"><fmt:message key="Name"/>:</label>
                                    <input type="text" name="simple" id="simple" class="form-control" value="${simple}"/>
                                </div>
                                <br/>
                                <div class="form-group">
                                    <input type="submit" name="submit" class="btn btn-info btn-md" value="<fmt:message key='save'/>">
                                    <tf:errortag text="${message}"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>