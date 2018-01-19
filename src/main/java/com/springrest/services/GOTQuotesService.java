package com.springrest.services;

import com.springrest.mappers.GOTQuoteMapper;
import com.springrest.model.gotquotes.GOTQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GOTQuotesService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GOTQuoteMapper mapper;

    public GOTQuote getQuote(){

        GOTQuote quote = null; // initialized to make intelliJ happy.

        // do this 10 times to fill db with some quotes (api returns one at a time)
        for (int i = 0; i < 10; i++) {
            // call the api and map the return to the GOTQuote class
            quote = restTemplate.getForObject("https://got-quotes.herokuapp.com/quotes",
                    GOTQuote.class);
            // insert the response to the DB
            insertQuote(quote);
        }
        return quote;
    }

    private void insertQuote(GOTQuote quote) {

        int id;

        try {
            // tries to getQuoteId. If it doesn't have one (ie doesn't yet exist in db, set it to zero.
            // Note to self: The comparison of quotes happens in the GET_QUOTE_ID SQL query in the Mapper
            id = mapper.getQuoteId(quote.getQuote());
        } catch (Exception e){
            id = 0;
        }

        // if new quote
        if (id == 0){
            // call insertNewQuote and pass it quote to store in db,
            mapper.insertNewQuote(quote);
        }
    }
}
