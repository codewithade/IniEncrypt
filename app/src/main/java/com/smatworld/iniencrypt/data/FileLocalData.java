package com.smatworld.iniencrypt.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.smatworld.iniencrypt.data.security.algorithms.AES;
import com.smatworld.iniencrypt.data.security.algorithms.DiffieHellman;
import com.smatworld.iniencrypt.data.security.algorithms.RSA;
import com.smatworld.iniencrypt.data.security.algorithms.TripleDES;
import com.smatworld.iniencrypt.domain.repository.FileRepo;
import com.smatworld.iniencrypt.models.Algorithm;
import com.smatworld.iniencrypt.models.TaskData;
import com.smatworld.iniencrypt.models.TaskStatus;

import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

public class FileLocalData implements FileRepo {
    MutableLiveData<TaskData<InputStream>> mCipherLiveData = new MutableLiveData<>();
    MutableLiveData<TaskData<Key>> mDHLiveData = new MutableLiveData<>();

    @Override
    public LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, byte[] key) {
        TaskData<InputStream> taskData = new TaskData<>();
        AES aes = new AES(key);
        try {
            taskData.setStartTime(System.nanoTime());
            final InputStream encryptedData = aes.encrypt(inputStream);
            taskData.setData(encryptedData);
            taskData.setSuccessMessage("Encryption successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mCipherLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Encryption Failed.");
        }
        return mCipherLiveData;
    }

    @Override
    public LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, byte[] key) {
        TaskData<InputStream> taskData = new TaskData<>();
        AES aes = new AES(key);
        try {
            taskData.setStartTime(System.nanoTime());
            final InputStream decryptedData = aes.decrypt(inputStream);
            taskData.setData(decryptedData);
            taskData.setSuccessMessage("Decryption successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mCipherLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Decryption failed.");
        }
        return mCipherLiveData;
    }

    @Override
    public LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, byte[] key) {
        TaskData<InputStream> taskData = new TaskData<>();
        TripleDES tripleDES = new TripleDES(key);
        try {
            taskData.setStartTime(System.nanoTime());
            final InputStream encryptedData = tripleDES.encrypt(inputStream);
            taskData.setData(encryptedData);
            taskData.setSuccessMessage("Encryption successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mCipherLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Encryption Failed.");
        }
        return mCipherLiveData;
    }

    @Override
    public LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, Key key) {
        TaskData<InputStream> taskData = new TaskData<>();
        TripleDES tripleDES = new TripleDES(key);
        try {
            taskData.setStartTime(System.nanoTime());
            final InputStream decryptedData = tripleDES.decrypt(inputStream);
            taskData.setData(decryptedData);
            taskData.setSuccessMessage("Decryption successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mCipherLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Decryption failed.");
        }
        return mCipherLiveData;
    }

    @Override
    public LiveData<TaskData<InputStream>> decryptTripleDES(InputStream inputStream, byte[] key) {
        TaskData<InputStream> taskData = new TaskData<>();
        TripleDES tripleDES = new TripleDES(key);
        try {
            taskData.setStartTime(System.nanoTime());
            final InputStream decryptedData = tripleDES.decrypt(inputStream);
            taskData.setData(decryptedData);
            taskData.setSuccessMessage("Decryption successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mCipherLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Decryption failed.");
        }
        return mCipherLiveData;
    }

    @Override
    public LiveData<TaskData<InputStream>> encryptRSA(InputStream inputStream, PublicKey publicKey) {
        TaskData<InputStream> taskData = new TaskData<>();
        RSA rsa = new RSA(publicKey);
        try {
            taskData.setStartTime(System.nanoTime());
            final InputStream encryptedData = rsa.encrypt(inputStream);
            taskData.setData(encryptedData);
            taskData.setSuccessMessage("Encryption successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mCipherLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Encryption failed.");
        }
        return mCipherLiveData;
    }

    @Override
    public LiveData<TaskData<InputStream>> decryptRSA(InputStream inputStream, PrivateKey privateKey) {
        TaskData<InputStream> taskData = new TaskData<>();
        RSA rsa = new RSA(privateKey);
        try {
            taskData.setStartTime(System.nanoTime());
            final InputStream decryptedData = rsa.decrypt(inputStream);
            taskData.setData(decryptedData);
            taskData.setSuccessMessage("Decryption successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mCipherLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Decryption failed.");
        }
        return mCipherLiveData;
    }

    @Override
    public LiveData<TaskData<Key>> initDHKeyExchange(int keySize, Algorithm symmetricAlgorithm) {
        TaskData<Key> taskData = new TaskData<>();
        DiffieHellman diffieHellman = new DiffieHellman(keySize, symmetricAlgorithm);
        try {
            taskData.setStartTime(System.nanoTime());
            final Key secretKey = diffieHellman.init();
            taskData.setData(secretKey);
            taskData.setSuccessMessage("DH Key Exchange successful!");
            taskData.setTaskStatus(TaskStatus.SUCCESS);
            taskData.setEndTime(System.nanoTime());
            mDHLiveData.setValue(taskData);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            taskData.setErrorMessage(String.format(Locale.getDefault(), "%s %s", "DH Key Exchange Failed.", e.getMessage()));
            taskData.setTaskStatus(TaskStatus.FAILED);
            mDHLiveData.setValue(taskData);
            e.printStackTrace();
        }
        return mDHLiveData;
    }

    private void processErrorResponse(TaskData<InputStream> taskData, Exception e, String errorMessage) {
        taskData.setErrorMessage(String.format(Locale.getDefault(), "%s %s", errorMessage, e.getMessage()));
        taskData.setTaskStatus(TaskStatus.FAILED);
        mCipherLiveData.setValue(taskData);
        e.printStackTrace();
    }
}
