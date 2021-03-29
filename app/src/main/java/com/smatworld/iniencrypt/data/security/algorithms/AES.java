package com.smatworld.iniencrypt.data.security.algorithms;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.smatworld.iniencrypt.utils.Constants.TAG;

public class AES {

    private static final String ALGORITHM = "AES";
    private final String mTransformation = "AES/CBC/PKCS5Padding";
    private byte[] mKey;
    private Key mSecretKey;

    // supports only key size of 16 bytes
    public AES(byte[] key) {
        mKey = key;
    }

    public AES(Key secretKey) {
        mSecretKey = secretKey;
    }

    public InputStream encrypt(InputStream stream2encrypt) throws Exception {
        byte[] stream2encryptArray = new byte[stream2encrypt.available()];
        final int readLength = stream2encrypt.read(stream2encryptArray);
        Cipher cipher = Cipher.getInstance(mTransformation);

        // block size is 16 for AES
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, generateKey(), ivParams);
        byte[] encryptedData = cipher.doFinal(stream2encryptArray);
        return new ByteArrayInputStream(encryptedData);
    }

    /*public InputStream decrypt(InputStream encryptedData) throws Exception {

        // InputStream is = new ByteArrayInputStream(new byte[] { 0, 1, 2 }); // not really unknown

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = encryptedData.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        byte[] encryptedByteArray = buffer.toByteArray();

        Cipher cipher = Cipher.getInstance(mTransformation);

        // block size is 16 for AES
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, generateKey(), ivParams);
        byte[] decryptedData = cipher.doFinal(encryptedByteArray);

        return new ByteArrayInputStream(decryptedData);
    }*/

    public InputStream decrypt(InputStream encryptedData) throws Exception {
        byte[] encryptedByteArray = new byte[encryptedData.available()];
        final int readLength = encryptedData.read(encryptedByteArray);
        Log.i(TAG, "decryptAES: readLength: " + readLength);
        Cipher cipher = Cipher.getInstance(mTransformation);

        // block size is 16 for AES
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, generateKey(), ivParams);
        byte[] decryptedData = cipher.doFinal(encryptedByteArray);

        return new ByteArrayInputStream(decryptedData);
    }

    private Key generateKey() {
        if (mKey == null)
            return mSecretKey;
        // returns the first 16 bytes of the passed key
        return new SecretKeySpec(mKey, 0, 16, ALGORITHM);
    }
}
