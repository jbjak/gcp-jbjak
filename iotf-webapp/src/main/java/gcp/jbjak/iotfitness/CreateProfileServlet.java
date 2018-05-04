//[START all]
package gcp.jbjak.iotfitness;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

@WebServlet(name = "createProfile", value = "/createProfile")
public class CreateProfileServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Profile profile;

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    
    Date now = new Date();

    String fname = req.getParameter("fname");
    String lname = req.getParameter("lname");
    String birth_yr = req.getParameter("birth_yr");
    String height = req.getParameter("height");
    String weight = req.getParameter("weight");
    String device_type = req.getParameter("device_type");
    
    if (user != null) {
    	profile = ObjectifyService.ofy().load().type(Profile.class).filter("member_id",user.getUserId()).first().now();
    	if (profile == null) {
    		profile = new Profile(user.getUserId(),user.getEmail(), fname, lname, birth_yr, height, weight, device_type);	
    	} else {
    		profile.setFirst_name(fname);
    		profile.setLast_name(lname);
    		profile.setBirth_year(birth_yr);
    		profile.setHeight(height);
    		profile.setWeight(weight);
    		profile.setDevice_type(device_type);
    		profile.setLastUpdateDate(now);
    	}
	    // Use Objectify to save the profile and now() is used to make the call synchronously as we
	    // will immediately get a new page using redirect and we want the data to be present.
	    ObjectifyService.ofy().save().entity(profile).now();
	    System.out.println("### Saved profile to datastore: " + profile.toString()); 
	}

    resp.sendRedirect("/home.jsp");
  }
}
//[END all]
