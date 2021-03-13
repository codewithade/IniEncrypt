package com.smatworld.iniencrypt.models;

import android.graphics.Bitmap;

import java.io.File;

public class FileData {
    private Bitmap mBitmap;
    private File mFile;
    private String mFileName;
    private long mFileSize;
    private boolean mHasData;

    public FileData(Bitmap bitmap, File file, String fileName, long fileSize, boolean hasData) {
        mBitmap = bitmap;
        mFile = file;
        mFileName = fileName;
        mFileSize = fileSize;
        mHasData = hasData;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File file) {
        mFile = file;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setHasData(boolean hasData) {
        mHasData = hasData;
    }

    public boolean isHasData() {
        return mHasData;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public void setFileSize(long fileSize) {
        mFileSize = fileSize;
    }
}
