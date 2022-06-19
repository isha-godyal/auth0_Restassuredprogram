package oAuthdemo;

import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

public class oAuthTest {

	public static void main(String[] args) {

		
		// First hit auth url to get Access token 
		String accessTokenResponse = given()
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type", "authorization_code").queryParams("state", "verifyfjdss")
				.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
        
		// using JsonPath object fetch the value of accessToken received upper response
		JsonPath jsonPath = new JsonPath(accessTokenResponse);

		String accessToken = jsonPath.getString("access_token");

		System.out.println(accessToken);

		// hit access url by passing accessToken in query params
		String response = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").asString(); // convert raw response into string

		System.out.println(response);

	}

}
