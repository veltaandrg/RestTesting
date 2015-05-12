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
}
