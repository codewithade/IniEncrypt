package com.smatworld.iniencrypt.domain.usecases;

import androidx.lifecycle.LiveData;

import com.smatworld.iniencrypt.domain.repository.FileRepo;
import com.smatworld.iniencrypt.models.TaskData;

import java.io.InputStream;

public class FileTask {

    private final FileRepo mFileRepo;

    public FileTask(FileRepo fileRepo) {
        mFileRepo = fileRepo;
    }

    public LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, byte[] key) {
        return mFileRepo.encryptAES(inputStream, key);
    }

    public LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, byte[] key) {
        return mFileRepo.decryptAES(inputStream, key);
    }
}
