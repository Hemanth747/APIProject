package Sample;
import io.restassured.RestAssured;
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
public class Block_User 
{
	Logger L=Logger.getLogger("Block_User");
	Properties prop = new Properties(); 
	@BeforeTest
	public void Blockuser() throws Exception
	{
		PropertyConfigurator.configure("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\Log4j.properties");
		FileInputStream  fis = new FileInputStream("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\src\\Sample\\base.properties");
		prop.load(fis);
	}
	@Test
	public void method() throws Exception
	{
		String Consumerkey =prop.getProperty("Consumerkey");
		String ConsumerSecretkey =prop.getProperty("ConsumerSecretkey");
		String Token =prop.getProperty("Token");
		String TokenSecretkey =prop.getProperty("TokenSecretkey");
		
		 RestAssured.baseURI="https://api.twitter.com/1.1";
		 Response res=given().auth().oauth(Consumerkey, ConsumerSecretkey, Token, TokenSecretkey).
		 queryParam("screen_name", "Shaik_Irshad0").
		 when().post("/blocks/create.json").then().extract().response();
		 String responce=res.asString();
		 L.info(responce);
			System.out.println(responce);
			JsonPath js=new JsonPath(responce);
			String id=js.get("name").toString();
			System.out.println(id);
			L.info(id);
	}		
			
			@AfterTest
			public void unblock() throws Exception
			{
				String Consumerkey =prop.getProperty("Consumerkey");
				String ConsumerSecretkey =prop.getProperty("ConsumerSecretkey");
				String Token =prop.getProperty("Token");
				String TokenSecretkey =prop.getProperty("TokenSecretkey");
				
				 RestAssured.baseURI="https://api.twitter.com/1.1";
				 Response res1=given().auth().oauth(Consumerkey, ConsumerSecretkey, Token, TokenSecretkey).
				 queryParam("screen_name", "Shaik_Irshad0").	 
				 when().post("/blocks/destroy.json").then().extract().response();
				 
				 String responce1=res1.asString();
				 L.info(responce1);
				 System.out.println(responce1);
				 JsonPath js=new JsonPath(responce1);
				 String id=js.get("name").toString();
				 System.out.println(id);
				 L.info(id);
			}
			
				 
	}
	


