<%-- //[START imports]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="gcp.jbjak.iotfitness.Profile" %>
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

<body>
<h1>
<img src="gym_logo.jpg" style="vertical-align:bottom" style="float:left" width="99" height="99"> IoT Fitness<br>
</h1>
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
<div>
<h4>
<form action="/createProfile" method="post">
	First Name: <input type="text" name="fname" required/>
	Last Name: <input type="text" name="lname" required/>
	Year of Birth (MM/DD/YY): <input type="text" name="birth_yr" required/>
	Height (ft,in): <input type="text" name="height" required/>
	Weight (lbs): <input type="text" name="weight" required/>
	IoT Device Type: <select id=device_type name="device_type" required>
      <option value="google">Google Fit</option>
      <option value="apple">Apple Health</option>
      <option value="fitbit">Fitbit</option>
      <option value="garmin">Garmin</option>
      <option value="other" selected="selected">Other</option>
    </select>
    <div><input type="submit" value="Create a Profile"/>
</form>
</h4>
</div>

<%
        } else {
%>

<h2>
Thank you for coming back <%=profile.first_name%>!
</h2>
<div>
<h3>
What would you like to do today?
</h3>
<form action="/logDayForm.jsp" method="get">
    <input type="submit" value="Log Single Day's Activity"/>
</form>
<form action="/uploadMultiDayForm.jsp" method="get">
    <input type="submit" value="Upload Multiple Days"/>
</form>
<form action="https://datastudio.google.com/open/11UMD7VWP-IhbxkITlatH4y4tWww3Rprs" target="_blank" method="get">
    <input type="submit" value="Analyze Your Progress"/>
</form>
<h3>Or you can update your profile details...</h3>
<%=profile.toString()%>
<form action="/createProfile" method="post">
	First Name: <input type="text" name="fname" value=<%=profile.first_name%> required/>
	Last Name: <input type="text" name="lname" value=<%=profile.last_name%> required/>
	Year of Birth (MM/DD/YY): <input type="text" name="birth_yr" value=<%=profile.birth_year%> required/>
	Height (ft,in): <input type="text" name="height" value=<%=profile.height%> required/>
	Weight (lbs): <input type="text" name="weight" value=<%=profile.weight%> required/>
	
	<script>document.getElementById("device_type").value = "<%=profile.device_type%>"</script>
	
	IoT Device Type: <select id=device_type name="device_type" required>
      <option value="google">Google Fit</option>
      <option value="apple">Apple Health</option>
      <option value="fitbit">Fitbit</option>
      <option value="garmin">Garmin Connect</option>
      <option value="other">Other</option>
    </select>
    <input type="submit" value="Update Your Profile"/>
</form>
</h3>
</div>
<%
        }
%>
<h2>
<p>Don't forget to <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>"><b>Check-Out</b></a> when you are done!</p>
</h2>
<%
    } else {
%>
<h2>
<p>Please <a href="<%= userService.createLoginURL(request.getRequestURI()) %>"><b>Check-In</b></a> to get started...</p>
</h2>
<%
    }
%>
</body>
</html>
<%-- //[END all]--%>
