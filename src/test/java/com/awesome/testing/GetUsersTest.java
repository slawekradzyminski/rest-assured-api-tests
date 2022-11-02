package com.awesome.testing;

import com.awesome.testing.dto.LoginDto;
import com.awesome.testing.dto.RegisterDto;
import com.awesome.testing.dto.UserResponseDto;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUsersTest extends AbstractRestAssuredTest {


    private static final Faker FAKER = new Faker();
    private RegisterDto user;
    private String token;

    @BeforeMethod
    public void setUp() {
        user = registerAndGetUser();
        LoginDto loginBody = new LoginDto("admin", "admin");
        token = loginAndGetToken(loginBody);
    }

    @AfterMethod
    public void cleanUp() {
        deleteUser(user.getUsername(), token);
    }

    @Test
    public void shouldGetAllUsers() {

        UserResponseDto[] users = given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .when()
                .get("").as(UserResponseDto[].class);

        assertThat(users).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    public void shouldNotGetAllUsersWithoutToken() {
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer 123"))
                .when()
                .get("")
                .then()
                .statusCode(statusCode.getForbidden());
    }

    @Test
    public void shouldGetSpecificUser() {
        UserResponseDto specificUser = getSpecificUser(user.getUsername(), token);

        assertThat(specificUser).usingRecursiveComparison().ignoringFields("id", "password").isEqualTo(user);
    }

    @Test
    public void shouldNotGetSpecificUserWithoutToken() {
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer 123"))
                .when()
                .get("/" + user.getUsername())
                .then()
                .statusCode(statusCode.getForbidden());
    }

    @Test
    public void shouldNotGetSpecificUserWhoDoesNotExist() {
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .when()
                .get("/" + FAKER.name().username() + FAKER.random().hex(1))
                .then()
                .statusCode(statusCode.getNotFound());
    }
}
