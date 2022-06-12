package com.awesome.testing.login;

import com.awesome.testing.AbstractRestAssuredTest;
import com.awesome.testing.dto.LoginDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class FailedLoginTest extends AbstractRestAssuredTest {

    @Test
    public void shouldFailToLoginIfWrongCredentials() {
        LoginDto loginDto = new LoginDto("wrong", "wrong");

        given().contentType(ContentType.JSON).body(loginDto)
                .when()
                .post("/signin")
                .then()
                .statusCode(statusCode.getUnprocessableEntity())
                .body("error", is("Unprocessable Entity"));
    }

    @Test
    public void shouldHandleMissingKeyBody() {
        String invalidRequest =
                "{\n" +
                        " \"username\": \"jamar.greenholtA\" \n" +
                        "}";

        given().body(invalidRequest).contentType(ContentType.JSON)
                .when()
                .post("/signin")
                .then()
                .statusCode(statusCode.getUnprocessableEntity());
    }
}
