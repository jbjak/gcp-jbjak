package gcp.jbjak.iotfitness.objects;

import java.util.Date;

public class Activity {
	
	  public Long member_id;
	  public String first_name;
	  public String last_name;
	  public String gender;
	  public Integer age;
	  public String height;
	  public Integer weight;
	  public Integer hours_sleep;
	  public Integer calories_consumed;
	  public Integer exercise_calories_burned;
	  public String date;
	  public String activity_type;
	  
  /**
	 * @param member_id
	 * @param first_name
	 * @param last_name
	 * @param gender
	 * @param age
	 * @param height
	 * @param weight
	 * @param hours_sleep
	 * @param calories_consumed
	 * @param exercise_calories_burned
	 * @param date
	 * @param activity_type
	 */
	public Activity(long member_id, String first_name, String last_name, String gender, int age, String height,
			int weight, int hours_sleep, int calories_consumed, int exercise_calories_burned, String date,
			String activity_type) {
		this.member_id = member_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.hours_sleep = hours_sleep;
		this.calories_consumed = calories_consumed;
		this.exercise_calories_burned = exercise_calories_burned;
		this.date = date;
		this.activity_type = activity_type;
	}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "Activity [member_id=" + member_id + ", first_name=" + first_name + ", last_name=" + last_name + ", gender="
			+ gender + ", age=" + age + ", height=" + height + ", weight=" + weight + ", hours_sleep=" + hours_sleep
			+ ", calories_consumed=" + calories_consumed + ", exercise_calories_burned=" + exercise_calories_burned
			+ ", date=" + date + ", activity_type=" + activity_type + "]";
}
}
  
