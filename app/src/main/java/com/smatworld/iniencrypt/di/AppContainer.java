package com.smatworld.iniencrypt.di;

import com.smatworld.iniencrypt.data.FileLocalData;
import com.smatworld.iniencrypt.domain.repository.FileRepo;
import com.smatworld.iniencrypt.domain.usecases.FileTask;
import com.smatworld.iniencrypt.presentation.factory.FileViewModelFactory;

// Manual Dependency Injection to prevent having boilerplate codes scattered around the codebase
// https://developer.android.com/training/dependency-injection/manual#java
public class AppContainer {
    private final FileRepo fileRepo = new FileLocalData();
    private final FileTask fileTask = new FileTask(fileRepo);
    public final FileViewModelFactory fileViewModelFactory = new FileViewModelFactory(fileTask);
}
