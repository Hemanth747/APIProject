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
public class Home_title 
{
	Logger L=Logger.getLogger("Home_title");  
	Properties prop = new Properties(); 
	
        @Test
        public void tweet() throws Exception {
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
            Response res= given().auth().oauth(Consumerkey, ConsumerSecretkey, Token, TokenSecretkey).
            when().get("home_timeline.json").then().extract().response();
            String str=res.asString();
            L.info(str);
            System.out.println(str);
            JsonPath js=new JsonPath(str);
            String text=js.get("text").toString();
            System.out.println("Home_tittle = "+text);
            }
    }
 






