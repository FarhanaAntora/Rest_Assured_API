import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Customer {
    Properties prop = new Properties();
    FileInputStream file = new FileInputStream("./src/test/resources/config.properties");

    public Customer() throws FileNotFoundException {
    }


    public void callingCustomerLoginAPI() throws IOException, ConfigurationException {
        prop.load(file);

        RestAssured.baseURI = prop.getProperty("baseUrl");

        String username = "salman";
        String password = "salman1234";
        Response response = (Response) given()
                .contentType("application/json")
                .body("{\n" +
                        "\"username\":\"salman\",\n" +
                        "\"password\":\"salman1234\"\n" +
                        "}")
                .when()
                .post("/customer/api/v1/login")
                .then()
                .assertThat().statusCode(200).extract().response();


        JsonPath resObj = response.jsonPath();
        String token = resObj.get("token");
        Utils.setEnvVariable("token", token);
        System.out.println("token : " + token);
    }

    public void callingCustomerListAPI() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .when()
                        .get("/customer/api/v1/list")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());


        JsonPath jsonObj = response.jsonPath();
        String id = jsonObj.get("Customers[0].id").toString();
        Assert.assertEquals("101", id);
    }

    public void callingSearchCustomerAPI() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .when()
                        .get("/customer/api/v1/get/101")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());


        JsonPath jsonObj = response.jsonPath();
        String name = jsonObj.get("name");
        Assert.assertEquals(" Mr. Rafiq", name);


    }

    public Integer ID;
    public String name;
    public String email;
    public String address;
    public String phone_number;

    public void callingGenerateCustomerAPI() throws ConfigurationException, IOException {

        prop.load(file);
        RestAssured.baseURI = "https://randomuser.me";
        Response res =

                given()
                        .contentType("application/json")
                        .when()
                        .get("/api")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath resObj = res.jsonPath();
        ID = (int) Math.floor(Math.random() * (999999 - 100000) + 1);
        name = "Name " + resObj.get("results[0].name.first");
        email = resObj.get("results[0].email");
        address = resObj.get("results[0].location.state");
        phone_number = resObj.get("results[0].cell");
        Utils.setEnvVariable("id", ID.toString());
        Utils.setEnvVariable("name", name);
        Utils.setEnvVariable("email", email);
        Utils.setEnvVariable("address", address);
        Utils.setEnvVariable("phone_number", phone_number);
        System.out.println(res.asString());
    }

    public void callingCreateCustomerAPI() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .body("" +
                                "{\"id\":" + prop.getProperty("id") + ",\n" +
                                "    \"name\":\"" + prop.getProperty("name") + "\", \n" +
                                "    \"email\":\"" + prop.getProperty("email") + "\",\n" +
                                "    \"address\":\"" + prop.getProperty("address") + "\",\n" +
                                "    \"phone_number\":\"" + prop.getProperty("phone_number") + "\"}")
                        .when()
                        .post("/customer/api/v1/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        System.out.println(response.asString());
    }


    public void updateCustomerAPI() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .body("{\n" +
                                "    \"id\":101,\n" +
                                "    \"name\":\" Mr. Rafiq\", \n" +
                                "    \"email\":\"rafiq101@gmail.com\",\n" +
                                "    \"address\":\"Banani,Dhaka\",\n" +
                                "    \"phone_number\":\"01502020117\"\n" +
                                "}")

                        .when()
                        .put("/customer/api/v1/update/101")
                        .then()
                        .assertThat().statusCode(200).extract().response();
        //System.out.println(response.asString());
        //JsonPath jsonObj = response.jsonPath();
        //String name = jsonObj.get("name");
        //Assert.assertEquals(" Mr. Rafiq", name);
    }


    public void deleteCustomerAPI() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))


                        .when()
                        .delete("/customer/api/v1/delete/175299")
                        .then()
                        .assertThat().statusCode(200).extract().response();

    }
}
