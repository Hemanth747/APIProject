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
public class User_Information {
		 Properties prop = new Properties(); 
		 Logger L=Logger.getLogger("User_Information");   
	  @Test
	  public void User_Info() throws Exception
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
			
		  RestAssured.baseURI="https://api.twitter.com/1.1/statuses/"; 
		  Response res=given().auth().oauth(Consumerkey,ConsumerSecretkey,Token,TokenSecretkey).
				  queryParam("screen_name","HemanthMR7").
				  when().get("user_timeline.json").then().extract().response();
		          String s=res.asString();
		          L.info(s);
		          System.out.println("Timeline  ="+s);
		          JsonPath js=new JsonPath(s);
		          String id=js.getString("id").toString();
		          System.out.println("UserId ="+id);
		          
	  }
	}


