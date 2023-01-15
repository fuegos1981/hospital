<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" uri="/WEB-INF/pagination.tld" %>
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
        <form id="base-form" class="form" action="readPatient?id=${patient_id}&command=patient_info" method="post">
            <c:import url="/WEB-INF/pages/header.jsp" />
            <input type="hidden" name="command" value="patient_info" />
            <input id = "sub" type="submit" name="submit" class="btn btn-info btn-md" value="sub">
            <input type="hidden" name="patient_id" value="${patient_id}" />
            <div id="base1">
                <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
                <div class="container">
                    <div class="row row-patient">
                        <div id ="patient_table_info" class="col-md-12">
                            <table class="table table-bordered table-hover table-striped">
                                <tr>
                                    <th><fmt:message key="Name"/></th>
                                    <th><fmt:message key="Birthday"/></th>
                                    <th><fmt:message key="Email"/></th>
                                    <th colspan ="2"><fmt:message key="operation"/></th>
                                </tr>
                                <tr>
                                    <td><c:out value="${patient.toString()}"/></td>
                                    <td><c:out value="${patient.getBirthday()}"/></td>
                                    <td><c:out value="${patient.getEmail()}"/></td>
                                    <td><a href ="editPatient?id=${patient.getId()}&command=edit_patient&isFirst=true" method="post"/><fmt:message key="edit"/></td>
                                    <td><input type="submit" name="download" class="btn btn-info btn-md" value='<fmt:message key="download_history"/>'/></td>

                                </tr>
                            </table>
                        </div>
                        <div class="table-responsive col-md-6">
                            <div class="form-group">
                                <br>
                                <a class="btn btn-info btn-md" href ="/hospital/addSchedule?patient_id=${patient.getId()}&command=add_schedule&is_patient=true&from=patient_info"><fmt:message key="add_visit"/></a>
                            </div>
                            <table id = "AllSchedule" class="table table-bordered table-hover table-striped">
                                <tr>
                                    <th><fmt:message key="num"/></th>
                                    <th><fmt:message key="Name"/></th>
                                    <th><fmt:message key="visit"/></th>
                                    <th colspan ="2"><fmt:message key="operation"/></th>
                                </tr>
                                <c:forEach var="schedule" items="${schedules}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count+maxCountOnPage*(current_page_schedule-1)}"/></td>
                                        <td><c:out value="${schedule.getDoctorName()}"/></td>

                                        <td><fmt:formatDate value="${schedule.getDateVisit()}" pattern = "yyyy-MM-dd HH:mm" /></td>
                                        <td><a href ="/hospital/addSchedule?id=${schedule.getId()}&patient_id=${schedule.getPatientId()}&command=add_schedule&isFirst=true&from=patient_info"/><fmt:message key="edit"/></td>
                                        <td><a href ="/hospital/deleteSchedule?id=${schedule.getId()}&patient_id=${schedule.getPatientId()}&command=delete_schedule&from=patient_info"/><fmt:message key="delete"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <tg:pgn name="schedule" current_page="${current_page_schedule}"  count_page = "${count_page_schedule}"/>
                        </div>
                        <div class="table-responsive  col-md-6">
                            <div class="form-group">
                                <br>
                                <a class="btn btn-info btn-md" href="/hospital/editAppointment?command=edit_appointment&patient_id=${patient_id}&isFirst=true&from=patient_info"><fmt:message key="create_appointment"/></a>
                            </div>
                            <table id = "AllAppointments" class="table table-bordered table-hover table-striped">
                                <tr>
                                  <th><fmt:message key="num"/></th>
                                  <th><fmt:message key="date"/></th>
                                  <th><fmt:message key="Doctor"/></th>
                                  <th><fmt:message key="Category"/></th>
                                  <th><fmt:message key="diagnosis"/></th>
                                  <th colspan ="1"><fmt:message key="operation"/></th>
                              </tr>
                               <c:forEach var="appointment" items="${appointments}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count+maxCountOnPage*(current_page_appointment-1)}"/></td>
                                        <td><c:out value="${appointment.getDateCreate()}"/></th>
                                        <td><c:out value="${appointment.getDoctorName()}"/></td>
                                        <td><c:out value="${appointment.getCategoryName()}"/></td>
                                        <td><c:out value="${appointment.getDiagnosisName()}"/></td>
                                        <td><a href ="/hospital/editAppointment?id=${appointment.getId()}&patient_id=${patient.getId()}&command=edit_appointment&isFirst=true&from=patient_info"><fmt:message key="edit"/></td>
                                    </tr>
                               </c:forEach>
                            </table>
                             <tg:pgn name="appointment" current_page="${current_page_appointment}"  count_page = "${count_page_appointment}"/>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
    <script>
        <%@include file="/WEB-INF/main.js"%>
    </script>
</html>