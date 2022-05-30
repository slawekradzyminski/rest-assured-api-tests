package com.awesome.testing;

import com.awesome.testing.util.UserProvider;
import io.restassured.http.ContentType;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static com.awesome.testing.config.YmlParser.getConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RegisterTest extends AbstractRestAssuredTest {

    @Test
    public void shouldSuccessfullyRegister() {
        val registerBody = UserProvider.getRandomUser();

        given().body(registerBody).contentType(ContentType.JSON)
                .when().log().all()
                .post("/signup")
                .then().statusCode(201);
    }

    @Test
    public void shouldValidateFirstNameLength() {
        val registerBody = UserProvider.getRandomUserBuilder()
                .firstName(RandomStringUtils.randomAlphabetic(1))
                .build();

        given().body(registerBody).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().statusCode(getConfig().getStatusCode().getBadRequest())
                .body("firstName", is("Minimum firstName length: 4 characters"));
    }

    @Test
    public void shouldValidateEmailLength() {
        val registerBody = UserProvider.getRandomUserBuilder()
                .email(RandomStringUtils.randomAlphabetic(10))
                .build();

        given().body(registerBody).contentType(ContentType.JSON)
                .when()
                .log().all()
                .post("/signup")
                .then().statusCode(400)
                .body("email", is("musi być poprawnie sformatowanym adresem e-mail"));
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
                .when()
                .log().all()
                .post("/signup")
                .then()
                .log().all()
                .statusCode(400)
                .body("email", is("nie może być puste"));
    }
}
