package com.sda.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.model.Currency;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.IOException;



public class HttpClientSample {

    public static void main(String[] args) throws IOException {
        String url = "http://api.nbp.pl/api/exchangerates/rates/a/usd/2017-01-24/2017-02-05";

        HttpClient client = HttpClientBuilder.create().build();


        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        String content = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
        Currency currency = objectMapper.readValue(content,Currency.class);
        System.out.println(currency);


    }

}
