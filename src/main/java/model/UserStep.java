package model;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static utils.Constants.*;

public class UserStep {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(POST_REGISTER)
                .then().statusCode(SC_OK);
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(User user) {
        System.out.println("Токен для удаления: " + user.getAccessToken()); // для проверки
        return given()
                .spec(getSpec("Bearer " + user.getAccessToken()))
                .log().all() // логируем запрос
                .when()
                .delete(DELETE_USER)
                .then()
                .log().all(); // логируем ответ
    }

    // Дополнительный метод для извлечения токена из ответа
    public String extractAccessToken(ValidatableResponse response) {
        return response.extract().body().jsonPath().getString("accessToken");
    }

    protected static RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    protected static RequestSpecification getSpec(String bearerToken) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", bearerToken)
                .setBaseUri(BASE_URL)
                .build();
    }

}
