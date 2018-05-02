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
  public Date lastUpdateDate;

  /**
   * no arg constructor
   **/
  public Profile() {}
  
  /**
   * Takes all important fields
   **/
  public Profile(String id, String email, String fname, String lname, String year, String h, String w) {
	  this.member_id = id;
	  this.email_address = email;
	  this.first_name = fname;
	  this.last_name = lname;
	  this.birth_year = year;
	  this.height = h;
	  this.weight = w;
	  this.lastUpdateDate = new Date();
  }
  
  public String toString() 
  {
	  String profileString = 
			  "ID: " + this.id + "\n" +
			  "Member ID:" + this.member_id + "\n" +
			  "Email Address: " + this.email_address + "\n" +
			  "First Name: " + this.first_name + "\n" +
			  "Last Name: " + this.id + "\n" +
			  "Birth Year: " + this.birth_year + "\n" +
			  "Height: " + this.height + "\n" +
			  "Weight: " + this.weight + "\n" +
			  "Last Update: " + this.lastUpdateDate.toString();
	  return profileString;
  }

}
