package com.smatworld.iniencrypt.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.smatworld.iniencrypt.domain.usecases.FileTask;
import com.smatworld.iniencrypt.presentation.FileViewModel;

public class FileViewModelFactory implements ViewModelProvider.Factory {
    private final FileTask mFileTask;

    public FileViewModelFactory(FileTask fileTask) {
        mFileTask = fileTask;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FileViewModel.class)) {
            return (T) new FileViewModel(mFileTask);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
