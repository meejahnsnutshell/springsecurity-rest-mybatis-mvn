package com.springrest.services;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.mappers.NytMapper;
import com.springrest.model.nyt.Multimedia;
import com.springrest.model.nyt.NytTopArticle;
import com.springrest.model.nyt.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class NytService {
// This is where the API is accessed from the web. We're not doing anything yet.
// Service methods hold all the logic and should return items from the api in pure form.
// Service methods should be able to be used
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    NytMapper mapper;

    // method that loads from the API
    // do this again for HW.
    public NytTopArticle loadTopStories(){

        NytTopArticle top = restTemplate.getForObject(
                "https://api.nytimes.com/svc/topstories/v2/home.json?apikey=" +
                        "f6d1ef6d53484163b2f9d800cea06ca5", NytTopArticle.class);
        insertTopStories(top);

        return top;
    }

    public void insertTopStories(NytTopArticle articles){

        // for every result (story) in the NYT response
        for (Results result : articles.getResults()){

            int id = 0;
            // check to see if the story already exists in our db
            try {
                id = mapper.getStoryId(result.getUrl());
            } catch (Exception e){
                id = 0;
            }

            // if id is 0, that means it doesn't exist in our db - we can insert it
            if (id == 0){

                // insert story (success will be number of rows/stories inserted)
                int success = mapper.insertNewTopStory(result);

                // insertTopStory returns the # of rows inserted - if that number is > 0
                // that means the story was inserted - now we can insert the associated
                // multimedia
                if (success > 0){

                    // get the id of the newly inserted story to use for each multimedia
                    // record
                    id = mapper.getStoryId(result.getUrl());

                    // for every multimedia record in the story
                    for (Multimedia multimedia : result.getMultimedia()){

                        //set the story_id on the multimedia object (associate it with a
                        //story in our DB
                        multimedia.setTop_story_id(id);

                        //insert multimedia
                        mapper.insertMultimedia(multimedia);
                    }
                }
            }
        }
    }

    public ArrayList<Results> getTopStories(String query) {

        if (!query.equalsIgnoreCase("null")){
            return mapper.searchTopStories("%" + query + "%");
        } else {
            return mapper.getAllTopStories();
        }
    }

    public ArrayList<Results> topStoriesBySection(String section) throws InvalidRequestException {

        try {
            int tempId = mapper.checkIfSectionExists(section);
        } catch (Exception npe){
            throw new InvalidRequestException("Unknown section: " + section, 400);
        }
        return mapper.searchBySection(section);
    }

    //delete
    public int deleteById(int id) {
        mapper.deleteById(id);
        return mapper.getById(id);
    }
}
