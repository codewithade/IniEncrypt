package com.smatworld.iniencrypt.domain.usecases;

import androidx.lifecycle.LiveData;

import com.smatworld.iniencrypt.domain.repository.FileRepo;
import com.smatworld.iniencrypt.models.Algorithm;
import com.smatworld.iniencrypt.models.TaskData;

import java.io.InputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public class FileTask {

    private final FileRepo mFileRepo;

    public FileTask(FileRepo fileRepo) {
        mFileRepo = fileRepo;
    }

    public LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, byte[] key) {
        return mFileRepo.encryptAES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, Key key) {
        return mFileRepo.encryptAES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, byte[] key) {
        return mFileRepo.decryptAES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, Key key) {
        return mFileRepo.decryptAES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, byte[] key) {
        return mFileRepo.encryptTripleDES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, Key key) {
        return mFileRepo.encryptTripleDES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> decryptTripleDES(InputStream inputStream, byte[] key) {
        return mFileRepo.decryptTripleDES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> decryptTripleDES(InputStream inputStream, Key key) {
        return mFileRepo.decryptTripleDES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> encryptRSA(InputStream inputStream, PublicKey publicKey) {
        return mFileRepo.encryptRSA(inputStream, publicKey);
    }

    public LiveData<TaskData<InputStream>> decryptRSA(InputStream inputStream, PrivateKey privateKey) {
        return mFileRepo.decryptRSA(inputStream, privateKey);
    }

    public LiveData<TaskData<Key>> initDHKeyExchange(int keySize, Algorithm symmetricAlgorithm) {
        return mFileRepo.initDHKeyExchange(keySize, symmetricAlgorithm);
    }

}
