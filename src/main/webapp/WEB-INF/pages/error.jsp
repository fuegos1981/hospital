<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <style>
        <%@include file="/WEB-INF/styles/main.css"%>
    </style>
    <title>Error Page</title>
</head>
<html>
    <body>
        <div id="base">
            <%
                String name = (String)request.getAttribute("message");
            %>
            <h3 class="text-center text-white pt-5">HOSPITAL</h3>
            <div class="container">
                <div id="base-row" class="row justify-content-center align-items-center">
                    <div id="base-column" class="col-md-6">
                        <div id="base-box" class="col-md-12">
                            <div class="form-group">
                                <label class="text-danger"><%=message%>:</label><br>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>