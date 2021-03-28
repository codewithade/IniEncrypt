package com.smatworld.iniencrypt.data.security.algorithms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private PrivateKey mPrivateKey;
    private PublicKey mPublicKey;

    public RSA(PrivateKey privateKey, PublicKey publicKey) {
        this.mPrivateKey = privateKey;
        this.mPublicKey = publicKey;
    }

    public RSA(PrivateKey privateKey) {
        mPrivateKey = privateKey;
    }

    public RSA(PublicKey publicKey) {
        mPublicKey = publicKey;
    }

    public InputStream encrypt(InputStream stream2encrypt)
            throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, IOException {
        byte[] stream2encryptArray = new byte[stream2encrypt.available()];
        stream2encrypt.read(stream2encryptArray);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, mPublicKey);

        byte[] encryptedData = cipher.doFinal(stream2encryptArray);
        return new ByteArrayInputStream(encryptedData);
    }

    public InputStream decrypt(InputStream stream2decrypt) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] stream2decryptArray = new byte[stream2decrypt.available()];
        stream2decrypt.read(stream2decryptArray);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, mPrivateKey);

        byte[] decryptedData = cipher.doFinal(stream2decryptArray);
        return new ByteArrayInputStream(decryptedData);
    }
}
