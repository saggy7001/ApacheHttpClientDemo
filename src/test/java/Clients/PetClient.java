package Clients;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PetClient {

    //CloseableHttpClient httpClient = HttpClients.createDefault();

    private static CloseableHttpClient httpClient = null;
    private static PetClient petClient = null;

    private PetClient()
    {
    }

    private static PetClient GetInstance()
    {
        if (petClient == null)
        {
            petClient = new PetClient();
            httpClient = HttpClients.createDefault();
        }
        return petClient;
    }

    public static CloseableHttpClient GetRestClient()
    {
        if (httpClient == null) {
            GetInstance();
        }
        return httpClient;
    }
}
