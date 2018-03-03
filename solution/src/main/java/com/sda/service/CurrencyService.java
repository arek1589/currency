package com.sda.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.model.Currency;
import com.sda.model.Rate;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public final class CurrencyService {

    private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/a";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public CurrencyService() {
        //stworzenie http client
        client = HttpClientBuilder.create().build();
        //stworenie mappera - bedzie zamieniac json <-> obiekt
        mapper = new ObjectMapper();
    }

    public BigDecimal getAverage(String currencyCode, String fromDate, String toDate){

        //adres url z parametrami
        String callUrl = NBP_URL +"/"+ currencyCode +"/"+fromDate+"/"+toDate;
        Currency currency = null;
        //Stworzenie zapytania get
        HttpGet request = new HttpGet(callUrl);
        try {
            //wykonania - http wola  nbp
            HttpResponse response = client.execute(request);
            //zamiana InputStream na String. W content json z odpowiedzia
            String content = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            //Zamiana json na obiekt Currency
            currency = mapper.readValue(content, Currency.class);
        } catch (IOException e) {
            //todo: sytuacja wyjatkowa
        }
        BigDecimal average = getCurrencyAverage(currency);
        return average;
    }



    private BigDecimal getCurrencyAverage(Currency currency){
        List<Rate> rates = currency.getRates();
        BigDecimal sum = new BigDecimal(0);
        for(Rate rate:rates){
            BigDecimal mid = rate.getMid();
            sum = sum.add(mid);
        }
        BigDecimal numberElement = new BigDecimal(rates.size());
        MathContext mathContext = new MathContext(3);
        return sum.divide(numberElement,mathContext);
    }

}
