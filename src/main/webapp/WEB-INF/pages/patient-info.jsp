<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<html>
    <head>
        <title>Patient info</title>
        <style>
            <%@include file="/WEB-INF/styles/main.css"%>
        </style>
    </head>
    <body>
        <fmt:requestEncoding value="UTF-8" />
        <fmt:setLocale value="${locale}" scope="session" />
        <fmt:setBundle basename="pagecontent"/>
        <form id="base-form" class="form" action="readPatient" method="post">
            <c:import url="/WEB-INF/pages/header.jsp" />
            <input type="hidden" name="command" value="patient-info" />
            <input type="hidden" name="patient_id" value="${patient_id}" />
            <div id="base1">
                <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
                <div class="container">
                    <div class="row">
                        <div id ="patient_table_info" class="col-md-12">
                            <table class="table table-bordered table-hover table-striped">
                                <tr>
                                    <th><fmt:message key="name"/></th>
                                    <th><fmt:message key="date_of_birthday"/></th>
                                    <th><fmt:message key="email"/></th>
                                    <th colspan ="1"><fmt:message key="operation"/></th>
                                </tr>
                                <tr>
                                    <td><c:out value="${patient.getPerson().toString()}"/></td>
                                    <td><c:out value="${patient.getPerson().getBirthday()}"/></td>
                                    <td><c:out value="${patient.getPerson().getEmail()}"/></td>
                                    <td><a href ="/edit-patient?id=${patient.getId()}&command=edit_patient"/><fmt:message key="edit"/></td>
                                </tr>
                            </table>
                        </div>
                        <div class="table-responsive col-md-6">
                            <div class="form-group">
                                <br>
                                <a class="btn btn-info btn-md" href ="/hospital/addSchedule?id=${patient.getId()}&name=${patient.toString()}&command=add_schedule&is_patient=true" method="post"><fmt:message key="add_visit"/></a>
                            </div>
                            <table id = "AllSchedule" class="table table-bordered table-hover table-striped">
                                <tr>
                                    <th><fmt:message key="num"/></th>
                                    <th><fmt:message key="name"/></th>
                                    <th><fmt:message key="visit"/></th>
                                    <th colspan ="1">Operation</th>
                                </tr>
                                <c:forEach var="schedule" items="${schedules}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}"/></td>
                                        <td><c:out value="${schedule.getDoctor().toString()}"/></td>
                                        <td><c:out value="${schedule.getDateVisit()}"/></td>
                                        <td><td><a href ="/hospital/addSchedule?id=${patient.getId()}&name=${patient.toString()}&command=add_schedule&is_patient=true" method="post"><fmt:message key="add_visit"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div class="table-responsive  col-md-6">
                            <div class="form-group">
                                <br>
                                <a class="btn btn-info btn-md" href="/hospital/editAppointment?command=edit_appointment&name=${patient.toString()}&patient_id=${patient_id}"><fmt:message key="create_appointment"/></a>
                            </div>
                            <table id = "AllAppointments" class="table table-bordered table-hover table-striped">
                                <tr>
                                  <th><fmt:message key="num"/></th>
                                  <th><fmt:message key="date"/></th>
                                  <th><fmt:message key="doctor"/></th>
                                  <th><fmt:message key="category"/></th>
                                  <th><fmt:message key="diagnosis"/></th>
                                  <th colspan ="1"><fmt:message key="operation"/></th>
                              </tr>
                               <c:forEach var="appointment" items="${appointments}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}"/></td>
                                        <td><c:out value="${appointment.getDateCreate()}"/></th>
                                        <td><c:out value="${appointment.getDoctor().getPerson().toString()}"/></td>
                                        <td><c:out value="${appointment.doctor.getCategory()}"/></td>
                                        <td><c:out value="${appointment.getDiagnosis()}"/></td>
                                        <td><a href ="/read-appointment?id=${appointment.getId()}">Read</td>
                                    </tr>
                               </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>