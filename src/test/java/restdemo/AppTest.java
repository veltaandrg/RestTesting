package restdemo;

import java.util.*;
import org.junit.*;
import com.mashape.unirest.http.*;

public class AppTest {
    
    String siteUrl = "http://vfrg.pythonanywhere.com";
    String customersUrl = "/customers/";
    
    @Test
    public void testGetCustomers() throws Exception {
        HttpResponse<String> response = Unirest.get(siteUrl + customersUrl).asString();
        //System.out.println(Arrays.toString(HttpResponse.class.getMethods()));
        Assert.assertEquals(200, response.getStatus());
        System.out.println(response.getBody());
    }

    @Test
    public void testPostCustomers() throws Exception {
        HttpResponse<String> response = Unirest.post(siteUrl + customersUrl)
                .body("{}").asString();
        Assert.assertEquals(405, response.getStatus());
    }

    @Test
    public void testPutDeleteCustomers() throws Exception {
        HttpResponse<JsonNode> response = Unirest.put(siteUrl + customersUrl)
                .body("{\"username\":\"zlopotam\",\"fullname\":\"Zlop Otam\"}").asJson();
        Assert.assertEquals(200, response.getStatus());
        int id = response.getBody().getObject().getInt("id");
        HttpResponse<String> response2 = Unirest.delete(String.format("%s/%s/%d/zlopotam",
                siteUrl, customersUrl, id)).body("").asString();
        Assert.assertEquals(200, response.getStatus());
    }
}
