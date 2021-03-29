package com.smatworld.iniencrypt.models;

import android.graphics.Bitmap;

import java.io.File;

public class FileData {
    private Bitmap mBitmap;
    private File mFile;
    private String mFileName;
    private long mFileSize;
    private boolean mIsStreamAvailable;
    private boolean mIsSecretKeyAvailable;
    private long encryptionTime;
    private long decryptionTime;
    private String key;
    private String mFileExtension;
    private boolean mIsImage;

    private String mPreviewData;
    private String encryptedFile; // both text and image are converted to Base64 encoded texts
    private String decryptedText;
    private String decryptedImagePath;

    public FileData(Bitmap bitmap, File file, String fileName, long fileSize, String fileExtension, String previewData, boolean isImage) {
        mBitmap = bitmap;
        mFile = file;
        mFileName = fileName;
        mFileSize = fileSize;
        mFileExtension = fileExtension;
        mPreviewData = previewData;
        mIsImage = isImage;
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

    public String getDecryptedImagePath() {
        return decryptedImagePath;
    }

    public void setDecryptedImagePath(String decryptedImagePath) {
        this.decryptedImagePath = decryptedImagePath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileExtension() {
        return mFileExtension;
    }

    public void setFileExtension(String fileExtension) {
        mFileExtension = fileExtension;
    }

    public boolean isImage() {
        return mIsImage;
    }

    public void setIsImage(boolean image) {
        mIsImage = image;
    }

    public String getEncryptedFile() {
        return encryptedFile;
    }

    public void setEncryptedFile(String encryptedFile) {
        this.encryptedFile = encryptedFile;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    public void setDecryptedText(String decryptedText) {
        this.decryptedText = decryptedText;
    }

    public boolean isSecretKeyAvailable() {
        return mIsSecretKeyAvailable;
    }

    public void setSecretKeyAvailable(boolean secretKeyAvailable) {
        mIsSecretKeyAvailable = secretKeyAvailable;
    }
}
