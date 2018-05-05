//[START all]
package gcp.jbjak.iotfitness;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@WebServlet(name = "logDay", value = "/logDay")
public class LogDayServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Profile profile;
    Activity activity;

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    
    ObjectMapper mapper = new ObjectMapper();

    if (user != null) {
    	profile = ObjectifyService.ofy().load().type(Profile.class).filter("member_id",user.getUserId()).first().now();
    	if (profile != null) {

    		long member_id = profile.getId();
    		String first_name = profile.getFirst_name();
    		String last_name = profile.getLast_name();
    		String gender = profile.getGender();
    		// Calculate the member's age at the activity date
    		int this_year = Calendar.getInstance().get(Calendar.YEAR);
    		int age = this_year - Integer.parseInt(profile.getBirth_year());
    		String height = profile.getHeight();
    		int weight = Integer.parseInt(profile.getWeight());
    		int hours_sleep = Integer.parseInt(req.getParameter("sleep_hrs"));
    		int calories_consumed = Integer.parseInt(req.getParameter("cals_consumed"));
    		int exercise_calories_burned = Integer.parseInt(req.getParameter("cals_burned"));
    		String activity_date = req.getParameter("activity_date");
    		String activity_type = req.getParameter("activity_type");
    		
    		activity = new Activity(member_id, first_name, last_name, gender, age, height, weight, hours_sleep, calories_consumed, exercise_calories_burned, activity_date, activity_type);
    		
    		mapper.enable(SerializationFeature.INDENT_OUTPUT); 
    		mapper.writeValue(System.out, activity);
    		
    		System.out.println("### Sending activity log entry to the queue: " + activity.toString());
    	} 
	}

    resp.sendRedirect("/home.jsp");
  }
}
//[END all]
