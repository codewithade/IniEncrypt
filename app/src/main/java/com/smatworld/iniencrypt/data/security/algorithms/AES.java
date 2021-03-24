package com.smatworld.iniencrypt.data.security.algorithms;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static final String ALGORITHM = "AES";
    private final String mTransformation = "AES/CBC/PKCS5Padding";
    private final byte[] mKey;

    // supports only key size of 16 bytes
    public AES(byte[] key) {
        mKey = key;
    }

    public InputStream encrypt(InputStream stream2encrypt) throws Exception {
        byte[] stream2encryptArray = new byte[stream2encrypt.available()];
        stream2encrypt.read(stream2encryptArray);
        Cipher cipher = Cipher.getInstance(mTransformation);

        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, generateKey(), ivParams);
        byte[] encryptedData = cipher.doFinal(stream2encryptArray);
        return new ByteArrayInputStream(encryptedData);
    }

    public InputStream decrypt(InputStream encryptedData) throws Exception {
        byte[] encryptedByteArray = new byte[encryptedData.available()];
        encryptedData.read(encryptedByteArray);

        Cipher cipher = Cipher.getInstance(mTransformation);
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, generateKey(), ivParams);
        byte[] decryptedData = cipher.doFinal(encryptedByteArray);

        return new ByteArrayInputStream(decryptedData);
    }

    private Key generateKey() {
        // returns the first 16 bytes of the passed key
        return new SecretKeySpec(mKey, 0, 16, ALGORITHM);
    }
}
