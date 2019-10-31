package Sample;
import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
public class weather_update {    
	Properties prop = new Properties(); 
	 Logger L=Logger.getLogger("weather_update");   
    @Test
    public void searchtweet() throws IOException
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
		
        RestAssured.baseURI="https://api.twitter.com/1.1/search/";
        Response res=given().auth().oauth(Consumerkey, ConsumerSecretkey, Token, TokenSecretkey).
        queryParam("q","Bangalore Weather").
        when().get("tweets.json").then().extract().response();
        
        String responce=res.asString();
        L.info(responce);
        System.out.println("Weather_Response = "+responce);    
}
}







