package Clients;

import APIs.PetApi;
import Config.ConfigManager;
import POJOs.Pet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ClientHelper {

    public static HttpPost FormattedPutRequest(String url, String body)
    {
        //url and header
        HttpPost request = new HttpPost(ConfigManager.BaseURL + url);
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        //add body
        try {
            StringEntity entity = new StringEntity(body);
            request.setEntity(entity);
        }
        catch (UnsupportedEncodingException exps)
        {
            System.err.println(exps.getStackTrace());
        }

        return request;
    }

    public static HttpGet FormattedGetRequest(String url)
    {
        HttpGet httpGet = new HttpGet(ConfigManager.BaseURL + url);
        httpGet.addHeader(HttpHeaders.ACCEPT, "*/*");

        return httpGet;
    }

    public static String GetJsonStringFromResponse(CloseableHttpResponse response)
    {
        String respStr = "";
        try{
            respStr = EntityUtils.toString(response.getEntity());
        }
        catch (IOException | ParseException ioex){
            System.err.println(Arrays.toString(ioex.getStackTrace()));
        }

        return respStr;
    }

    public static <T> T GetResponseObject(CloseableHttpResponse response, Class<T> type)
    {
        String respStr = "";
        try{
            respStr = EntityUtils.toString(response.getEntity());
        }
        catch (IOException | ParseException ioex){
            System.err.println(Arrays.toString(ioex.getStackTrace()));
        }
        return new Gson().fromJson(respStr, type);
    }

    public static String ToJson(Object tClass)
    {
        return new Gson().toJson(tClass);
    }
}
