package oAuthdemo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Create place object and set value which we can pass in request body of
		// addPlace call
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

		// Create request speccs or rule for any request which use the specs/rule
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();


		// Create response speccs or rule for any response which check for this specs/rule
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		RequestSpecification res = given().spec(req).body(place);

		Response response = res.when().post("/maps/api/place/add/json").then().spec(resspec).extract().response();

		String responseString = response.asString();
		System.out.println(responseString);

	}

}
