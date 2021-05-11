package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final EncryptionService encryptionService;
    private final CredentialMapper mapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper mapper) {
        this.encryptionService = encryptionService;
        this.mapper = mapper;
    }

    public List<Credential> getAllCredentials(int userId){
        return mapper.getCredentialsByUser(userId);
    }

    public int insertCredential(int userId, Credential credential){
        credential.setUserId(userId);
        String hashedCredential= encryptionService.encryptValue(credential.getPassword(),credential.getKey());
        credential.setPassword(hashedCredential);
        return mapper.addCredential(credential);
    }

    public void deleteCredential(Integer credentialId, Integer userId) {
        mapper.deleteCredential(credentialId);
    }

    public void updateCredential(int userId, Credential credential) {
        credential.setUserId(userId);
        Credential cred = mapper.getCredentialsById(credential.getCredentialId());
        credential.setKey(cred.getKey());
        String encodedPassword = this.encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encodedPassword);
        mapper.update(credential);
    }

    public Credential getCredentialById(Integer credentialId) {
        return mapper.getCredentialsById(credentialId);
    }
}
