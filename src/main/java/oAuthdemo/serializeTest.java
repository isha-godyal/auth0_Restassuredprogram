package oAuthdemo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
// Serialization to pass object in request body
public class serializeTest {

	public static void main(String[] args) {
		// set base url using baseURI datamember of RestAssured
		RestAssured.baseURI = "https://rahulshettyacademy.com";


		// Create place object and set value which we can pass in request body of addPlace call
		AddPlace place = new AddPlace();
		place.setAccuracy(50);
		place.setAddress("29, side layout, cohen 09");
		place.setLanguage("French-IN");
		place.setPhone_number("(+91) 983 893 3937");
		place.setWebsite("https://rahulshettyacademy.com");
		place.setName("Frontline house");
		
		// create list to store types
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		// set types in place object using setTypes method
		place.setTypes(myList);
		
		// Created nested object location and set lat and long
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);

	    // set location value in place using setLocation method
		place.setLocation(loc);
		
		
		Response res = given().log().all().queryParam("key", "qaclick123").body(place).when()
				.post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response();

		String responseString = res.asString();
		System.out.println(responseString);

	}

}
