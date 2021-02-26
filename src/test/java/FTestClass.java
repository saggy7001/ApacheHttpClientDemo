import APIs.PetApi;
import Clients.ClientHelper;
import Clients.PetClient;
import POJOs.Category;
import POJOs.Pet;
import POJOs.Tag;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class FTestClass
{
    CloseableHttpClient client = PetClient.GetRestClient();

    //POST
    @Test
    public void createPet() throws Exception
    {
        int petId =123;
        //String json = "{\"id\":"+petId+",\"category\":{\"id\":0,\"name\":\"string\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";

        Pet newPet = new Pet(petId, new Category(0,"string"), "doggie", new ArrayList<String>(){}, Arrays.asList(new Tag(0, "string")), "available");

        //HttpPost request = ClientHelper.FormattedPutRequest(PetApi.PET, new Gson().toJson(newPet));

        HttpPost request = ClientHelper.FormattedPutRequest(PetApi.PET, ClientHelper.ToJson(newPet));

        CloseableHttpResponse response =  client.execute(request);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Request failed with status" );

        //String jsonStr = ClientHelper.GetJsonStringFromResponse(response);
        //Pet pet = new Gson().fromJson(jsonStr, Pet.class);
        Pet pet = ClientHelper.GetResponseObject(response, Pet.class);
        Assert.assertEquals(pet.name, "doggie");

    }

    //Get
    @Test
    public void getPet() throws Exception {
        Integer petId = 123;

        HttpGet getRequest = ClientHelper.FormattedGetRequest(PetApi.GETPET(petId));
        CloseableHttpResponse response = client.execute(getRequest);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Request failed with status" );

        String jsonStr = ClientHelper.GetJsonStringFromResponse(response);
        Pet pet = new Gson().fromJson(jsonStr, Pet.class);

        Assert.assertEquals(pet.id, petId);
    }
}
