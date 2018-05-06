package gcp.jbjak.iotfitness.objects;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Activity {

	public Long member_id;
	public String first_name;
	public String last_name;
	public String gender;
	public Integer age;
	public Integer height;
	public Integer weight;
	public Integer hours_sleep;
	public Integer calories_consumed;
	public Integer exercise_calories_burned;
	public String date;
	public String activity_type;
	public Integer activity_code;
	public Integer equipment_id;

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
	 * @param activity_code
	 * @param equipment_id
	 */
	public Activity(long member_id, String first_name, String last_name, String gender, int age, String heightStr,
			int weight, int hours_sleep, int calories_consumed, int exercise_calories_burned, String date,
			String activity_type) {
		this.member_id = member_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.age = age;
		
		// Store height as inches
		String[] values = heightStr.split(",");
		int heightFt = Integer.parseInt(values[0]);
		int heightIn = Integer.parseInt(values[1]);
		this.height = heightFt*12 + heightIn;

		this.weight = weight;
		this.hours_sleep = hours_sleep;
		this.calories_consumed = calories_consumed;
		this.exercise_calories_burned = exercise_calories_burned;
		this.date = date;

		this.activity_type = activity_type;
		// Determine activity code based on selected type
		switch (activity_type) {
		case "Walking":
			this.activity_code = 1;
			break;
		case "Running":
			this.activity_code = 2;
			break;
		case "Bicycling":
			this.activity_code = 3;
			break;
		case "Swimming":
			this.activity_code = 4;
			break;
		case "Elliptical":
			this.activity_code = 5;
			break;
		case "Stair_Stepper":
			this.activity_code = 6;
			break;
		case "Stationary_Bike":
			this.activity_code = 7;
			break;
		case "Treadmill":
			this.activity_code = 8;
			break;
		case "Rowing_Machine":
			this.activity_code = 9;
			break;
		default:
			this.activity_code = 0;
		}

		// Generate a random equipment ID in the right range to simulate individual machines of the right type
		if(this.activity_code < 5) {
			this.equipment_id = 0;
		} else {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
			this.equipment_id = this.activity_code*10 + randomNum;
		}
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Activity [member_id=" + member_id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", gender=" + gender + ", age=" + age + ", height=" + height + ", weight=" + weight + ", hours_sleep="
				+ hours_sleep + ", calories_consumed=" + calories_consumed + ", exercise_calories_burned="
				+ exercise_calories_burned + ", date=" + date + ", activity_type=" + activity_type + ", activity_code="
				+ activity_code + ", equipment_id=" + equipment_id + "]";
	}
	
	public String toCSV() {
		return member_id + "," + first_name + "," + last_name
				+ "," + gender + "," + age + "," + height + "," + weight + ","
				+ hours_sleep + "," + calories_consumed + ","
				+ exercise_calories_burned + "," + date + "," + activity_type + ","
				+ activity_code + "," + equipment_id;
	}
}
