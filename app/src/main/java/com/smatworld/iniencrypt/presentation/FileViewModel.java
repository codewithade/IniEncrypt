package com.smatworld.iniencrypt.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.smatworld.iniencrypt.domain.usecases.FileTask;
import com.smatworld.iniencrypt.models.Algorithm;
import com.smatworld.iniencrypt.models.FileData;
import com.smatworld.iniencrypt.models.TaskData;

import java.io.InputStream;

public class FileViewModel extends ViewModel {
    private boolean isAlgorithmSelected = false;
    private final MutableLiveData<FileData> mFileData = new MutableLiveData<>();
    private Algorithm mAlgorithm;
    private final FileTask mFileTask;

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

    public LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, String key) {
        return mFileTask.decryptAES(inputStream, key.getBytes());
    }
}
