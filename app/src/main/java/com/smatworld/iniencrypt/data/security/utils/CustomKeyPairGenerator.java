package com.smatworld.iniencrypt.data.security.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CustomKeyPairGenerator {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final int keyLength;
    private final String algorithm;

    public CustomKeyPairGenerator(int keyLength, String algorithm) {
        this.algorithm = algorithm;
        this.keyLength = keyLength;
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert keyGen != null;
        keyGen.initialize(keyLength);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
