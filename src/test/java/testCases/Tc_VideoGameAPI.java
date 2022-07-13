package testCases;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class Tc_VideoGameAPI {
   @Test(priority=1)
	public void test_getAllVideoGames() {
		
	given()
	
	.when()
	    .get("http://localhost:8080/app/videogames")
	 .then()
	     .statusCode(200).log().all();
}
    @Test(priority=2)
      public void test_addNewVideoGame() {
    	  HashMap data=new HashMap();
    	  data.put("id","104");
    	  data.put("name","jungleMan");
    	  data.put("releaseDate","2022-07-12T14:14:16.638Z");
    	  data.put("reviewScore","5");
    	  data.put("category","Adventure");
    	  data.put("rating","Universal");
    	  
    String res= given().contentType("application/json")
    	         .body(data)
    	 .when()
    	       .post("http://localhost:8080/app/videogames")
    	 .then()
    	        .statusCode(200).log().all()
    	        .extract().response().asString();
    System.out.println(res);
    	  JsonPath js=new JsonPath(res);
    	  String status=js.getString("status");
    	  Assert.assertEquals( status, "Record Added Successfully");
    	  
      }
    @Test(priority=3)
    
    public void test_getVideoGame() {
    	
    	given()
    	.when()
    	.get("http://localhost:8080/app/videogames/104")
    	.then()
    	.statusCode(200)
    	.body("videoGame.id", equalTo("104")).log().all()
    	.body("videoGame.name", equalTo("jungleMan"));
    	
        }
    @Test(priority=4)
    public void test_updateVideoGame() {
    	
  	  HashMap data=new HashMap();
	  data.put("id","104");
	  data.put("name","thor");
	  data.put("releaseDate","2022-07-13T14:14:16.638Z");
	  data.put("reviewScore","4");
	  data.put("category","Adventure");
	  data.put("rating","Universal");
	  
	  given().contentType("application/json").body(data)
	  .when()
	  .put("http://localhost:8080/app/videogames/104")
	  .then()
	  .statusCode(200)
	  .body("videoGame.name", equalTo("thor")).log().all();
    	
    }
    @Test(priority=5)
    public void test_deleteVideoGame() {
    	
    	String response=given()
    	.when()
    	.delete("http://localhost:8080/app/videogames/104")
    	.then()
    	.statusCode(200).log().body()
    	.extract().response().asString();
    	
    	JsonPath js=new JsonPath(response);
    	String status=js.get("status");
    	
    	Assert.assertEquals(status, "Record Deleted Successfully");
    	
    	
    	
    	
    }
}
