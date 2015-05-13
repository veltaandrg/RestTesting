package restdemo;

import java.util.*;
import org.junit.*;
import org.json.*;
import com.mashape.unirest.http.*;

public class AppTest {
    
    String siteUrl = "http://vfrg.pythonanywhere.com";
    String customersUrl = "/customers/";
    
    @Test
    public void testGetCustomers() throws Exception {
        HttpResponse<String> response = Unirest.get(siteUrl + customersUrl).asString();
        Assert.assertEquals(200, response.getStatus());
        System.out.println(response.getBody());
    }

    @Test
    public void testPostCustomers() throws Exception {
        // write something to check that 405 METHOD NOT SUPPORTED is returned
        // for post request to the same URL
    }

    @Test
    public void testPutDeleteCustomers() throws Exception {
        // create test for adding a customer and then deleting it
    }

    @Test
    public void testBasket() throws Exception {
        Random rnd = new Random();
        JSONArray customers = Unirest.get(siteUrl + customersUrl).asJson().getBody().getObject().getJSONArray("data");
        JSONArray goods = Unirest.get(siteUrl + "/goods/").asJson().getBody().getObject().getJSONArray("data");
        int quantity = rnd.nextInt(5) + 1;
        int customerId = customers.getJSONObject(rnd.nextInt(customers.length())).getInt("id");
        int productId = goods.getJSONObject(rnd.nextInt(goods.length())).getInt("id");
        HttpResponse<JsonNode> result = Unirest.post(siteUrl + "/baskets/" + customerId)
                .body("{\"productid\":" + productId + ",\"quantity\":" + quantity + "}").asJson();
        Assert.assertEquals(200, result.getStatus());
        System.out.println(String.format("Put to Basket: %d items of Product #%d for Customer #%d", quantity, productId, customerId));
    }

}
