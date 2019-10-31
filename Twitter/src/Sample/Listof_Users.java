package Sample;
import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class Listof_Users {
	Logger L=Logger.getLogger("Listof_Users");   
	Properties prop = new Properties(); 
    @Test
    public void listusers() throws Exception 
    {
    	PropertyConfigurator.configure("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\Log4j.properties");
		FileInputStream  fis = new FileInputStream("C:\\Users\\Qualitest\\git\\twitter\\Twitter\\src\\Sample\\base.properties");
		prop.load(fis);
    }
    @AfterTest
    public void Listuser()
    {
		String Consumerkey =prop.getProperty("Consumerkey");
		String ConsumerSecretkey =prop.getProperty("ConsumerSecretkey");
		String Token =prop.getProperty("Token");
		String TokenSecretkey =prop.getProperty("TokenSecretkey");
		
        RestAssured.baseURI="https://api.twitter.com/1.1/search";
        Response res=given().auth().oauth( Consumerkey, ConsumerSecretkey, Token, TokenSecretkey).
        param("q","Qualitest").
        when().get("/tweets.json").then().assertThat().statusCode(200).
        and().contentType(ContentType.JSON).extract().response();
        
        String response=res.asString();
        L.info(response);
        System.out.println(response);
        JsonPath js=new JsonPath(response);
        
        int count=js.get("statuses.size()");
        System.out.println(count);
        for(int a=0;a<count;a++)
        {
            String user_name=js.get("statuses["+a+"].user.screen_name");
            System.out.println("User_Name = "+user_name);
        }
    }
}
 