package com.smatworld.iniencrypt.domain.repository;

import androidx.lifecycle.LiveData;

import com.smatworld.iniencrypt.models.TaskData;

import java.io.InputStream;

public interface FileRepo {
    LiveData<TaskData<InputStream>> encryptAES(InputStream inputStream, byte[] key);

    LiveData<TaskData<InputStream>> decryptAES(InputStream inputStream, byte[] key);
}
