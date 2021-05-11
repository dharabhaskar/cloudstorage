package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private HashService hashService;
    private UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public int createUser(User user){
        SecureRandom secureRandom=new SecureRandom();
        byte[] salt=new byte[16];
        secureRandom.nextBytes(salt);

        String encodedSalt= Base64.getEncoder().encodeToString(salt);
        String hashedPassword=hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insertUser(User .builder()
                .username(user.getUsername())
                .salt(encodedSalt)
                .password(hashedPassword)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build());
    }

    public boolean isUserAvailable(String username){
        return userMapper.getUserByUsername(username)==null;
    }

    public User getUserByUsername(String username){
        return userMapper.getUserByUsername(username);
    }

}
