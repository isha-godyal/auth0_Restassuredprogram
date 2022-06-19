package oAuthdemo;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuthTest {

	public static void main(String[] args) {
		
	

//		Hit Below url on browser  
//		https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyisha
//		and login with google account and paste url redirect in below String

		String resp = "https://rahulshettyacademy.com/getCourse.php?state=verifyisha&code=4%2F0AX4XfWgTUUuOGCstWsEUi4Xn2uvDoJkq2BxPJMfVutGF80if7bQNL_XQjCdumvrcKLKFPA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none";

		String partialcode = resp.split("code=")[1];

		String code = partialcode.split("&scope")[0];

		System.out.println("Auth code : " + code);

		// First hit auth url to get Access token
		String accessTokenResponse = given().urlEncodingEnabled(false) // default encoding by restassured will be true
				.queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type", "authorization_code").queryParams("state", "verifyisha")
				.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").when()
//				.log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		System.out.println("Access Token response :" + accessTokenResponse);

		// using JsonPath object fetch the value of accessToken received upper response
		JsonPath jsonPath = new JsonPath(accessTokenResponse);

		String accessToken = jsonPath.getString("access_token");

		System.out.println("Access TOken :" + accessToken);

		// hit access url by passing accessToken in query params
		// Converting Response body in GetCourse object using as(GetCourse.class)  (Deserilization)
		GetCourse gc = given().queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON)   // check whether Response is JSON  or we skip if response-type is application/json in response headers
				.when()
//				.log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);   // convert respoonse as GetCourse object

		System.out.println("LinkedIn :"+ gc.getLinkedIn());
		System.out.println("Instructor :"+gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		String price = gc.getCourses().getMobile().get(0).getPrice();

		System.out.println("Price :"+price);
	
		
		System.out.println("Finding the price of SoapUI Webservices testing");
		List<Api> apiCourses=gc.getCourses().getApi();
	
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
					{
							System.out.println(apiCourses.get(i).getPrice());
					}
		}
		
		System.out.println("Get the course names of WebAutomation");
		
		String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};
		
		
		ArrayList<String> titles= new ArrayList<String>();
		
		
		List<WebAutomation> w=gc.getCourses().getWebAutomation();
		
		for(int j=0;j<w.size();j++)
		{
			titles.add(w.get(j).getCourseTitle());
		}
		
		List<String> expectedList=	Arrays.asList(courseTitles);
		System.out.println("expectedList :"+expectedList);
		System.out.println("actualList :"+titles);
		
		
		Assert.assertTrue(titles.equals(expectedList));
	}

}
