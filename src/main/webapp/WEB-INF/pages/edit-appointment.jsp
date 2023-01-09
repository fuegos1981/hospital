<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <style>
        <%@include file="/WEB-INF/styles/main.css"%>
    </style>
    <script>
        <%@include file="/WEB-INF/main.js"%>
    </script>
    <title>Appointment</title>
</head>
<html>
    <body>
        <fmt:requestEncoding value="UTF-8" />
        <c:if test="${not empty locale }">
            <fmt:setLocale value="${locale}" scope="session" />
        </c:if>
        <fmt:setBundle basename="pagecontent"/>
        <form id="base-form" class="form" action="editAppointment" method="post">
            <c:import url="/WEB-INF/pages/header.jsp" />
            <input type="hidden" name="command" value="edit_appointment" />
            <input type="hidden" name="id" value="${id}" />
            <div id="base3">
                <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
                <div class="container">
                    <div class="row row-patient">
                        <div id ="patient_table_info" class="col-md-12">
                            <br/>
                            <h3 class="text-center text-info"><fmt:message key="appointment"/></h3>
                            <br/>
                            <table class="table table-bordered table-hover table-striped">
                                <tr>
                                    <th><fmt:message key="num"/></th>
                                    <th><fmt:message key="date"/></th>
                                    <th><fmt:message key="patient"/></th>
                                    <th><fmt:message key="Doctor"/></th>
                                    <th><fmt:message key="diagnosis"/></th>
                                </tr>
                                <tr>
                                    <td><input type="label" name="id" id="id" class="form-control"value="${id}"></td>
                                    <td><input type="date" name="date_create" id="date" class="form-control" value="${date_create}"></td>
                                    <td>
                                        <select class="form-control" name="patient_id" id="patients" onchange="myReadPatientInfo(${patient.getId()})">
                                            <option value=""><fmt:message key="select_patient"/>...</option>
                                            <c:forEach var="patient" items="${patients}" varStatus="status">
                                                <option value="${patient.getId()}" ${appointment.getPatientId() == patient.getId()? 'selected':''}>
                                                    <c:out value="${patient.toString()}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-control" name="doctor_id" id="doctors">
                                            <option value=""><fmt:message key="select_doctor"/>...</option>
                                            <c:forEach var="doctor" items="${doctors}" varStatus="status">
                                                <option value="${doctor.getId()}" ${doctor.getId() == appointment.getDoctorId()? 'selected':''}>
                                                    <c:out value="${doctor.toString()}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-control" name="diagnosis_id" id="diagnosises">
                                            <option value=""><fmt:message key="select_diagnosis"/>...</option>
                                            <c:forEach var="diagnosis" items="${diagnosises}" varStatus="status">
                                                <option value="${diagnosis.getId()}" ${diagnosis.getId() == appointment.getDiagnosisId()? 'selected':''}>
                                                    <c:out value="${diagnosis.toString()}" />
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="base-column" class="col-md-12">
                            <div id="base-box" class="col-md-12">
                                <div class="form-group">
                                    <div class="form-group">
                                        <label for="medication" class="text-info"><fmt:message key="Medication"/>:</label><br>
                                        <input type="text" name="medication" id="medication" class="form-control" value="${appointment.getMedication()}"/>
                                        <label for="procedures" class="text-info"><fmt:message key="Procedure"/>:</label><br>
                                        <input type="text" name="procedures" id="procedures" class="form-control" value="${appointment.getProcedure()}"/>
                                        <label for="operation" class="text-info"><fmt:message key="Operation"/>:</label><br>
                                        <input type="text" name="operation" id="operation" class="form-control" value="${appointment.getOperation()}"/>
                                    </div>
                                    <div class="form-group">
                                        <br>
                                        <input type="submit" name="submit" class="btn btn-info btn-md" value="<fmt:message key='save'/>">
                                        <tf:errortag text="${message}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>