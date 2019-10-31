package Sample;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.util.Properties;

public class GetTweet
{
	Logger L=Logger.getLogger("GetTweet");   
	Properties prop = new Properties(); 
	@Test
	public void gettweet() throws Exception
	{
		PropertyConfigurator.configure("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\Log4j.properties");
		FileInputStream  fis = new FileInputStream("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\src\\Sample\\base.properties");
		prop.load(fis);
	}
	@AfterTest
	public void method()
	{
		String Consumerkey =prop.getProperty("Consumerkey");
		String ConsumerSecretkey =prop.getProperty("ConsumerSecretkey");
		String Token =prop.getProperty("Token");
		String TokenSecretkey =prop.getProperty("TokenSecretkey");
		
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		Response res = given().auth().oauth(Consumerkey,ConsumerSecretkey,Token,TokenSecretkey).
		queryParam("count", "1").
		when().get("/home_timeline.json").then().extract().response();
		
		String response = res.asString();
		L.info(response);
		System.out.println("Tittle = "+response);
		JsonPath js = new JsonPath(response);
		
		String id = js.get("id").toString();
		System.out.println("Id = "+id);
		String text = js.get("text").toString();
		System.out.println("Text ="+text);
	}
}
