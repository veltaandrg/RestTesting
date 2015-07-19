package restdemo;

import java.util.*;
import java.io.*;
import org.junit.*;
import org.json.*;
import com.mashape.unirest.http.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public class AppTest {
    
    String siteUrl = "http://vfrg.pythonanywhere.com";
    String customersUrl = "/customers/";
    static final String XML_PATH = "https://gist.githubusercontent.com/RodionGork/8064ab8a648f4be6ff13/raw/2482569baedc06f0d36c2fb54738e66453c3f091/pom.xml";
    
    DocumentBuilder db;
    XPath xPath;
    
    public AppTest() {
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xPath = XPathFactory.newInstance().newXPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void testField(Document doc, String path, String value) {
        try {
            Assert.assertEquals(value, xPath.evaluate(path, doc.getDocumentElement()));
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void testGetXml() throws Exception {
        HttpResponse<String> response = Unirest.get(XML_PATH).asString();
        Assert.assertEquals(200, response.getStatus());
        Document doc = db.parse(new ByteArrayInputStream(response.getBody().getBytes("utf-8")));
        testField(doc, "/project/properties/maven.compiler.target", "1.7");
    }

    @Test
    public void testGetCustomers() throws Exception {
        HttpResponse<String> response = Unirest.get(siteUrl + customersUrl).asString();
        Assert.assertEquals(200, response.getStatus());
        
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
