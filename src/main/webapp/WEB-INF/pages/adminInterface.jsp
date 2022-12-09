<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <form id="base-form" class="form" action="main" method="post">
            <input type="hidden" name="command" value="admin" />
            <div id="base1">
                <h3 class="text-center text-white pt-5">HOSPITAL</h3>
                    <div class="container">
                        <div class="row">
                            <div class="table-responsive col-md-6">
                                <div class="form-group">
                                    <br>
                                    <input type="submit" name="submit" class="btn btn-info btn-md" value="createPatient">
                                </div>
                                <div class="form-group">
                                       <input type="submit" name="submit" class="btn btn-info btn-md" value="sort">
                                        <input type="radio" name="sortPatient"  value="name asc" ${requestScope['sortPatient'] == 'name asc'? 'checked':''}/> name asc
                                        <input type="radio" name="sortPatient"  value="name desc" ${requestScope['sortPatient'] == 'name desc'? 'checked':''}/>name desc
                                        <input type="radio" name="sortPatient"  value="birthday asc" ${requestScope['sortPatient'] == 'birthday asc'? 'checked':''}/>birthday asc
                                        <input type="radio" name="sortPatient"  value="birthday desc" ${requestScope['sortPatient'] == 'birthday desc'? 'checked':''}/>birthday desc
                                </div>
                                <table id = "AllPatients" class="table table-bordered table-hover table-striped">
                                    <tr>
                                        <th>No.</th>
                                        <th>Name</th>
                                        <th>Date of birthday</th>
                                        <th colspan ="2">Operation</th>
                                    </tr>
                                    <c:forEach var="patient" items="${patients}" varStatus="status">
                                        <tr>

                                              <td><c:out value="${status.count}" /></th>
                                              <td><c:out value="${patient.getPerson().toString()}"/></td>
                                              <td><c:out value="${patient.getPerson().getBirthday()}"/></td>
                                              <td><a href ="/hospital/readPatient?id=${patient.getId()}&command=patient_info">Read</td>
                                              <td><a href ="/hospital/addSchedule?id=${patient.getId()}&name=${patient.toString()}&command=add_schedule&is_patient=true" method="post">Add doctor</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <div class btn-group btn group-xs>
                                    <ul class = "pagination pagination-sm">
                                        <li class="disabled"><a class="btn btn-info" href = "#">begin</a></li>
                                        <li class="active"><a class="btn  btn-info" href = "#">1</a></li>
                                        <li><a class="btn  btn-info" href = "#">2</a></li>
                                        <li><a class="btn  btn-info" href = "#">end</a></li>
                                    </ul>
                                </div>
                            </div>

                            <div class="table-responsive  col-md-6">
                                <div class="form-group">
                                <br>
                               <input type="submit" name="submit" class="btn btn-info btn-md" value="createPatient">
                            </div>
                            <div class="form-group">
                              <input type="submit" name="submit" class="btn btn-info btn-md" value="sort">
                              <input type="radio" name="sortDoctor"  value="name asc" ${requestScope['sortDoctor'] == 'name asc'? 'checked':''}/>name asc
                              <input type="radio" name="sortDoctor"  value="name desc" ${requestScope['sortDoctor'] == 'name desc'? 'checked':''}/>name desc
                              <input type="radio" name="sortDoctor"  value="category asc" ${requestScope['sortDoctor'] == 'category asc'? 'checked':''}/>category asc
                              <input type="radio" name="sortDoctor"  value="category desc" ${requestScope['sortDoctor'] == 'category desc'? 'checked':''}/>category desc
                            </div>
                            <table id = "AllDoctors" class="table table-bordered table-hover table-striped">
                                <tr>
                                  <th>No.</th>
                                  <th>Name</th>
                                  <th>Category</th>
                                  <th colspan ="2">Operation</th>
                              </tr>
                               <c:forEach var="doctor" items="${doctors}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count}"/></th>
                                        <td><c:out value="${doctor.getPerson().getLastName()} ${doctor.getPerson().getFirstName()}"/></td>
                                        <td><c:out value="${doctor.getCategory()}"/></td>
                                        <td><a href ="/read-doctor?id=${doctor.getId()}">Read</td>
                                        <td><a href ="/hospital/addSchedule?id=${doctor.getId()}&name=${doctor.toString()}&command=add_schedule&is_patient=false">Add patient</td>
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