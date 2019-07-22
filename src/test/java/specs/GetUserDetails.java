package specs;

import configuration.Config;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetUserDetails {
    static String userId = "1";

    @Test
    void verifyUserIsAbleToGetTheirDetails() {
        given()
                .when()
                .get(Config.BASE_URL + "/user/" + userId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("get_user_schema.json"));
    }
}
