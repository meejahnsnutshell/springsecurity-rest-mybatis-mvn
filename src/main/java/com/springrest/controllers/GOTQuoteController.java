package com.springrest.controllers;

import com.springrest.model.gotquotes.GOTQuote;
import com.springrest.services.GOTQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/got")
public class GOTQuoteController {

    @Autowired
    GOTQuotesService service;

    // this method reads quotes from the API
    @RequestMapping("/get")
    public GOTQuote getQuote(){
        return service.getQuote();
    }
}
