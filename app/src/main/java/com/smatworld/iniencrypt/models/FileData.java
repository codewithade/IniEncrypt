package com.smatworld.iniencrypt.models;

import android.graphics.Bitmap;

import java.io.File;
import java.io.InputStream;

public class FileData {
    private Bitmap mBitmap;
    private File mFile;
    private String mFileName;
    private long mFileSize;
    private boolean mIsStreamAvailable;
    private InputStream encryptedStream;
    private InputStream decryptedStream;
    private long encryptionTime;
    private long decryptionTime;
    private String key;

    // not used
    private String mPreviewData;
    private byte[] decryptedImage;

    public FileData(Bitmap bitmap, File file, String fileName, long fileSize, String previewData) {
        mBitmap = bitmap;
        mFile = file;
        mFileName = fileName;
        mFileSize = fileSize;
        mPreviewData = previewData;
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

    public void setStreamAvailable(boolean streamAvailable) {
        mIsStreamAvailable = streamAvailable;
    }

    public boolean isStreamAvailable() {
        return mIsStreamAvailable;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public void setFileSize(long fileSize) {
        mFileSize = fileSize;
    }

    public long getEncryptionTime() {
        return encryptionTime;
    }

    public void setEncryptionTime(long encryptionTime) {
        this.encryptionTime = encryptionTime;
    }

    public long getDecryptionTime() {
        return decryptionTime;
    }

    public void setDecryptionTime(long decryptionTime) {
        this.decryptionTime = decryptionTime;
    }

    public String getPreviewData() {
        return mPreviewData;
    }

    public void setPreviewData(String previewData) {
        mPreviewData = previewData;
    }

    public byte[] getDecryptedImage() {
        return decryptedImage;
    }

    public void setDecryptedImage(byte[] decryptedImage) {
        this.decryptedImage = decryptedImage;
    }

    public InputStream getEncryptedStream() {
        return encryptedStream;
    }

    public void setEncryptedStream(InputStream encryptedStream) {
        this.encryptedStream = encryptedStream;
    }

    public InputStream getDecryptedStream() {
        return decryptedStream;
    }

    public void setDecryptedStream(InputStream decryptedStream) {
        this.decryptedStream = decryptedStream;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
