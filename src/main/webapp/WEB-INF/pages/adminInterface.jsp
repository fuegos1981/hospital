<%@ page import="com.epam.hospital.model.Patient"%>
<%@ page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language ="java"%>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <br>
    <h1>First Page</h1>
    <table id = "AllPatients">
    <tr>
        <th>No.</th>
        <th>Last name</th>
        <th>First name</th>
        <th>Date of birthday</th>
        <th colspan ="2">Operation</th>
    </tr>
     <%
        int count =1;
        for (Patient patient: (List<Patient>)request.getAttribute("patients")){
     %>
      <tr>
          <td><%=count++%></th>
          <td><%=patient.getPerson().getLastName()%></td>
          <td><%=patient.getPerson().getFirstName()%></td>
          <td><%=patient.getPerson().getBirthday()%></td>
          <td><a href ="/read-patient?id=<%=patient.getId()%>">Read</td>
          <td><a href ="/hospital/addDoctorToPatient?id=<%=patient.getId()%>&name=<%=patient.toString()%>">Add doctor</td>

      </tr>
      <%
        }
      %>
      <form action="create-patient" method="get">
            <input type="submit" name="submit" class="btn btn-info btn-md" value="submit">
      </form>
</body>
</html>