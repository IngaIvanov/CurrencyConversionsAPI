import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import static io.restassured.RestAssured.given;


public class APIHistoricalConversionTest {

    private static Response response;
    private static final String[] CURRENCIES = {"USDCAD", "USDEUR", "USDRUB"};

    @BeforeAll
    public static void setup (){
        response = given().get(Consts.API_HISTORICAL_CONVERSION_RATE+Consts.KEY);
    }


    @Test
    public void timeStampTests() {
        Response response = given().get(Consts.URL + Consts.KEY);
        System.out.println(response.asString());
        String expected = LocalDate.now().minusDays(0).toString();
        Integer actualMs = response.path("timestamp");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date((long) actualMs * 1000);
        String actual = format.format(date2.getTime());
        Assertions.assertEquals(expected, actual);
        for (String currency : CURRENCIES) {
            Assertions.assertTrue(response.jsonPath().getMap("quotes").containsKey(currency));
        }
    }
}


