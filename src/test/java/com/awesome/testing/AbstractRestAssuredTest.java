package com.awesome.testing;

import com.awesome.testing.dto.LoginDto;
import com.awesome.testing.dto.LoginResponseDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;

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

}
