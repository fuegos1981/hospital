<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <form id="base-form" class="form" action="main" method="post">
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
                                        <input type="radio" name="sortPatient" onClick="clickSort()"  value="name asc" ${requestScope['sortPatient'] == 'name asc'? 'checked':''}/><fmt:message key="name_asc"/>
                                        <input type="radio" name="sortPatient" onClick="clickSort()" value="name desc" ${requestScope['sortPatient'] == 'name desc'? 'checked':''}/><fmt:message key="name_desc"/>
                                        <input type="radio" name="sortPatient" onClick="clickSort()" value="birthday asc" ${requestScope['sortPatient'] == 'birthday asc'? 'checked':''}/><fmt:message key="birthday_asc"/>
                                        <input type="radio" name="sortPatient" onClick="clickSort()"  value="birthday desc" ${requestScope['sortPatient'] == 'birthday desc'? 'checked':''}/><fmt:message key="birthday_desc"/>
                                </div>
                                <table id = "AllPatients" class="table table-bordered table-hover table-striped">
                                    <tr>
                                        <th><fmt:message key="num"/></th>
                                        <th><fmt:message key="name"/></th>
                                        <th><fmt:message key="date_of_birthday"/></th>
                                        <th colspan ="2"><fmt:message key="operation"/></th>
                                    </tr>
                                    <c:forEach var="patient" items="${patients}" varStatus="status">
                                        <tr>
                                              <td><c:out value="${status.count+10*(current_page_patients-1)}" /></th>
                                              <td><c:out value="${patient.getPerson().toString()}"/></td>
                                              <td><c:out value="${patient.getPerson().getBirthday()}"/></td>
                                              <td><a href ="/hospital/readPatient?id=${patient.getId()}&command=patient_info"><fmt:message key="read"/></td>
                                              <td><a href ="/hospital/addSchedule?id=${patient.getId()}&name=${patient.toString()}&command=add_schedule&is_patient=true" method="post"><fmt:message key="add_visit"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <div class btn-group btn group-xs>
                                    <ul id="pagination_patient" class = "pagination pagination-sm">
                                        <li class="disabled"><input type="button" onClick="clickPagePatient(1)" name="pat" class="btn btn-info btn-md" value='<fmt:message key="begin"/>'/></li>
                                       <c:forEach varStatus="status" begin="1" end ="${countPagePatient}">
                                            <li class="active"><input type="button" onClick="clickPagePatient(${status.count})" name="pat" class="btn btn-info btn-md" value='${status.count}'/></li>
                                       </c:forEach>
                                        <li><input type="button" onClick="clickPagePatient(${requestScope['countPagePatient']})" name="pat" class="btn btn-info btn-md" value='<fmt:message key="end"/>'/></li>
                                    </ul>
                                    <input type="hidden" id ="pat_comment" name="current_page_patients" value="${current_page_patients}" />
                                </div>
                            </div>
                            <div class="table-responsive  col-md-6">
                               <div class="form-group">
                               <br>
                               <a class="btn btn-info btn-md" href="/hospital/editPatient?command=edit_doctor"><fmt:message key="create_doctor"/></a>
                               </div>
                            <div class="form-group">
                              <input type="radio" name="sortDoctor" onClick="clickSort()"  value="name asc" ${requestScope['sortDoctor'] == 'name asc'? 'checked':''}/><fmt:message key="name_asc"/>
                              <input type="radio" name="sortDoctor" onClick="clickSort()"  value="name desc" ${requestScope['sortDoctor'] == 'name desc'? 'checked':''}/><fmt:message key="name_desc"/>
                              <input type="radio" name="sortDoctor" onClick="clickSort()"  value="category asc" ${requestScope['sortDoctor'] == 'category asc'? 'checked':''}/><fmt:message key="category_asc"/>
                              <input type="radio" name="sortDoctor" onClick="clickSort()"  value="category desc" ${requestScope['sortDoctor'] == 'category desc'? 'checked':''}/><fmt:message key="category_desc"/>
                            </div>
                            <table id = "AllDoctors" class="table table-bordered table-hover table-striped">
                                <tr>
                                  <th><fmt:message key="num"/></th>
                                  <th><fmt:message key="name"/></th>
                                  <th><fmt:message key="category"/></th>
                                  <th colspan ="2"><fmt:message key="operation"/></th>
                              </tr>
                               <c:forEach var="doctor" items="${doctors}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}"/></th>
                                        <td><c:out value="${doctor.getPerson().getLastName()} ${doctor.getPerson().getFirstName()}"/></td>
                                        <td><c:out value="${doctor.getCategory()}"/></td>
                                        <td><a href ="/read-doctor?id=${doctor.getId()}"><fmt:message key="read"/></td>
                                        <td><a href ="/hospital/addSchedule?id=${doctor.getId()}&name=${doctor.toString()}&command=add_schedule&is_patient=false"><fmt:message key="add_visit"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div class btn-group btn group-xs>
                                <ul id="pagination_doctor" class = "pagination pagination-sm">
                                    <li class="disabled"><input type="button" onClick="clickPageDoctor(1)" name="doc" class="btn btn-info btn-md" value='<fmt:message key="begin"/>'/></li>
                                    <c:forEach varStatus="status" begin="1" end ="${countPageDoctor}">
                                        <li class="active"><input type="button" onClick="clickPageDoctor(${status.count})" name="doc" class="btn btn-info btn-md" value='${status.count}'/></li>
                                    </c:forEach>
                                    <li><input type="button" onClick="clickPageDoctor(${requestScope['countPageDoctor']})" name="doc" class="btn btn-info btn-md" value='<fmt:message key="end"/>'/></li>
                                </ul>
                                <input type="hidden" id ="pat_comment_doctor" name="current_page_doctors" value="${current_page_doctors}" />
                            </div>
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