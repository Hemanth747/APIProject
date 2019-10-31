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
public class Trends {
				 //india    //US     //UK     //Israel
	 int[] k = {1,23424848,23424977,23424975,23424852};
	 Properties prop = new Properties(); 
	 Logger L=Logger.getLogger("Trends");   
	@Test
	public void Updatetweets() throws Exception
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
		
		for(int i=0;i<k.length;i++)
		{
		RestAssured.baseURI="https://api.twitter.com/1.1/trends";
		Response res=given().auth().oauth(Consumerkey, ConsumerSecretkey, Token, TokenSecretkey).
		queryParam("id",k[i]).
		when().get("/place.json").then().extract().response();
		
		String responce=res.asString();
		L.info(responce);
		System.out.println(responce);
		
		JsonPath js=new JsonPath(responce);
		String id=js.get("name").toString();
		System.out.println("Place_Id_Info ="+id);
	}
}
}
