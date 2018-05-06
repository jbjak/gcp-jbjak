//[START all]
package gcp.jbjak.iotfitness.actions;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

import gcp.jbjak.iotfitness.objects.Activity;
import gcp.jbjak.iotfitness.objects.Profile;
import gcp.jbjak.iotfitness.util.PubSubHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@WebServlet(name = "logDay", value = "/logDay")
public class LogDayServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Profile profile;
		Activity activity;

		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();

			ObjectMapper mapper = new ObjectMapper();

			if (user != null) {
				profile = ObjectifyService.ofy().load().type(Profile.class).filter("member_id", user.getUserId())
						.first().now();
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

					activity = new Activity(member_id, first_name, last_name, gender, age, height, weight, hours_sleep,
							calories_consumed, exercise_calories_burned, activity_date, activity_type);

					// Send as JSON
					//mapper.enable(SerializationFeature.INDENT_OUTPUT);
					mapper.writeValue(System.out, activity);
					String msgToPublish = mapper.writeValueAsString(activity);
					
					// Send as CSV line
					// String msgToPublish = activity.toCSV();

					// SEND TO PUB/SUB
					String topic_id = getServletContext().getInitParameter("pubsub.topic");
					PubSubHelper psHelper = new PubSubHelper();
					psHelper.publish(topic_id, msgToPublish);

					System.out.println("### Sending activity log entry to the queue: " + msgToPublish);
					System.out.flush();
				}
			}

			req.getRequestDispatcher("/home.jsp").forward(req, resp);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
// [END all]
