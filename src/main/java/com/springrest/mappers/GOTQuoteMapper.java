package com.springrest.mappers;

import com.springrest.model.gotquotes.GOTQuote;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
// need to generate a random key
// need a generate method, user enters their name/email or something and is given a key
// then we check to see if key exists, if so user already exists, if not give it to them.
// can do further to allow only 500 hits per day or something, so would need a count that tracks
// how many times a specific key is used, once 500 is reached access becomes false.

@Mapper
public interface GOTQuoteMapper {

    // Use to check if quote is already in DB.
    String GET_QUOTE_ID = "select id from `mybatis-test`.gotquotes where quote = #{quote}";

    // Use to save new quotes to DB
    String INSERT_NEW_QUOTE = "insert into `mybatis-test`.gotquotes (quote, characterName) " +
            "VALUES (#{quote}, #{character})";

    @Select(GET_QUOTE_ID)
    public int getQuoteId(String quote);

    @Insert(INSERT_NEW_QUOTE)
    public int insertNewQuote(GOTQuote quote);

}
