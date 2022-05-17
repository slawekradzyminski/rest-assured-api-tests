package com.awesome.testing.login;

import com.awesome.testing.AbstractRestAssuredTest;
import com.awesome.testing.dto.LoginDto;
import com.awesome.testing.dto.RegisterDto;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SuccessfulLoginTest extends AbstractRestAssuredTest {

    private RegisterDto user;
    private String token;

    @BeforeMethod
    public void setUp() {
        user = registerAndGetUser();
    }
    
    @AfterMethod
    public void cleanUp() {
        deleteUser(user, token);
    }
    
    @Test
    public void shouldSuccessfullyLogin() {
        LoginDto loginBody = new LoginDto(user.getUsername(), user.getPassword());
        token = loginAndGetToken(loginBody);
    }

}
