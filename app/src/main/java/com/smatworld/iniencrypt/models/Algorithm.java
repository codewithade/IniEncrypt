package com.smatworld.iniencrypt.models;

public enum Algorithm {
    RSA("RSA"),
    DIFFIE_HELLMAN("DH"),
    AES("AES"),
    TRIPLE_DES("DESede");

    private final String algorithm;

    Algorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
