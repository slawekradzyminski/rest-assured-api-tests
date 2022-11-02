package com.awesome.testing;

import com.awesome.testing.config.LocalConfig;
import com.awesome.testing.config.SpringConfig;
import com.awesome.testing.config.StatusCode;
import com.awesome.testing.dto.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;


import static com.awesome.testing.util.UserProvider.getRandomUser;
import static io.restassured.RestAssured.given;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringConfig.class)
@EnableConfigurationProperties
public abstract class AbstractRestAssuredTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected LocalConfig config;

    @Autowired
    protected StatusCode statusCode;
    
    @BeforeClass
    public void setUpRestAssured() {
        RestAssured.baseURI = config.getUrl();
    }

    protected String loginAndGetToken(LoginDto loginBody) {
        Response response = given().contentType(ContentType.JSON).body(loginBody)
                .when().log().all()
                .post("/signin");

        response.then().statusCode(statusCode.getOk());

        return response.as(LoginResponseDto.class).getToken();
    }

    protected RegisterDto registerAndGetUser() {
        RegisterDto randomUser = getRandomUser();
        given().body(randomUser).contentType(ContentType.JSON)
                .when().post("/signup")
                .then().statusCode(statusCode.getCreated());

        return randomUser;
    }

    protected UserResponseDto getSpecificUser(String userName, String token) {
        Response response = given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .when()
                .get("/" + userName);
        response.then().statusCode(statusCode.getOk());

        return response.as(UserResponseDto.class);
    }

    protected Response editUser(EditUserDto editedUser, String usernameToEdit, String token) {
        Response response = given()
                .header(new Header("Authorization", "Bearer " + token))
                .contentType(ContentType.JSON).body(editedUser)
                .when()
                .put("/" + usernameToEdit);

        response.then().statusCode(statusCode.getOk());

        return response;
    }

    protected void deleteUser(String userName, String token) {
        given()
                .header(new Header("Authorization", "Bearer " + token))
                .delete("/" + userName)
                .then().statusCode(statusCode.getDeleted());
    }
}
