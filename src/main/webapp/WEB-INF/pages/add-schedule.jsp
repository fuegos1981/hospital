<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
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
        <fmt:requestEncoding value="UTF-8" />
        <c:if test="${not empty locale }">
            <fmt:setLocale value="${locale}" scope="session" />
        </c:if>
        <fmt:setBundle basename="pagecontent"/>
        <form id="base-form" class="form" action="addSchedule" method="post">
            <c:import url="/WEB-INF/pages/header.jsp" />
            <input type="hidden" name="command" value="add_schedule" />
            <input type="hidden" name="path_return" value="${path_return}" />
            <input type="hidden" name="id" value="${id}" />
            <div id="base">
                <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
                <div class="container">
                <div id="base-row" class="row justify-content-center align-items-center">
                    <div id="base-column" class="col-md-6">
                        <div id="base-box" class="col-md-12">
                            <br/>
                            <h3 class="text-center text-info"><fmt:message key="add_visit"/></h3>
                            <br/>
                            <select class="form-control" name="doctor_id" id="doctors">
                                <option value=""><fmt:message key="select_doctor"/>...</option>
                                <c:forEach var="doctor" items="${doctors}" varStatus="status">
                                    <option value="${doctor.getId()}" ${schedule.getDoctorId() == doctor.getId()? 'selected':''}>
                                    <c:out value="${doctor.toString()}"/>
                                    </option>
                                </c:forEach>
                            </select>
                            <br>
                            <select class="form-control" name="patient_id" id="patients">
                                <option value=""><fmt:message key="select_patient"/>...</option>
                                <c:forEach var="patient" items="${patients}" varStatus="status">
                                    <option value="${patient.getId()}" ${schedule.getPatientId() == patient.getId()? 'selected':''}>
                                        <c:out value="${patient.toString()}"/>
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="form-group">
                                <label for="visit_time" class="text-info"><fmt:message key="visit_time"/>:</label><br>
                                <input type="datetime-local" name="visit_time" id="visit_time" class="form-control" value="${visit_time}">
                            </div>
                            <div class="form-group">
                                <input type="submit" name="submit" class="btn btn-info btn-md" value="submit">
                                <tf:errortag text="${message}"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>