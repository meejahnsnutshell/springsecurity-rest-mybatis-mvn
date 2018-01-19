package com.springrest.controllers;

import java.util.ArrayList;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.model.CustomResponseObject;
import com.springrest.model.user.User;
import com.springrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //Get, the only method that doesn't have a body
    @RequestMapping(method = RequestMethod.GET, value ="/")
    public ArrayList<User> getUsers() {
        return userService.getAllUsers();
    }

    // Original getById method
//    @RequestMapping(method = RequestMethod.GET, value ="/{id}")
//    public user getById(@PathVariable(value="id")int id) {
//        return userService.getById(id);
//    }

    //getById with exception handling, returns error if requested id is not found.
    @RequestMapping(method = RequestMethod.GET, value ="/{id}")
    public ResponseEntity<CustomResponseObject> getById(@PathVariable(value="id")int id)
            throws InvalidRequestException {

        User user = null;
        CustomResponseObject cro = new CustomResponseObject();

        try {
            user = userService.getById(id);
            cro.setMessage("success");
            cro.setStatus_code(200);
            cro.setData(user);
            return new ResponseEntity(cro, HttpStatus.OK);
        } catch (InvalidRequestException down) {
            throw down;
        }
    }


    @RequestMapping(method = RequestMethod.GET, value ="/manual")
    public ArrayList<User> getUsersManually() {
        return userService.getAllUsersManually();
    }

    //Original Post method (to create a new user)
//    @RequestMapping(method = RequestMethod.POST, value = "/")
//    public user addNew(@RequestBody user user) {
//        return userService.addNew(user);
//    }

    // Edited post method to include exception handling
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<CustomResponseObject> addNew(@RequestBody User user)
            throws InvalidRequestException {

        CustomResponseObject cro = new CustomResponseObject();

        try {
            user = userService.addNew(user);
            cro.setMessage("Success!");
            cro.setData(user);
            cro.setStatus_code(200);
            return new ResponseEntity(cro, HttpStatus.OK);
        } catch (InvalidRequestException ire){
            throw ire;
        }
    }

    // Original patch method
//    @RequestMapping(method = RequestMethod.PATCH, value = "/")
//    public user updateById(@RequestBody user user) {
//        return userService.updateById(user);
//    }

    // Updated patch method with exception handling
    @RequestMapping(method = RequestMethod.PATCH, value = "/")
    public ResponseEntity<CustomResponseObject> updateById(@RequestBody User user)
            throws InvalidRequestException {

        CustomResponseObject cro = new CustomResponseObject();

        try {
            user = userService.updateById(user);
            cro.setMessage("successfully patched user " + user.getId());
            cro.setStatus_code(200);
            cro.setData(user);
            return new ResponseEntity(cro, HttpStatus.OK);
        } catch (InvalidRequestException ire){
            throw ire;
        }
    }


    //Delete
    @RequestMapping(method= RequestMethod.DELETE, value="/")
    public User deleteById(@RequestBody User user){
        return userService.deleteById(user.getId());
    }
}
