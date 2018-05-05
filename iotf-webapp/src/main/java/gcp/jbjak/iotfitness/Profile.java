package gcp.jbjak.iotfitness;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.lang.String;
import java.util.Date;

/**
 * The @Entity tells Objectify about our entity.  We also register it in OfyHelper.java -- very
 * important. Our primary key @Id is set automatically by the Google Datastore for us.
 *
 * Objectify, unlike the AppEngine library requires that you specify the fields you
 * want to index using @Index.  This is often a huge win in performance -- though if you don't Index
 * your data from the start, you'll have to go back and index it later.
 **/
@Entity
public class Profile {
  @Id public Long id;
  @Index public String member_id;
  public String email_address;
  public String first_name;
  public String last_name;
  public String birth_year;
  public String height;
  public String weight;
  public String gender;
  public String device_type;
  public Date lastUpdateDate;

  /**
   * no arg constructor
   **/
  public Profile() {}
  
  /**
   * Takes all important fields
   **/
  public Profile(String id, String email, String fname, String lname, String g, String year, String h, String w, String d) {
	  this.member_id = id;
	  this.email_address = email;
	  this.first_name = fname;
	  this.last_name = lname;
	  this.gender = g;
	  this.birth_year = year;
	  this.height = h;
	  this.weight = w;
	  this.device_type = d;
	  this.lastUpdateDate = new Date();
  }
  
  public String toString() 
  {
	  String profileString = 
			  "ID: " + this.id + "\n" +
			  "Member ID:" + this.member_id + "\n" +
			  "Email Address: " + this.email_address + "\n" +
			  "First Name: " + this.first_name + "\n" +
			  "Last Name: " + this.last_name + "\n" +
			  "Gender: " + this.gender + "\n" +
			  "Birth Year: " + this.birth_year + "\n" +
			  "Height: " + this.height + "\n" +
			  "Weight: " + this.weight + "\n" +
			  "IoT Device Type: " + this.device_type + "\n" +
			  "Last Update: " + this.lastUpdateDate.toString();
	  return profileString;
  }

/**
 * @return the id
 */
public Long getId() {
	return id;
}

/**
 * @param id the id to set
 */
public void setId(Long id) {
	this.id = id;
}

/**
 * @return the member_id
 */
public String getMember_id() {
	return member_id;
}

/**
 * @param member_id the member_id to set
 */
public void setMember_id(String member_id) {
	this.member_id = member_id;
}

/**
 * @return the email_address
 */
public String getEmail_address() {
	return email_address;
}

/**
 * @param email_address the email_address to set
 */
public void setEmail_address(String email_address) {
	this.email_address = email_address;
}

/**
 * @return the first_name
 */
public String getFirst_name() {
	return first_name;
}

/**
 * @param first_name the first_name to set
 */
public void setFirst_name(String first_name) {
	this.first_name = first_name;
}

/**
 * @return the last_name
 */
public String getLast_name() {
	return last_name;
}

/**
 * @param last_name the last_name to set
 */
public void setLast_name(String last_name) {
	this.last_name = last_name;
}

/**
 * @return the birth_year
 */
public String getBirth_year() {
	return birth_year;
}

/**
 * @param birth_year the birth_year to set
 */
public void setBirth_year(String birth_year) {
	this.birth_year = birth_year;
}

/**
 * @return the height
 */
public String getHeight() {
	return height;
}

/**
 * @param height the height to set
 */
public void setHeight(String height) {
	this.height = height;
}

/**
 * @return the weight
 */
public String getWeight() {
	return weight;
}

/**
 * @param weight the weight to set
 */
public void setWeight(String weight) {
	this.weight = weight;
}

/**
 * @return the device_type
 */
public String getDevice_type() {
	return device_type;
}

/**
 * @param device_type the device_type to set
 */
public void setDevice_type(String device_type) {
	this.device_type = device_type;
}

/**
 * @return the lastUpdateDate
 */
public Date getLastUpdateDate() {
	return lastUpdateDate;
}

/**
 * @param lastUpdateDate the lastUpdateDate to set
 */
public void setLastUpdateDate(Date lastUpdateDate) {
	this.lastUpdateDate = lastUpdateDate;
}

public String isSelectedGender(String gender_compare) {
	String selected = new String("");
	if(this.gender.equals(gender_compare)) {
		selected = "checked";
	}
	return selected;
}

public String isSelectedDevice(String select_type) {
	String selected = new String("");
	if(this.device_type.equals(select_type)) {
		selected = "selected";
	}
	return selected;
}

/**
 * @return the gender
 */
public String getGender() {
	return gender;
}

/**
 * @param gender the gender to set
 */
public void setGender(String gender) {
	this.gender = gender;
}

}
