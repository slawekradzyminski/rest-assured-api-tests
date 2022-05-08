package com.awesome.testing;

import com.awesome.testing.dto.LoginDto;
import org.testng.annotations.Test;

public class LoginTest extends AbstractRestAssuredTest {

    @Test
    public void shouldSuccessfullyLogin() {
        LoginDto loginBody = new LoginDto("admin", "admin");
        loginAndGetToken(loginBody);
    }

}
