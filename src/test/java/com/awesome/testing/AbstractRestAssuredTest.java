package com.awesome.testing;

import com.awesome.testing.dto.LoginDto;
import com.awesome.testing.dto.LoginResponseDto;
import com.awesome.testing.dto.RegisterDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.testng.annotations.BeforeClass;

import static com.awesome.testing.util.UserProvider.getRandomUser;
import static io.restassured.RestAssured.given;

public abstract class AbstractRestAssuredTest {

    @BeforeClass
    public static void setUpRestAssured() {
        RestAssured.baseURI = "http://localhost:4001/users";
    }

    protected String loginAndGetToken(LoginDto loginBody) {
        return given().contentType(ContentType.JSON).body(loginBody)
                .when().post("/signin").as(LoginResponseDto.class).getToken();
    }

    protected RegisterDto registerAndGetUser() {
        RegisterDto randomUser = getRandomUser();
        given().body(randomUser).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().statusCode(201);

        return randomUser;
    }

    protected void deleteUser(RegisterDto user, String token) {
        given().header(new Header("Authorization", "Bearer " + token))
                .delete("/" + user.getUsername())
                .then().statusCode(204);
    }
}
