<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
    <head>
    <title>edit patient</title>
    <style>
        <%@include file="/WEB-INF/styles/main.css"%>
    </style>
    </head>
    <body>
     <c:if test="${not empty locale }">
                <fmt:setLocale value="${locale}" scope="session" />
     </c:if>
     <fmt:setBundle basename="pagecontent"/>
     <c:import url="/WEB-INF/pages/header.jsp" />
        <div id="base">
            <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
            <div class="container">
                <div id="base-row" class="row justify-content-center align-items-center">
                    <div id="base-column" class="col-md-6">
                        <div id="base-box" class="col-md-12">
                            <form id="base-form" class="form" action="editPatient" method="post">
                                <input type="hidden" name="command" value="edit_patient" />
                                <h3 class="text-center text-info">Create patient</h3>
                                <div class="form-group">
                                    <label for="lastName" class="text-info">Last name:</label><br>
                                    <input type="text" name="lastName" id="lastName" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="firstName" class="text-info">firstName:</label><br>
                                    <input type="text" name="firstName" id="firstName" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="birthday" class="text-info">birthday:</label><br>
                                    <input type="date" name="birthday" id="birthday" class="form-control" lang ="${locale}">
                                </div>
                                <div class="form-group">
                                    <input type="radio" name="gender"  value="male" checked ="checked"/> male
                                    <input type="radio" name="gender"  value="female"/>female
                                </div>
                                <div class="form-group">
                                    <input type="submit" name="submit" class="btn btn-info btn-md" value="submit">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>