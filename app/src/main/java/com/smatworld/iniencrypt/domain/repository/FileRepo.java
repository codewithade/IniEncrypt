package com.smatworld.iniencrypt.domain.repository;

import androidx.lifecycle.LiveData;

import com.smatworld.iniencrypt.models.Algorithm;
import com.smatworld.iniencrypt.models.TaskData;

import java.io.InputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface FileRepo {
    LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, byte[] key);

    LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, byte[] key);

    LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, byte[] key);

    LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, Key key);

    LiveData<TaskData<InputStream>> decryptTripleDES(InputStream inputStream, byte[] key);

    LiveData<TaskData<InputStream>> encryptRSA(InputStream inputStream, PublicKey publicKey);

    LiveData<TaskData<InputStream>> decryptRSA(InputStream inputStream, PrivateKey privateKey);

    LiveData<TaskData<Key>> initDHKeyExchange(int keySize, Algorithm symmetricAlgorithm);
}
