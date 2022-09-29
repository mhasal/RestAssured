import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test(){

            given()
                        // hazırlık işlemlerini yapacağız. (token, send body, parametreler)
                .when()
                       // link i ve metodu veriyoruz
                .then();
                      // assertion ve verileri ele alma (extract)

    }

    @Test
    public void statusCodeTest(){

            given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log.All() bütün response u gösterir
                .statusCode(200) // status kontrolü
                .contentType(ContentType.JSON)
                ;
    }

    @Test
    public void checkStateInResponseBody(){
            given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log()
                .body()
                .body("country", equalTo("United States")) // body.country == United States ?
                .statusCode(200)
        ;
    }

    // body.country -> body("country",
    // body.'post code' -> body("post code",
    // body.'country abbreviation' -> body("country abbreviation",
    // body.places[0].'place name' -> body("body.places[0].'place name'"
    // body.places[0].state

    @Test
    public void bodyJsonPathTest2(){
            given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log()
                .body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyArrayHasSizeTest(){
            given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log()
                .body()
                .body("places", hasSize(1))
                .statusCode(200)
        ;
    }

    @Test
    public void combiningTest(){
            given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log()
                .body()
                .body("places", hasSize(1))
                .body("places.state" , hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest(){
            given()
                    .pathParam("Country" , "us")
                    .pathParam("ZipCode" , 90210)

                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipCode}")

                .then()
                .log().body()

                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest2(){
        // 90210 dan 90213 kadar test sonuçlarında places in size nın hepsinde 1 gediğini test ediniz.

        for (int i= 90210; i<=90213; i++){
        given()
                .pathParam("Country" , "us")
                .pathParam("ZipCode" , i)
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipCode}")
                .then()
                .log().body()
                .body("places",hasSize(1))
                .statusCode(200)
        ;
    }
    }

    @Test
    public void queryParamTest(){

        given()
                .param("page" , 1)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page" , equalTo(1))
                .statusCode(200)
        ;
    }

    @Test
    public void queryParamTest2(){

        for (int i=1; i<=10; i++)
        given()
                .param("page" , i)
                .log().uri() // request linki

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page" , equalTo(i))
                .statusCode(200)
        ;
    }

    RequestSpecification requestSpecs;
    ResponseSpecification responseSpecs;

    @BeforeClass
    void Setup(){

        baseURI = "https://gorest.co.in/public/v1";

        requestSpecs = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecs = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();


    }

    @Test
    public void requestResponseSpecification(){

        given()
                .param("page" , 1)
                .spec(requestSpecs)

                .when()
                .get("/users") // url nin başında http yoksa baseUri deki değer otomatik geliyor

                .then()
                .log().body()
                .body("meta.pagination.page" , equalTo(1))
                .spec(responseSpecs)
        ;
    }

    @Test
    public void extractingJsonPath(){

        String placeName=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .extract().path("places[0].'place name'")
            // extract metodu ile given ile başlayan satır, bir değer döndürür hale geldi, en sonda extract olmalı
        ;

        System.out.println("placeName = " + placeName);

    }

}












