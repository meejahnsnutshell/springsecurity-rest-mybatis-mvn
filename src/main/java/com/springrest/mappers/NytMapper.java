package com.springrest.mappers;

import com.springrest.model.nyt.Multimedia;
import com.springrest.model.nyt.NytTopArticle;
import com.springrest.model.nyt.Results;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;

@Mapper
public interface NytMapper {

    // a query to get the id of stories & to check if top story already exists in our db.
    String GET_STORY_ID = "select id from `mybatis-test`.nyt_top_stories where url = #{url}";
    // a query to insert a new top story, must list all column headings because we are not using them
    // all. Multimedia is treated separately (see below).
    String GET_BY_ID = "SELECT * FROM `mybatis-test`.nyt_top_stories where id = #{id}";
    String INSERT_NEW_TOP_STORY = "insert into `mybatis-test`.nyt_top_stories (section, subsection, title," +
            "url, byline, item_type, updated_date, created_date, published_date, " +
            "material_type_facet, kicker, short_url) VALUES (#{section}, #{subsection}, #{title}," +
            "#{url}, #{byline}, #{item_type}, #{updated_date}, #{created_date}, " +
            "#{published_date}, #{material_type_facet}, #{kicker}, #{short_url})";
    // query to insert into multimedia
    String INSERT_MULTIMEDIA = "INSERT INTO `mybatis-test`.multimedia (url, format, height," +
            "width, type, subtype, caption, copyright, top_story_id) values (#{url}, #{format}," +
            "#{height}, #{width}, #{type}, #{subtype}, #{caption}, #{copyright}, #{top_story_id})";
    String SELECT_ALL_TOP_STORIES = "Select * from `mybatis-test`.nyt_top_stories";
    String SELECT_WITH_SEARCH_PARAM = "Select * from `mybatis-test`.nyt_top_stories where title like #{query}";
    String SELECT_STORIES_BY_SECTION = "Select * from `mybatis-test`.nyt_top_stories where " +
            "section = #{section} order by updated_date desc limit 10";
    String CHECK_SECTION_EXISTS = "Select id from `mybatis-test`.nyt_top_stories where section = #{section} limit 1";
    String DELETE_BY_ID = "UPDATE `mybatis-test`.nyt_top_stories set isActive = 0 WHERE id = #{id}";

    // methods utilizing the query searches above, to get and insert
    @Select(GET_STORY_ID)
    public int getStoryId(String url);

    @Select(GET_BY_ID)
    public int getById(int id);

    @Select(SELECT_ALL_TOP_STORIES)
    public ArrayList<Results> getAllTopStories();

    @Select(SELECT_WITH_SEARCH_PARAM)
    public ArrayList<Results> searchTopStories(String query);

    @Select(SELECT_STORIES_BY_SECTION)
    public ArrayList<Results> searchBySection(String section);

    @Select(CHECK_SECTION_EXISTS)
    public int checkIfSectionExists(String section);

    // the int returned is the number of rows affected
    @Insert(INSERT_NEW_TOP_STORY)
    public int insertNewTopStory(Results results);

    @Insert(INSERT_MULTIMEDIA)
    public int insertMultimedia(Multimedia multimedia);

    @Delete(DELETE_BY_ID)
    public int deleteById(int id);

}
