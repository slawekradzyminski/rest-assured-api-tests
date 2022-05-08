package com.awesome.testing;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public abstract class AbstractRestAssuredTest {

    @BeforeClass
    public static void setUpRestAssured() {
        RestAssured.baseURI = "http://localhost:4001/users";
    }

}
