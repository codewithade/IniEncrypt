package com.smatworld.iniencrypt.data.security.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CustomKeyPairGenerator {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private final int keyLength;
    private final String algorithm;

    public CustomKeyPairGenerator(int keyLength, String algorithm) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.keyLength = keyLength;
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
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
