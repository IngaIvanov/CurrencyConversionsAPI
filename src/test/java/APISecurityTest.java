import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APISecurityTest {


    @Test
    public void unauthorizedUserCurrentConversionErrorMessageTest(){
        Response response = given().get(Consts.URL+Consts.API_CURRENT_CONVERSION_RATE);
        int statusCode = response.getStatusCode();
        String errorMessage = response.jsonPath().getString("message");
        assertEquals(statusCode, 401);
        System.out.println(response.print());
        assertEquals(errorMessage, "Invalid authentication credentials");
    }

    @Test
    public void unauthorizedUserHistoricalConversionErrorMessageTest(){
        Response response = given().get(Consts.URL+Consts.API_HISTORICAL_CONVERSION_RATE);
        int statusCode = response.getStatusCode();
        String errorMessage = response.jsonPath().getString("message");
        assertEquals(statusCode, 401);
        System.out.println(response.print());
        assertEquals(errorMessage, "Invalid authentication credentials");
    }
}
