package com.smatworld.iniencrypt.utils;

public class Constants {
    public static final String TAG = "com.smatworld.TAG";
    public static final int FILE_REQUEST_CODE = 4;
    // max key sizes in bytes
    public static final int AES_MAX_KEY_SIZE = 16;
    public static final int TRIPLE_DES_MIN_KEY_SIZE = 16;
    public static final int TRIPLE_DES_MAX_KEY_SIZE = 24;
    public static final int DH_MAX_KEY_SIZE = 2048;
    public static final int DH_MIN_KEY_SIZE = 512;
    public static final int RSA_MIN_KEY_SIZE = 1024;
    public static final int RSA_MAX_KEY_SIZE = 65536;

    // File Name Constants
    public static final String ENCRYPTED_TEXT_FILE_NAME = "cryptText.bin";
    public static final String DECRYPTED_TEXT_FILE_NAME = "decryptedText";
    public static final String ENCRYPTED_IMAGE_FILE_NAME = "cryptImage.bin";
    public static final String DECRYPTED_IMAGE_FILE_NAME = "decryptedImage";

    // remove
    public static final String PLAIN_IMAGE_NAME = "plainImage";
    public static final String PLAIN_TEXT_FILE_NAME = "plainText";
}
