<%@ page import="com.epam.hospital.model.Patient"%>
<%@ page import="java.util.List"%>
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
        <div id="base">
            <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
            <div class="container">
                <div id="base-row" class="row justify-content-center align-items-center">
                    <div id="base-column" class="col-md-6">
                        <div id="base-box" class="col-md-12">
                            <form id="base-form" class="form" action="edit-simple" method="post">
                                <h3 class="text-center text-info">Create patient</h3>
                                <div class="form-group">
                                    <%
                                        String name = (String)request.getAttribute("name");
                                    %>
                                    <label for="simple" class="text-info"><%=name%>:</label><br>
                                    <input type="text" name="simple" id="simple" class="form-control">
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