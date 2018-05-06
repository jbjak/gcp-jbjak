<%-- //[START imports]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="gcp.jbjak.iotfitness.objects.Profile" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.util.List" %>
<%-- //[END imports]--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
        Profile profile = ObjectifyService.ofy().load().type(Profile.class).filter("member_id",user.getUserId()).first().now();
        if (profile == null)
        {
%>
<%-- No profile created yet, send them home --%>
<body>
    <%
        String redirectURL = "/home.jsp";
        response.sendRedirect(redirectURL);
    %>
</body>
</html>

<%
        } else {
%>
<body>
<h1>
<img src="gym_logo.jpg" style="vertical-align:bottom" style="float:left" width="99" height="99"> IoT Fitness<br>
</h1>
<h2>
Please select the file to upload your activities...
</h2>
<div>
<form action="/uploadMultiDay" method="post" enctype="multipart/form-data">
    File: <input type="file" name="file" id="file" required/>	
    <input type="hidden" name="message" value="Your activity upload has been submitted."/>	
    <input type="submit" value="Upload Your Activities"/>
</form>
<form action="/home.jsp" method="post">
    <input type="hidden" name="message" value="Your activity upload was cancelled."/>	
    <input type="submit" value="Cancel"/>
</form>
</div>
<%
        }
%>
<h2 align="center">
Don't forget to <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>"><b>Check-Out</b></a> when you are done!
</h2>
<%
} else {
%>
<h2>
Please <a href="<%= userService.createLoginURL(request.getRequestURI()) %>"><b>Check-In</b></a> to get started...
</h2>
<%
}
%>
</body>
</html>
<%-- //[END all]--%>
