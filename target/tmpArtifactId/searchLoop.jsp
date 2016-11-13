<%--
  Created by IntelliJ IDEA.
  User: Tim
  Date: 11/12/2016
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Loop</title>
</head>
<body>
<form action="service/loops/getWithID" method="post">
    <!-- adding search for old loops once database functionality is added-->
    <h2>Find Loop By Id(in progress)</h2>
    ID: <input type="number" name="Id" />
    <br/>
    <button type="submit" value="Submit Form">Submit</button>
</form>
</body>
</html>
