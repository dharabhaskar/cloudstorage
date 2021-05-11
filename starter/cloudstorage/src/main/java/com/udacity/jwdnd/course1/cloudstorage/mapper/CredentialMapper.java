package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("select * from CREDENTIALS where userid=#{userId}")
    public List<Credential> getCredentialsByUser(int userId);

    @Select("select * from CREDENTIALS where credentialid=#{credentialId}")
    Credential getCredentialsById(Integer credentialId);

    /*@Insert("insert into CREDENTIAL(url,username,key,password,userid) values(" +
            "#{url},#{username},#{key},#{password},#{userid})")*/
    @Insert("INSERT INTO CREDENTIALS (`url`, `username`, `key`, `password`, `userid`) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialId")
    public int addCredential(Credential credential);

    @Delete("delete from CREDENTIALS where credentialid=#{credentialId}")
    public void deleteCredential(int credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, `key` = #{key}, `password` = #{password}, " +
            "username = #{username} WHERE credentialid = #{credentialId}")
    int update(Credential credential);
}
