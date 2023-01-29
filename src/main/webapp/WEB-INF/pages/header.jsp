<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <fmt:requestEncoding value="UTF-8" />
        <c:if test="${not empty locale }">
            <fmt:setLocale value="${locale}" scope="session" />
        </c:if>
        <fmt:setBundle basename="pagecontent"/>
    <div class="form-group">
        <input type="submit" name="submit_us" class="btn btn-info btn-md" value='<fmt:message key="en"/>'/>
        <input type="submit" name="submit_ua" class="btn btn-info btn-md" value='<fmt:message key="ua"/>'/>

        <c:if test="${ not empty role}">
            <c:if test="${role.toString() eq 'ADMIN'}">
                <a class="btn btn-info btn-md" href="/hospital/admin?command=admin"><fmt:message key="main"/></a>
            </c:if>
            <c:if test="${role.toString() eq 'DOCTOR'||role.toString() eq 'NURSE'}">
                <a class="btn btn-info btn-md" href="/hospital/medic?command=medic&doctor_id=${user_id}"><fmt:message key="main"/></a>
            </c:if>
            <a class="btn btn-info btn-md" href="/hospital/login?command=logout"><fmt:message key="logout"/> ${user_name}</a>
        </c:if>
    </div>
