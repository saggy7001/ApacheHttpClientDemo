import Clients.PetClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class SampleTest {
    //create client
    CloseableHttpClient httpClient = HttpClients.createDefault();
    int petId = 987;

    @BeforeTest
    public void setup() throws Exception
    {

        HttpPost request = new HttpPost("https://petstore.swagger.io/v2/pet");
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        String json = "{\"id\":"+petId+",\"category\":{\"id\":0,\"name\":\"string\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);

        httpClient.execute(request);
    }

    @Test
    public void GetPetTest () throws IOException {
        //create request
        HttpGet request = new HttpGet("https://petstore.swagger.io/v2/pet/"+petId);
        request.addHeader(HttpHeaders.ACCEPT, "*/*");

        //execute request using client
        CloseableHttpResponse response =  httpClient.execute(request);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Request failed with status" );

        //decode/decorate to json
        String respStr = EntityUtils.toString(response.getEntity());
        String petName = new Gson().fromJson(respStr, JsonObject.class).get("name").getAsString();

        //assert
        Assert.assertEquals(petName, "doggie");
    }


}


