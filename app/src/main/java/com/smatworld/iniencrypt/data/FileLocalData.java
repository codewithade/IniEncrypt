package com.smatworld.iniencrypt.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.smatworld.iniencrypt.data.security.algorithms.AES;
import com.smatworld.iniencrypt.domain.repository.FileRepo;
import com.smatworld.iniencrypt.models.TaskData;
import com.smatworld.iniencrypt.models.TaskStatus;

import java.io.InputStream;
import java.util.Locale;

public class FileLocalData implements FileRepo {
    MutableLiveData<TaskData<InputStream>> mLiveData = new MutableLiveData<>();

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
            mLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Encryption Failed.");
        }
        return mLiveData;
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
            mLiveData.setValue(taskData);
        } catch (Exception e) {
            processErrorResponse(taskData, e, "Decryption failed.");
        }
        return mLiveData;
    }

    private void processErrorResponse(TaskData<InputStream> taskData, Exception e, String errorMessage) {
        taskData.setErrorMessage(String.format(Locale.getDefault(), "%s %s", errorMessage, e.getMessage()));
        taskData.setTaskStatus(TaskStatus.FAILED);
        mLiveData.setValue(taskData);
        e.printStackTrace();
    }
}
