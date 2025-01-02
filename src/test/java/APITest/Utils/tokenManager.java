package APITest.Utils;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class tokenManager {

    private static String token;
    private static final String baseUri = "https://reqres.in";

    public static String getToken() {
        if (token == null) {
            generateToken();
        }
        return token;
    }

    private static void generateToken() {
        String body = String.format("""
            {
                "email": "eve.holt@reqres.in",
                "password": "cityslicka"
            }""");

        token = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}