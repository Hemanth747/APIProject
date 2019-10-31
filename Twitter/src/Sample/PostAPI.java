package Sample;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PostAPI 
{
	Logger L=Logger.getLogger("CreateTweet");
	Properties prop = new Properties(); 
	@BeforeTest
	public void createtweet() throws Exception 
	{
		PropertyConfigurator.configure("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\Log4j.properties");
		FileInputStream  fis = new FileInputStream("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\src\\Sample\\base.properties");
		prop.load(fis);
	}
	@Test
	public void method()
	{
		PropertyConfigurator.configure("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\Log4j.properties");
		String Consumerkey =prop.getProperty("Consumerkey");
		String ConsumerSecretkey =prop.getProperty("ConsumerSecretkey");
		String Token =prop.getProperty("Token");
		String TokenSecretkey =prop.getProperty("TokenSecretkey");
		
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		Response res = given().auth().oauth(Consumerkey, ConsumerSecretkey, Token, TokenSecretkey).
		queryParam("status", " “I am learning API testing using RestAssured in Java1 #Qualitest").when()
		.post("/update.json").then().extract().response();
		
		String response = res.asString();
		L.info(response);
		System.out.println(response);
		JsonPath js = new JsonPath(response);
	
		String id = js.get("id").toString();
		System.out.println(id);
		String text = js.get("text").toString();
		System.out.println("text ="+text);
		L.info(text);
		
		//For_deletion
	/*	Response res1 = given().auth().oauth(prop.getProperty(Consumerkey),prop.getProperty(ConsumerSecretkey) ,prop.getProperty(Token) , prop.getProperty(TokenSecretkey)).
		when().post("/destroy/"+id+".json").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response(); 
		String response1 = res1.asString();
		System.out.println("deleted = "+response1);
		L.info(response1);  */
	}

}
