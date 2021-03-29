package com.smatworld.iniencrypt.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.smatworld.iniencrypt.data.security.utils.CustomKeyPairGenerator;
import com.smatworld.iniencrypt.domain.usecases.FileTask;
import com.smatworld.iniencrypt.models.Algorithm;
import com.smatworld.iniencrypt.models.FileData;
import com.smatworld.iniencrypt.models.TaskData;

import java.io.InputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public class FileViewModel extends ViewModel {
    private boolean isAlgorithmSelected = false;
    private final MutableLiveData<FileData> mFileData = new MutableLiveData<>();
    private Algorithm mAlgorithm;
    private Algorithm mSelectedSymmetricAlgorithm;
    private final FileTask mFileTask;
    private PrivateKey mPrivateKey;
    private PublicKey mPublicKey;

    public FileViewModel(FileTask fileTask) {
        mFileTask = fileTask;
    }

    public LiveData<FileData> getFileData() {
        return mFileData;
    }

    public void setFileData(FileData fileData) {
        mFileData.setValue(fileData);
    }

    public boolean isAlgorithmSelected() {
        return isAlgorithmSelected;
    }

    public void setAlgorithmSelected(boolean algorithmSelected) {
        isAlgorithmSelected = algorithmSelected;
    }

    public Algorithm getAlgorithm() {
        return mAlgorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        mAlgorithm = algorithm;
    }

    public LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, String key) {
        return mFileTask.encryptAES(inputStream, key.getBytes());
    }

    public LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, Key key) {
        return mFileTask.encryptAES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, String key) {
        return mFileTask.decryptAES(inputStream, key.getBytes());
    }

    public LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, Key key) {
        return mFileTask.decryptAES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, String key) {
        return mFileTask.encryptTripleDES(inputStream, key.getBytes());
    }

    public LiveData<TaskData<InputStream>> encryptTripleDES(InputStream inputStream, Key key) {
        return mFileTask.encryptTripleDES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> decryptTripleDES(InputStream inputStream, String key) {
        return mFileTask.decryptTripleDES(inputStream, key.getBytes());
    }

    public LiveData<TaskData<InputStream>> decryptTripleDES(InputStream inputStream, Key key) {
        return mFileTask.decryptTripleDES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> encryptRSA(InputStream inputStream, int keyLength) {
        CustomKeyPairGenerator generator = new CustomKeyPairGenerator(keyLength, Algorithm.RSA.getAlgorithm());
        setPrivateKey(generator.getPrivateKey());
        setPublicKey(generator.getPublicKey());
        return mFileTask.encryptRSA(inputStream, mPublicKey);
    }

    public LiveData<TaskData<InputStream>> decryptRSA(InputStream inputStream) {
        return mFileTask.decryptRSA(inputStream, mPrivateKey);
    }

    public LiveData<TaskData<Key>> initDHKeyExchange(int keySize, Algorithm symmetricAlgorithm) {
        return mFileTask.initDHKeyExchange(keySize, symmetricAlgorithm);
    }

    private void setPrivateKey(PrivateKey privateKey) {
        mPrivateKey = privateKey;
    }

    private void setPublicKey(PublicKey publicKey) {
        mPublicKey = publicKey;
    }

    public Algorithm getSelectedSymmetricAlgorithm() {
        return mSelectedSymmetricAlgorithm;
    }

    public void setSelectedSymmetricAlgorithm(Algorithm selectedSymmetricAlgorithm) {
        mSelectedSymmetricAlgorithm = selectedSymmetricAlgorithm;
    }
}
