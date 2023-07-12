import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class APICurrencyEndpointTest {

    private static Response response;

    @BeforeAll
    public static void setup (){
        response = given().get(Consts.API_CURRENT_CONVERSION_RATE+Consts.KEY);
    }

    @Test
    public void getAllCurrenciesResponseTest(){
        System.out.println(response.asString());
        response.then().statusCode(200);
    }

    @Test
    public void getCurrentCurrencyConversionRates() {
        JsonPath jsonPath = response.jsonPath();

        boolean success = response.jsonPath().getBoolean("success");
        assertTrue(success);

        String sourceCurrency = response.jsonPath().getString("source");
        assertEquals(sourceCurrency, "USD");

        double usdToCadRate = response.jsonPath().getDouble("quotes.USDCAD");
        assertNotEquals(usdToCadRate, 0.0);
        System.out.println(usdToCadRate);

        double usdToEurRate = response.jsonPath().getDouble("quotes.USDEUR");
        assertNotEquals(usdToEurRate, 0.0);
        System.out.println(usdToEurRate);

        double usdToRubRate = response.jsonPath().getDouble("quotes.USDRUB");
        assertNotEquals(usdToRubRate, 0.0);
        System.out.println(usdToRubRate);

        Assertions.assertNotNull(jsonPath.getString("timestamp"));
        Assertions.assertNotNull(jsonPath.getString("quotes"));
        Assertions.assertEquals(jsonPath.getString("source"), "USD");
    }


    @Test
    public void getCurrentCurrenciesConversionRates() {
        String[] currencies = {"USDCAD", "USDEUR", "USDRUB"};

        JsonPath jsonPath = response.jsonPath();

        for (String currency : currencies) {
            double rate = getCurrencyRate(jsonPath, currency);
            assertNotEquals(rate, 0.0);

            System.out.println(currency + " rate: " + rate);
        }
    }

    private double getCurrencyRate(JsonPath jsonPath, String currency) {
        return jsonPath.getDouble("quotes." + currency);
    }
}
