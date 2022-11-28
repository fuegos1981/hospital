<%@ page import="com.epam.hospital.model.Doctor"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
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
        <div id="base">
            <h3 class="text-center text-white pt-5">HOSPITAL</h3>
            <div class="container">
                <div id="base-row" class="row justify-content-center align-items-center">
                    <div id="base-column" class="col-md-6">
                        <div id="base-box" class="col-md-12">
                            <form id="base-form" class="form" action="addDoctorToPatient" method="post">
                                <h3 class="text-center text-info">Add doctor</h3>
                                <div class="form-group">
                                    <label for="patient" class="text-info">patient:</label><br>
                                    <%
                                        String name = (String)request.getAttribute("name");
                                    %>
                                    <input type="text" name="patient" id="patient" class="form-control" value="<%=name%>"/>
                                </div>
                                    <select class="form-control" name="doctor_id" id="doctors">
                                          <option>Select doctors...</option>
                                          <%
                                               for (Doctor doctor: (List<Doctor>)request.getAttribute("doctors")){
                                               String s = doctor.toString();
                                          %>
                                                <option value="<%=doctor.getId()%>">
                                                	<%=s%>
                                                </option>
                                          <%
                                              }
                                          %>
                                    </select>
                                <div class="form-group">
                                    <label for="visit_time" class="text-info">Visit time:</label><br>
                                    <input type="date" name="visit_time" id="visit_time" class="form-control">
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