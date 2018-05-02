<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%@ page import="gcp.jbjak.iotfitness.Profile" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>
<center><h1>IoT Fitness</h1>
<hr>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
        Profile profile = ObjectifyService.ofy().load().type(Profile.class).filter("member_id",user.getUserId()).first().now();
        if (profile == null)
        {
%>
<h2>
<p>Welcome! Please tell us a bit more about yourself...
</h3>
<h4>
<form action="/createProfile" method="post">
	<div>First Name: <input type="text" name="fname" required/></div>
	<div>Last Name: <input type="text" name="lname" required/></div>
	<div>Year of Birth: <input type="text" name="birth_yr" required/></div>
	<div>Height (ft,in): <input type="text" name="height" required/></div>
	<div>Weight (lbs): <input type="text" name="weight" required/></div>
    <div><input type="submit" value="Create a Profile" required/></div>
</form>
</h4>
<%-- // Profile
    String fname = req.getParameter("fname");
    String lname = req.getParameter("lname");
    String birth_yr = req.getParameter("birth_yr");
    String height = req.getParameter("height");
    String weight = req.getParameter("weight");
--%>

<%
        } else {
%>

<h2>
<br>Thank you for coming back <%=profile.first_name%>!
</h2>
<h3>
Let's get your activities updated...
</h3>
<form action="/logDayForm.jsp" method="get">
    <div><input type="submit" value="Log Single Day's Activity"/></div>
</form>
<form action="/logMultiDayForm.jsp" method="get">
    <div><input type="submit" value="Upload Multiple Days"/></div>
</form>
<%
        }
%>


<hr>
<h2>
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Check-Out</a></p>
</h2>
<hr>
<%
    } else {
%>
<h2>
<p>Please login to get started... 
<hr>
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>"><b>Check-In</b></a>
</p>
</h2>
<%
    }
%>
</center>
</body>
</html>
<%-- //[END all]--%>
