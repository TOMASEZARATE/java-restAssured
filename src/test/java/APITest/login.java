package APITest;


import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;



public class login {

    private String baseUri = "https://reqres.in";

    @Test(groups = "login")
    @Description("Listar usuarios")
    @Owner("Tomas Zarate")
    @Feature("endpoint /api/users")
    public void credencialesIncorrectas(){ 
        String body = String.format("""
    {
        "email": "peter@klaven"
    }""" );

        given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("api/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"))
                .log().all();

    }
}
