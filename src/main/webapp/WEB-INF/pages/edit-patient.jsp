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
        <div id="base-edit-patient">
            <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
            <div class="container">
                <div id="base-row" class="row justify-content-center align-items-center">
                    <div id="base-column" class="col-md-6">
                        <div id="base-box" class="col-md-12">
                            <form id="base-form" class="form" action="editPatient" method="post">
                                <input type="hidden" name="command" value="edit_patient" />
                                <h3 class="text-center text-info">Create patient</h3>
                                <div class="form-group">
                                    <label for="lastName" class="text-info"><fmt:message key="last_name"/>:</label><br>
                                    <input type="text" name="lastName" id="lastName" class="form-control" value="${lastName}"/>
                                </div>
                                <div class="form-group">
                                    <label for="firstName" class="text-info"><fmt:message key="first_name"/>:</label><br>
                                    <input type="text" name="firstName" id="firstName" class="form-control" value="${firstName}"/>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="text-info"><fmt:message key="email"/>:</label><br>
                                    <input type="email" name="email" id="email" class="form-control" value="${email}"/>
                                </div>
                                <div class="form-group">
                                    <label for="birthday" class="text-info"><fmt:message key="birthday"/>:</label><br>
                                    <input type="date" name="birthday" id="birthday" class="form-control" lang ="${locale}" value="${birthday}">
                                </div>
                                <div class="form-group">
                                    <input type="radio" name="gender"  value="male" ${requestScope['gender'] == 'male'? 'checked':''}/><fmt:message key="male"/>
                                    <input type="radio" name="gender"  value="female" ${requestScope['gender'] == 'female'? 'checked':''}/><fmt:message key="female"/>
                                </div>

                                <div class="form-group">
                                    <input type="submit" name="submit" class="btn btn-info btn-md" value="<fmt:message key='create_patient'/>">
                                    <label  class="text-danger"><c:out value="${message}" ></c:out></label>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>