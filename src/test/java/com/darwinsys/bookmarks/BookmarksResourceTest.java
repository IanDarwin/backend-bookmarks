package com.darwinsys.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class BookmarksResourceTest {

    @Test
    public void testGetById() {
        given()
          .when().get("/rs/bookmarkById/1")
          .then()
             .statusCode(200)
             .body(containsString("economy"));
    }

    @Test
    public void testSearch() {
        given()
          .when().get("/bookmarksSearch/economy")
          .then()
             .statusCode(200)
             .body(containsString("economy"));
    }
}
