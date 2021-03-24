package com.smatworld.iniencrypt.data.security.algorithms;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// https://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html
public class TripleDES {

    private byte[] mKey;
    private Key mSecretKey;
    private static final String ALGORITHM = "DESede";
    private static final String sTransformation = "DESede/CBC/PKCS5Padding";

    // needs a 16 or 24 bytes key to work
    // but accept only a 16-byte key (128 bits)
    public TripleDES(byte[] key) {
        mKey = key;
    }

    public TripleDES(Key secretKey) {
        mSecretKey = secretKey;
    }

    public InputStream encrypt(InputStream stream2encrypt) throws Exception {

        byte[] stream2encryptArray = new byte[stream2encrypt.available()];
        stream2encrypt.read(stream2encryptArray);

        Cipher cipher = Cipher.getInstance(sTransformation);
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);

        cipher.init(Cipher.ENCRYPT_MODE, generateKey(), iv);
        byte[] encryptedData = cipher.doFinal(stream2encryptArray);
        return new ByteArrayInputStream(encryptedData);
    }

    public InputStream decrypt(InputStream encryptedData) throws Exception {

        byte[] encryptedByteArray = new byte[encryptedData.available()];
        encryptedData.read(encryptedByteArray);

        Cipher cipher = Cipher.getInstance(sTransformation);
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);

        cipher.init(Cipher.DECRYPT_MODE, generateKey(), iv);
        byte[] decryptedData = cipher.doFinal(encryptedByteArray);
        return new ByteArrayInputStream(decryptedData);
    }

    private Key generateKey() {
        if (mKey == null)
            return mSecretKey;
        if (mKey.length <= 16)
            // returns the first 16 bytes of the passed key
            return new SecretKeySpec(mKey, 0, 16, ALGORITHM);
        // returns the first 16 bytes of the passed key
        return new SecretKeySpec(mKey, 0, 24, ALGORITHM);
    }

}
