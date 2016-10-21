package com.game.ws.game;

import com.game.ws.game.service.GameCalculationService;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Created by abhishekrai on 21/10/2016.
 */
public class GameConnectClient {
    @Test
    private static void testGet(String url){
        try {
            Client client = ClientBuilder.newClient();
            WebTarget webResource = client
                    .target(url);
            Response response = webResource.request().get();

            if (response.getStatus() != 200 && response.getStatus()!= 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("Output from Server .... \n");
            String output = (String)response.getEntity();
            System.out.println(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("asd");
        testGet("https://jsonplaceholder.typicode.com'");
    }
}
