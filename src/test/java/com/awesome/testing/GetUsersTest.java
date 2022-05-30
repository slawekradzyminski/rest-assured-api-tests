package com.awesome.testing;

import com.awesome.testing.dto.LoginDto;
import com.awesome.testing.dto.UserResponseDto;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUsersTest extends AbstractRestAssuredTest {

    @Test
    public void shouldGetAllUsers() {
        LoginDto loginBody = new LoginDto("admin", "admin");
        String token = loginAndGetToken(loginBody);

        UserResponseDto[] users = given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .when().get("").as(UserResponseDto[].class);

        assertThat(users).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    public void shouldNotGetAllUsersWithoutToken(){
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer 123"))
                .when().get("").then().statusCode(403);
    }

}
