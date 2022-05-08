package com.awesome.testing;

import com.awesome.testing.dto.LoginDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoginTest extends AbstractRestAssuredTest {

    @Test
    public void shouldSuccessfullyLogin() {
        LoginDto loginBody = new LoginDto("admin", "admin");
        given().contentType(ContentType.JSON).body(loginBody)
                .log().all()
                .when().post("/signin")
                .then().log().all().statusCode(200);
    }

}
