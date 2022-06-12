package com.awesome.testing;

import com.awesome.testing.dto.*;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class EditUsersTest extends AbstractRestAssuredTest {

    private static final Faker FAKER = new Faker();

    private RegisterDto user;
    private String token;

    @BeforeMethod
    public void setUp() {
        user = registerAndGetUser();
        LoginDto loginBody = new LoginDto(user.getUsername(), user.getPassword());
        token = loginAndGetToken(loginBody);
    }

    @AfterMethod
    public void cleanUp() {
        deleteUser(user.getUsername(), token);
    }

    @Test
    public void shouldBeAbleToEditUser() {
        String email = FAKER.internet().emailAddress();
        EditUserDto newUser = EditUserDto.builder()
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .email(email)
                .roles(user.getRoles())
                .build();

        editUser(newUser, user.getUsername(), token);

        UserResponseDto editedUser = getSpecificUser(user.getUsername(), token);

        Assertions.assertThat(editedUser.getFirstName()).isEqualTo(newUser.getFirstName());
        Assertions.assertThat(editedUser.getLastName()).isEqualTo(newUser.getLastName());
        Assertions.assertThat(editedUser.getEmail()).isEqualTo(newUser.getEmail());
    }
}
