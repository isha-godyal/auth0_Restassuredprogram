package oAuthdemo;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;

public class oAuthTest {

	public static void main(String[] args) {

//		Hit Below url on browser  
//		https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyisha
//		and login with google account and paste url redirect in below String

		String resp = "https://rahulshettyacademy.com/getCourse.php?state=verifyisha&code=4%2F0AX4XfWiTyMUSpY0LdMxJqi3yQFL9nMZ39xseGYrF5KkY02qVtyIDaaoHmSShPkSrsncJQA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none";

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
		GetCourse gc = given().queryParam("access_token", accessToken)
				.expect().defaultParser(Parser.JSON)   // check whether Response is JSON  or we skip if response-type is application/json in response headers
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);   // convert respoonse as GetCourse object

		System.out.println("LinkedIn :"+ gc.getLinkedIn());
		System.out.println("Instructor :"+gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

//		System.out.println(response);

	}

}
