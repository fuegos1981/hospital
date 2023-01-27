<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" uri="/WEB-INF/pagination.tld" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<html>
    <head>
        <title>Simple</title>
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

        <form id="base-form" class="form" action="Simple" method="post">
            <c:import url="/WEB-INF/pages/header.jsp" />
            <input type="hidden" name="command" value="simple" />
            <input id = "sub" type="submit" name="submit" class="btn btn-info btn-md" value="sub">
            <div id="base1">
                <h3 class="text-center text-white pt-5"><fmt:message key="hospital"/></h3>
                    <div class="container">
                        <div class="row">
                            <div class="table-responsive col-md-6">
                                <div class="form-group">
                                    <br>
                                    <h3><fmt:message key="Category"/></h3>
                                    <a class="btn btn-info btn-md" href="/hospital/editSimple?command=edit_simple&name=Category"><fmt:message key="add"/></a>
                                </div>
                                <table id = "AllCategories" class="table table-bordered table-hover table-striped">
                                    <tr>
                                        <th><fmt:message key="num"/></th>
                                        <th><fmt:message key="Name"/></th>
                                        <th colspan ="2"><fmt:message key="operation"/></th>
                                    </tr>
                                    <c:forEach var="category" items="${categories}" varStatus="status">
                                        <tr>
                                              <td><c:out value="${status.count+maxCountOnPage*(current_page_category-1)}" /></th>
                                              <td><c:out value="${category.toString()}"/></td>
                                              <td><a href ="/hospital/editSimple?command=edit_simple&name=Category&id=${category.getId()}&simple=${category.toString()}"><fmt:message key="edit"/></td>
                                              <td><a href ="/hospital/editSimple?command=delete_simple&name=Category&id=${category.getId()}"><fmt:message key="delete"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                                <tg:pgn name="category" current_page="${current_page_category}"  count_page = "${count_page_category}"/>
                            </div>
                            <div class="table-responsive  col-md-6">
                               <div class="form-group">
                               <br>
                               <h3><fmt:message key="Diagnosis"/></h3>
                               <a class="btn btn-info btn-md" href="/hospital/editSimple?command=edit_simple&name=Diagnosis"><fmt:message key="add"/></a>
                               </div>
                            <table id = "AllDiagnosis" class="table table-bordered table-hover table-striped">
                                <tr>
                                  <th><fmt:message key="num"/></th>
                                  <th><fmt:message key="Name"/></th>
                                  <th colspan ="2"><fmt:message key="operation"/></th>
                              </tr>
                               <c:forEach var="diagnosis" items="${diagnosises}" varStatus="status">
                                    <tr>
                                        <td><c:out value="${status.count+maxCountOnPage*(current_page_diagnosis-1)}"/></th>
                                        <td><c:out value="${diagnosis.toString()}"/></td>
                                        <td><a href ="/hospital/editSimple?command=edit_simple&name=Diagnosis&id=${diagnosis.getId()}&simple=${diagnosis.toString()}"><fmt:message key="edit"/></td>
                                        <td><a href ="/hospital/editSimple?command=delete_simple&name=Diagnosis&id=${diagnosis.getId()}"><fmt:message key="delete"/></td>
                                    </tr>
                               </c:forEach>
                            </table>
                            <tg:pgn name="diagnosis" current_page="${current_page_diagnosis}"  count_page = "${count_page_diagnosis}"/>
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