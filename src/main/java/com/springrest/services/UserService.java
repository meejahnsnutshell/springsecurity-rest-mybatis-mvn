package com.springrest.services;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.mappers.UserMapper;
import com.springrest.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;


    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    //get all users using com
    public ArrayList<User> getAllUsers (){
        return userMapper.getAllUsers();
    }

    //original - get user by id
//    public user getById(int id){
//        return userMapper.getByID(id);
//    }

    // new get user by id with exception handling if id doesn't exist
    public User getById(int id) throws InvalidRequestException {
        try {
            int tempId = userMapper.checkIfIdExists(id);
        } catch (Exception e) {
            throw new InvalidRequestException("Unknown id: " + id, 400);
        }
        return userMapper.getByID(id);
    }

    //get all users manually without using com
    public ArrayList<User> getAllUsersManually() {

        //do everything necessary to get resutls from DB and turn them into objects
        //in arraylist and return to controller

        ArrayList<User> users = null;
        try {

            users = new ArrayList<>();

            System.out.println("creating connection");

            // Load the MySQL driver (updated to new driver class)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Setup the connection with the DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost/com-test?" +
                    "user=root&password=CodingNomadsFoEva!&useSSL=false");

            System.out.println("connection succeeded");


            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();

            // execute query and assign it to resulSet
            resultSet = statement.executeQuery("select * from users");

            // iterates through the result set
            while (resultSet.next()) {

                User u = new User();

                // set the instance vars of the user pojo to the resultSet values
                u.setId(resultSet.getInt("id"));
                u.setFirst_name(resultSet.getString("first_name"));
                u.setLast_name(resultSet.getString("last_name"));
                u.setIsActive(resultSet.getInt("isActive"));

                // adding the newly set pojo to the ArrayList
                users.add(u);
            }


        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return users;
    }

    //original add new user
//    public user addNew(user user) {
//        userMapper.insertUser(user);
//        return userMapper.getByName(user.getFirst_name());
//    }

//    public user getById(int id) throws InvalidRequestException {
//        try {
//            int tempId = userMapper.checkIfIdExists(id);
//        } catch (Exception e) {
//            throw new InvalidRequestException("Unknown id: " + id, 400);
//        }
//        return userMapper.getByID(id);
//    }


    // modified add user method with exception handling
    public User addNew(User user) throws InvalidRequestException {
        try {
            int tempId = userMapper.insertUser(user);
        } catch (Exception e) {
            throw new InvalidRequestException("Failed to add user: " + user, 400);
        }
        return userMapper.getByName(user.getFirst_name());
    }

    //original updateById
//    public user updateById(user user) {
//        userMapper.updateUser(user);
//        return userMapper.getByName(user.getFirst_name());
//    }

    // updated updateById with exception handling
    public User updateById(User user) throws InvalidRequestException {
        try {
            userMapper.updateUser(user);
            return userMapper.getByName(user.getFirst_name());
        } catch (Exception e){
            throw new InvalidRequestException("Failed to update id "+ user.getId(), 400);
        }
    }

    //delete
    public User deleteById(int id) {
        userMapper.deleteUser(id);
        return userMapper.getByID(id);
    }
}
