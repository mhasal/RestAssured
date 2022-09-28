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
                ;
    }

}
