package com.selenium.apitest;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class PetStoreRestApi {

    @Test
    public void createPetStoreApiUserSuccess_ReturnsOk(){
          Response response =  given().header("Content-Type","application/json")
                              .header("accept","application/json")
                         .body("{\n" +
                         "  \"id\": 0,\n" +
                          "  \"username\": \"ymail\",\n" +
                         "  \"firstName\": \"YmailT\",\n" +
                         "  \"lastName\": \"test\",\n" +
                         "  \"email\": \"test@gmail.com\",\n" +
                         "  \"password\": \"ymail\",\n" +
                         "  \"phone\": \"string\",\n" +
                         "  \"userStatus\": 0\n" +
                         "}")
                         .when()
                         .post("https://petstore.swagger.io/v2/user");
          response.prettyPrint();
          response.then().assertThat().statusCode(200);
    }

    @Test
    public void loggedInToPetStoreAppSuccess_ReturnsOk(){
        Response response = given().header("accept","application/json")
                .queryParam("user","ymail")
                .queryParam("pass","ymail")
                .when()
                .get("https://petstore.swagger.io/v2/user/login?user&pass");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getUserByNameSuccess_ReturnOk(){
        Response response = given().accept("application/json")
                .pathParam("user","sheetal")
                .when()
                .get("https://petstore.swagger.io/v2/user/{user}");
        response.prettyPrint();
        response.then().statusCode(200);
        String emailText = response.path("username");
        System.out.println("userEmail : "+emailText);
    }

    @Test
    public void updateExistingUserInfoSuccess_ReturnOk(){
        Response response = given().accept("application/json")
                .contentType("application/json")
                .pathParam("username","ymail")
                .body("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"username\": \"ymail\",\n" +
                        "  \"firstName\": \"string\",\n" +
                        "  \"lastName\": \"string\",\n" +
                        "  \"email\": \"string@gmail.com\",\n" +
                        "  \"password\": \"string\",\n" +
                        "  \"phone\": \"string\",\n" +
                        "  \"userStatus\": 0\n" +
                        "}")
                .when()
                .put("https://petstore.swagger.io/v2/user/{username}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void deleteUserFromPetStoreSuccess_ReturnOk(){
        Response response = given().accept("application/json")
                .pathParam("username","ymail")
                .when()
                .delete("https://petstore.swagger.io/v2/user/{username}");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void uploadImageForPetSuccess_ReturnOk(){
        File file = new File("C:\\Users\\Admin\\Downloads\\photo-1579393329936-4bc9bc673651.jpg");
        Response response = given().accept("application/json")
                .multiPart(file)
                .when()
                .post("https://petstore.swagger.io/v2/pet/101/uploadImage");

        response.prettyPrint();
        response.then().statusCode(200);
    }
}
