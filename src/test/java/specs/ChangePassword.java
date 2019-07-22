package specs;

import configuration.Config;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangePassword {

    static private String userId;

    @BeforeAll
    static void setUp() {
        //Test Data setup to be used in subsequent test
        userId = given()
                .contentType("application/json")
                .body("{\n" +
                        " \t\"username\" : \"Bilbo Baggins\",\n" +
                        "\t\"currentPassword\" : \"burglar\"\n" +
                        "}")
                .when()
                .post(Config.BASE_URL + "/user")
                .then()
                .extract()
                .header("id");
    }

    @Test
    void verifyUserCanChangePasswordWithValidNewPassword() {
        Response response = given()
                .contentType("application/json")
                .body("{\n" +
                        " \t\"username\" : \"Bilbo Baggins\",\n" +
                        "\t\"currentPassword\" : \"burglar\",\n" +
                        "\t\"newPassword\": \"burglar@43490Parrot\"\n" +
                        "}")
                .when()
                .put(Config.BASE_URL + "/user/" + userId);

        assertEquals(200, response.getStatusCode());
        assertEquals("true", response.getBody().asString());
    }

    @Test
    void verifyUserCannotChangePasswordWithInValidNewPassword() {
        Response response = given()
                .contentType("application/json")
                .body("{\n" +
                        " \t\"username\" : \"Bilbo Baggins\",\n" +
                        "\t\"currentPassword\" : \"burglar@43490Parrot\",\n" +
                        "\t\"newPassword\" : \"password@345p\"\n" +
                        "}")
                .when()
                .put(Config.BASE_URL + "/user/" + userId);

        assertEquals(200, response.getStatusCode());
        assertEquals("false", response.getBody().asString());
    }

    @AfterAll
    static void tearDown() {
        given()
                .when()
                .delete(Config.BASE_URL + "/user/" + userId);
    }
}
