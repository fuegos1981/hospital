<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
    <head>
    <title>edit doctor</title>
    <style>
        <%@include file="/WEB-INF/styles/main.css"%>
    </style>
    </head>
    <body>
        <c:if test="${not empty locale }">
        <fmt:setLocale value="${locale}" scope="session" />
        </c:if>
        <fmt:setBundle basename="pagecontent"/>
        <form id="base-form" class="form" action="editDoctor" method="post">
            <input type="hidden" name="command" value="edit_doctor" />
            <input type="hidden" name="id" value="${id}" />
            <c:import url="/WEB-INF/pages/header.jsp" />
            <div id="base2">
                <h3 class="text-center text-white"><fmt:message key="hospital"/></h3>
                <div class="container">
                    <div id="base-row" class="row justify-content-center align-items-center">
                        <div id="base-column" class="col-md-6">
                            <div id="base-box" class="col-md-12">
                                <br/>
                                <h3 class="text-center text-info"><fmt:message key="Doctor"/></h3>
                                <div class="form-group">
                                    <label for="lastName" class="text-info"><fmt:message key="last_name"/>:</label><br>
                                    <input type="text" name="last_name" id="last_name" class="form-control" value="${doctor.getLastName()}">
                                    <label for="firstName" class="text-info"><fmt:message key="first_name"/>:</label><br>
                                    <input type="text" name="first_name" id="first_name" class="form-control" value="${doctor.getFirstName()}">
                                    <label for="login" class="text-info"><fmt:message key="login"/>:</label><br>
                                    <input type="login" name="login" id="login" class="form-control" value="${doctor.getLogin()}"/>
                                    <label for="password" class="text-info"><fmt:message key="password"/>:</label><br>
                                    <input type="password" name="password" id="password" class="form-control" value="${password}"/>
                                    <br/>
                                    <input type="radio" name="role"  value="ADMIN" ${role eq 'ADMIN'? 'checked':''}/><fmt:message key="admin"/>
                                    <input type="radio" name="role"  value="DOCTOR" ${role eq 'DOCTOR'? 'checked':''}/><fmt:message key="doctor"/>
                                    <input type="radio" name="role"  value="NURSE" ${role eq 'NURSE'? 'checked':''}/><fmt:message key="nurse"/>
                                </div>
                                <select class="form-control" name="category_id" id="categories">
                                    <option value=""><fmt:message key="select_category"/>...</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.getId()}" ${doctor.getCategory().getName() eq category.getName()? 'selected':''}>
                                            <c:out value="${category.toString()}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="form-group">
                                    <br/>
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