<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" uri="/WEB-INF/pagination.tld" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<html>
    <head>
        <title>Administrator</title>
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

        <form id="base-form" class="form" action="Admin" method="post">
            <c:import url="/WEB-INF/pages/header.jsp" />
            <input type="hidden" name="command" value="admin" />
            <div id="base1">
                <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
                    <div class="container">
                        <div class="row">
                            <div class="table-responsive col-md-6">
                                <div class="form-group">
                                    <br>
                                    <a class="btn btn-info btn-md" href="/hospital/editPatient?command=edit_patient"><fmt:message key="create_patient"/></a>
                                </div>
                                <div class="form-group">
                                       <input id = "sub" type="submit" name="submit" class="btn btn-info btn-md" value="sort">
                                        <input type="radio" name="sortPatient" onClick="clickSort()"  value="NAME_ASC" ${requestScope['sortPatient'] == 'NAME_ASC'? 'checked':''}/><fmt:message key="name"/><span class="dirrect"> 🠗</span>
                                        <input type="radio" name="sortPatient" onClick="clickSort()" value="NAME_DESC" ${requestScope['sortPatient'] == 'NAME_DESC'? 'checked':''}/><fmt:message key="name"/><span class="dirrect"> 🠕</span>
                                        <input type="radio" name="sortPatient" onClick="clickSort()" value="BIRTHDAY_ASC" ${requestScope['sortPatient'] == 'BIRTHDAY_ASC'? 'checked':''}/><fmt:message key="birthday"/><span class="dirrect"> 🠗</span>
                                        <input type="radio" name="sortPatient" onClick="clickSort()"  value="BIRTHDAY_DESC" ${requestScope['sortPatient'] == 'BIRTHDAY_DESC'? 'checked':''}/><fmt:message key="birthday"/><span class="dirrect"> 🠕</span>
                                </div>
                                <table id = "AllPatients" class="table table-bordered table-hover table-striped">
                                    <tr>
                                        <th><fmt:message key="num"/></th>
                                        <th><fmt:message key="Name"/></th>
                                        <th><fmt:message key="Birthday"/></th>
                                        <th colspan ="2"><fmt:message key="operation"/></th>
                                    </tr>
                                    <c:forEach var="patient" items="${patients}" varStatus="status">
                                        <tr>
                                              <td><c:out value="${status.count+maxCountOnPage*(current_page_patient-1)}" /></th>
                                              <td><c:out value="${patient.toString()}"/></td>
                                              <td><c:out value="${patient.getBirthday()}"/></td>
                                              <td><a href ="/hospital/readPatient?id=${patient.getId()}&command=patient_info"><fmt:message key="read"/></td>
                                              <td><a href ="/hospital/addSchedule?patient_id=${patient.getId()}&command=add_schedule&is_patient=true" method="post"><fmt:message key="visit"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <tg:pgn name="patient" current_page="${current_page_patient}"  count_page = "${count_page_patient}"/>
                            </div>
                            <div class="table-responsive  col-md-6">
                               <div class="form-group">
                               <br>
                               <a class="btn btn-info btn-md" href="/hospital/editDoctor?command=edit_doctor"><fmt:message key="create_doctor"/></a>
                               <a class="btn btn-info btn-md" href="/hospital/editSimple?command=edit_simple&name=Category"><fmt:message key="add_new_category"/></a>
                               </div>
                            <div class="form-group">
                                <input type="radio" name="sortDoctor" onClick="clickSort()"  value="NAME_ASC" ${requestScope['sortDoctor'] == 'NAME_ASC'? 'checked':''}/><fmt:message key="name"/><span class="dirrect"> 🠗</span>
                                <input type="radio" name="sortDoctor" onClick="clickSort()"  value="NAME_DESC" ${requestScope['sortDoctor'] == 'NAME_DESC'? 'checked':''}/><fmt:message key="name"/><span class="dirrect"> 🠕</span>
                                <input type="radio" name="sortDoctor" onClick="clickSort()"  value="CATEGORY_ASC" ${requestScope['sortDoctor'] == 'CATEGORY_ASC'? 'checked':''}/><fmt:message key="category"/><span class="dirrect"> 🠗</span>
                                <input type="radio" name="sortDoctor" onClick="clickSort()"  value="CATEGORY_DESC" ${requestScope['sortDoctor'] == 'CATEGORY_DESC'? 'checked':''}/><fmt:message key="category"/><span class="dirrect"> 🠕</span>
                                <input type="radio" name="sortDoctor" onClick="clickSort()"  value="COUNT_PATIENT_ASC" ${requestScope['sortDoctor'] == 'COUNT_PATIENT_ASC'? 'checked':''}/><fmt:message key="patients"/><span class="dirrect"> 🠗</span>
                                <input type="radio" name="sortDoctor" onClick="clickSort()"  value="COUNT_PATIENT_DESC" ${requestScope['sortDoctor'] == 'COUNT_PATIENT_DESC'? 'checked':''}/><fmt:message key="patients"/><span class="dirrect"> 🠕</span>
                            </div>
                            <table id = "AllDoctors" class="table table-bordered table-hover table-striped">
                                <tr>
                                  <th><fmt:message key="num"/></th>
                                  <th><fmt:message key="Name"/></th>
                                  <th><fmt:message key="category"/></th>
                                  <th colspan ="3"><fmt:message key="operation"/></th>
                              </tr>
                               <c:forEach var="doctor" items="${doctors}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count+maxCountOnPage*(current_page_doctor-1)}"/></th>
                                        <td><c:out value="${doctor.getLastName()} ${doctor.getFirstName()}"/></td>
                                        <td><c:out value="${doctor.getCategory()}"/></td>
                                        <td><a href ="/hospital/medic?command=medic&doctor_id=${doctor.getId()}"><fmt:message key="read"/></td>
                                        <td><a href ="/hospital/editDoctor?command=edit_doctor&id=${doctor.getId()}&isFirst=true"><fmt:message key="edit"/></td>
                                        <td><a href ="/hospital/addSchedule?doctor_id=${doctor.getId()}&name=${doctor.toString()}&command=add_schedule&is_patient=false"><fmt:message key="visit"/></td>
                                    </tr>
                               </c:forEach>
                            </table>
                            <tg:pgn name="doctor" current_page="${current_page_doctor}"  count_page = "${count_page_doctor}"/>
                        </div>
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