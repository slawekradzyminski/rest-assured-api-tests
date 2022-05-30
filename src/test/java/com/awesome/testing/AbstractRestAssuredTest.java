package com.awesome.testing;

import com.awesome.testing.dto.LoginDto;
import com.awesome.testing.dto.LoginResponseDto;
import com.awesome.testing.dto.RegisterDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static com.awesome.testing.config.YmlParser.getConfig;
import static com.awesome.testing.util.UserProvider.getRandomUser;
import static io.restassured.RestAssured.given;

public abstract class AbstractRestAssuredTest {

    @BeforeClass
    public static void setUpRestAssured() {
        RestAssured.baseURI = getConfig().getUrl();
    }

    protected String loginAndGetToken(LoginDto loginBody) {
        Response response = given().contentType(ContentType.JSON).body(loginBody)
                .when().log().all()
                .post("/signin");

        response.then().statusCode(getConfig().getStatusCode().getOk());

        return response.as(LoginResponseDto.class).getToken();
    }

    protected RegisterDto registerAndGetUser() {
        RegisterDto randomUser = getRandomUser();
        given().body(randomUser).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().statusCode(201);

        return randomUser;
    }

    protected void deleteUser(String userName, String token) {
        given().log().all()
                .header(new Header("Authorization", "Bearer " + token))
                .delete("/" + userName)
                .then().statusCode(204);
    }
}
