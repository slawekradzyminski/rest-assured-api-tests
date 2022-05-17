package com.awesome.testing;

import com.awesome.testing.dto.RegisterDto;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static com.awesome.testing.util.UserProvider.getRandomUser;
import static com.awesome.testing.util.UserProvider.getRandomUserBuilder;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RegisterTest extends AbstractRestAssuredTest {

    @Test
    public void shouldSuccessfullyRegister() {
        RegisterDto registerBody = getRandomUser();

        given().body(registerBody).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().statusCode(201);
    }

    @Test
    public void shouldValidateFirstNameLength() {
        RegisterDto registerBody = getRandomUserBuilder()
                .firstName(RandomStringUtils.randomAlphabetic(3))
                .build();

        given().body(registerBody).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().statusCode(400)
                .body("firstName", is("Minimum firstName length: 4 characters"));
    }

    @Test
    public void shouldValidateEmailLength() {
        RegisterDto registerBody = getRandomUserBuilder()
                .email(RandomStringUtils.randomAlphabetic(10))
                .build();

        given().body(registerBody).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().statusCode(400)
                .body("email", is("must be a well-formed email address"));
    }

    @Test
    public void shouldValidateMissingKey() {
        String invalidRequest = "{\n" +
                "    \"firstName\": \"Haywood7\",\n" +
                "    \"lastName\": \"Block2\",\n" +
                "    \"password\": \"udox6tqayepyrD\",\n" +
                "    \"roles\": [\n" +
                "        \"ROLE_ADMIN\",\n" +
                "        \"ROLE_CLIENT\"\n" +
                "    ],\n" +
                "    \"username\": \"thurman.monahan\"\n" +
                "}";

        given().body(invalidRequest).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().log().all().statusCode(400)
                .body("email", is("must not be empty"));
    }

}
