package APITest;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import APITest.Utils.tokenManager;

public class listUsers {

    private String baseUri = "https://reqres.in";

    @Test(groups = "listUsers")
    @Description("Listar usuarios")
    @Owner("Tomas Zarate")
    @Feature("endpoint /api/users")
    public void verificarStatusCode200() {
        given()
            .baseUri(baseUri)
            .header("Authorization", "Bearer " + tokenManager.getToken())
            .header("Content-Type", "application/json")
            .when()
            .get("/api/users")
            .then()
            .statusCode(200)
            .log().all();
    }

    @Test(groups = "listUsers")
    @Description("Verificar los datos extraidos del body de la respuesta")
    @Owner("Tomas Zarate")
    @Feature("endpoint /api/users")
    public void verificarCamposJson() {
        Response response = given()
            .baseUri(baseUri)
            .header("Authorization", "Bearer " + tokenManager.getToken())
            .when()
            .get("/api/users")
            .then()
            .statusCode(200)
            .extract()
            .response();

            //Extrar datos de la respuesta
            String id = response.jsonPath().getString("data[0].id");
            String email = response.jsonPath().getString("data[0].email");
            String first_name = response.jsonPath().getString("data[0].first_name");
            String last_name = response.jsonPath().getString("data[0].last_name");
            String avatar = response.jsonPath().getString("data[0].avatar");
            // Imprimir los datos extraidos
            System.out.println("id: " + id);
            System.out.println("email: " + email);
            System.out.println("first_name: " + first_name);
            System.out.println("last_name: " + last_name);
            System.out.println("avatar: " + avatar);
            // Verificar los datos extraidos
            response.then().body("data[0].id", equalTo(1));
            response.then().body("data[0].email", equalTo("george.bluth@reqres.in"));
            response.then().body("data[0].first_name", equalTo("George"));
            response.then().body("data[0].last_name", equalTo("Bluth"));
            response.then().body("data[0].avatar", equalTo("https://reqres.in/img/faces/1-image.jpg"));      
        }

    @Test(groups = "listUsers")
    @Description("Verificar el schema del json")
    @Owner("Tomas Zarate")
    @Feature("endpoint /api/users")
    public void verificarSchemaJson(){
       given()  
                .baseUri(baseUri)
                .header("Authorization", "Bearer " + tokenManager.getToken())
                .header("Content-Type", "application/json")
                .when()
                .get("api/users")
                .then()
                .assertThat()
                .statusCode(200)
               .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/users-schemas.json"));
        } 

    @Test(groups = "listUsers")
    @Description("Verificar la creacion de un usuario")
    @Owner("Tomas Zarate")
    @Feature("endpoint /api/users")
    public void crearUsuario(){
        String body = String.format("""
        {
            "name": "Tomas",
            "job": "QA Automation"
        }""" );

        given()
                .baseUri(baseUri)
                .header("Authorization", "Bearer " + tokenManager.getToken())
                .contentType("application/json")
                .body(body)
                .when()
                .post("api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Tomas"))
                .body("job", equalTo("QA Automation"))
                .log().all();
    }

    @Test(groups = "listUsers")
    @Description("Verificar la actualizacion de un usuario")
    @Owner("Tomas Zarate")
    @Feature("endpoint /api/users")
    public void actualizarUsuario(){
        String body = String.format("""
        {
            "name": "Emiliano",
            "job": "Analista Funcional"
        }""" );

        given()
                .baseUri(baseUri)
                .header("Authorization", "Bearer " + tokenManager.getToken())
                .contentType("application/json")
                .body(body)
                .when()
                .put("api/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("Emiliano"))
                .body("job", equalTo("Analista Funcional"))
                .log().all();
    }

    @Test(groups = "listUsers")
    @Description("Verificar la eliminacion de un usuario")
    @Owner("Tomas Zarate")
    @Feature("endpoint /api/users")
    public void eliminarUsuario(){
        given()
                .baseUri(baseUri)
                .header("Authorization", "Bearer " + tokenManager.getToken())
                .contentType("application/json")
                .when()
                .delete("api/users/2")
                .then()
                .statusCode(204)
                .log().all();
    }
}





    

