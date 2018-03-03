package com.sda.controller;


import java.math.BigDecimal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.sda.service.CurrencyService;

@Path("/v1/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController() {
        currencyService = new CurrencyService();
    }

    @GET
    @Path("{code}/{from}/{to}")
    public String getAverage(@PathParam("code") String code,
                             @PathParam("from") String from,
                             @PathParam("to")String to){
        BigDecimal average = currencyService.getAverage(code, from, to);
        return average.toString();
    }

}

