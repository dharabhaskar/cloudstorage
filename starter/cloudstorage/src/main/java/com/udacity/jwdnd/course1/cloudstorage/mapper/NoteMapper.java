package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    //Get all notes of a specific user.
    @Select("select * from NOTES where userid=#{userid}")
    @Results({
            @Result(property = "title", column = "notetitle"),
            @Result(property = "description", column = "notedescription")
    })
    public List<Note> getNotesByUser(int userid);

    //Insert note
    @Insert("insert into NOTES(notetitle,notedescription,userid) values(#{title},#{description},#{userid})")
    @Options(useGeneratedKeys = true,keyProperty = "noteid")
    public int insertNote(Note note);

    //Delete note
    @Delete("delete from NOTES where noteid=#{noteid}")
    public void deleteNote(int noteid);

    //Update date.
    @Insert("update NOTES set notetitle=#{title},notedescription=#{description} where noteid=#{noteid}")
    void updateNote(Note note);

}
