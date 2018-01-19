package com.springrest.controllers;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.model.CustomResponseObject;
import com.springrest.model.nyt.NytTopArticle;
import com.springrest.model.nyt.Results;
import com.springrest.services.NytService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/nyt")
public class NytController {
// Controller methods contain http mapping and throw custom exceptions

    @Autowired
    NytService nytservice;

    // method to load all articles from the API
    @RequestMapping(method = RequestMethod.GET, value = "/topstories/load")
    public NytTopArticle loadTopStories(){
        return nytservice.loadTopStories();
    }

    // search top stories by section
    @RequestMapping("/topstories/{section:.+}")
    public ResponseEntity<CustomResponseObject> topStoriesBySection(@PathVariable("section") String section)
            throws InvalidRequestException {

        ArrayList<Results> nyt = null;
        CustomResponseObject response = new CustomResponseObject();

        try {
            nyt = nytservice.topStoriesBySection(section);
            response.setMessage("success");
            response.setData(nyt);
            response.setStatus_code(200);
            return new ResponseEntity(nyt, HttpStatus.OK);
        } catch (InvalidRequestException down) {
            throw down;
        }
    }

    // method to search stories
    @RequestMapping("/topstories")
    public ArrayList<Results> getTopStories(@RequestParam(value = "query", required = false, defaultValue = "null") String query){
        return nytservice.getTopStories(query);
    }

    //Delete
    @RequestMapping(method= RequestMethod.DELETE, value="/delete")
    public int deleteById(@RequestBody Results result){
        return nytservice.deleteById(result.getId());
    }

}
