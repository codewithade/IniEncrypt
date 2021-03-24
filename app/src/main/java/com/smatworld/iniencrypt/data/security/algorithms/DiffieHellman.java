package com.smatworld.iniencrypt.data.security.algorithms;

import com.smatworld.iniencrypt.data.security.utils.CustomKeyPairGenerator;
import com.smatworld.iniencrypt.models.Algorithm;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.smatworld.iniencrypt.data.security.utils.DataUtil.toHexString;

public class DiffieHellman {

    private static final String ALGORITHM = "DH";
    private final int mKeySize;
    private final Algorithm mSymmetricAlgorithm;
    private KeyAgreement mSenderKeyAgreement;
    private KeyAgreement mRecipientKeyAgreement;

    public DiffieHellman(int keySize, Algorithm symmetricAlgorithm) {
        this.mKeySize = keySize;
        mSymmetricAlgorithm = symmetricAlgorithm;
    }

    public Key init() throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException,
            InvalidKeySpecException, InvalidAlgorithmParameterException {
        System.out.println("Symmetric Algorithm in use: " + mSymmetricAlgorithm.getAlgorithm());
        final byte[] senderEncodedPublicKey = initSender();
        final byte[] recipientEncodedPublicKey = initRecipient(senderEncodedPublicKey);
        initSenderPhase(recipientEncodedPublicKey, mSenderKeyAgreement);
        initRecipientPhase(senderEncodedPublicKey, mRecipientKeyAgreement);
        final byte[] senderSharedSecret = generateSenderSharedSecret(mSenderKeyAgreement);
        final byte[] recipientSharedSecret = generateRecipientSharedSecret(mRecipientKeyAgreement);

        System.out.println("Sender's secret: " + toHexString(senderSharedSecret));
        System.out.println("Recipient's secret: " + toHexString(recipientSharedSecret));

        if (!Arrays.equals(senderSharedSecret, recipientSharedSecret))
            throw new InvalidAlgorithmParameterException("Shared secrets differs.");
        System.out.println("Shared secrets are the same.");

        System.out.println("Use shared secret as SecretKey object ...");
        return getSecretKey(recipientSharedSecret);
    }

    private byte[] initSender() throws NoSuchAlgorithmException, InvalidKeyException {
        // Sender creates his own DH key pair with the specified key size
        System.out.println("Sender: Generate DH keypair ...");
        CustomKeyPairGenerator senderDHGen = new CustomKeyPairGenerator(mKeySize, ALGORITHM);

        // Sender creates and initializes his DH KeyAgreement object
        System.out.println("Sender: Initialization ...");
        mSenderKeyAgreement = KeyAgreement.getInstance(ALGORITHM);
        mSenderKeyAgreement.init(senderDHGen.getPrivateKey());

        // Sender encodes his PublicKey and sends back to Recipient
        return senderDHGen.getPublicKey().getEncoded();
    }

    private byte[] initRecipient(byte[] senderEncodedPublicKey) throws NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException {
        KeyFactory recipientKeyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec encodedKeySPec = new X509EncodedKeySpec(senderEncodedPublicKey);
        PublicKey senderPublicKey = recipientKeyFactory.generatePublic(encodedKeySPec);

        DHParameterSpec dhParamFromSenderPublicKey = ((DHPublicKey) senderPublicKey).getParams();

        // Recipient generates DH KeyPairs
        KeyPair recipientKeyPair = getKeyPairFromParam(dhParamFromSenderPublicKey);

        // Recipient creates and initializes his DH KeyAgreement object
        System.out.println("Recipient: Initialization ...");
        mRecipientKeyAgreement = KeyAgreement.getInstance(ALGORITHM);
        mRecipientKeyAgreement.init(recipientKeyPair.getPrivate());

        // Recipient encodes his PublicKey and sends back to sender
        return recipientKeyPair.getPublic().getEncoded();
    }

    private void initSenderPhase(byte[] recipientEncodedPublicKey, KeyAgreement senderKeyAgreement)
            throws InvalidKeyException, IllegalStateException, NoSuchAlgorithmException, InvalidKeySpecException {
        /*
         * Sender uses Recipients's public key for the first (and only) phase of his
         * version of the DH protocol. Before he can do so, he has to instantiate a DH
         * public key from recipients's encoded key material.
         */
        KeyFactory senderKeyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec encodedKeySPec = new X509EncodedKeySpec(recipientEncodedPublicKey);
        PublicKey recipientPublicKey = senderKeyFactory.generatePublic(encodedKeySPec);

        System.out.println("Sender: Execute PHASE 1 ...");
        senderKeyAgreement.doPhase(recipientPublicKey, true);
    }

    private void initRecipientPhase(byte[] senderEncodedPublicKey, KeyAgreement recipientKeyAgreement)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalStateException {

        /*
         * Recipient uses Sender's public key for the first (and only) phase of his
         * version of the DH protocol.
         */
        KeyFactory recipientKeyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(senderEncodedPublicKey);
        PublicKey senderPublicKey = recipientKeyFactory.generatePublic(encodedKeySpec);
        System.out.println("Recipient: Execute PHASE 1 ...");
        recipientKeyAgreement.doPhase(senderPublicKey, true);
    }

    private byte[] generateSenderSharedSecret(KeyAgreement senderKeyAgreement) {
        /*
         * At this stage, both Sender and Recipient have completed the DH key agreement
         * protocol. Both generate the (same) shared secret.
         */
        return senderKeyAgreement.generateSecret();
    }

    private byte[] generateRecipientSharedSecret(KeyAgreement recipientKeyAgreement) {
        /*
         * At this stage, both Sender and Recipient have completed the DH key agreement
         * protocol. Both generate the (same) shared secret.
         */
        return recipientKeyAgreement.generateSecret();
    }

    // Works for only AES(16 bytes keys) and TripleDES(16, 24 bytes keys)
    private SecretKeySpec getSecretKey(final byte[] sharedSecret) {
        System.out.println("Shared Secret size: " + sharedSecret.length);
        if (mSymmetricAlgorithm == Algorithm.AES)
            // returns the first 16 bytes of the passed secret key
            return new SecretKeySpec(sharedSecret, 0, 16, mSymmetricAlgorithm.getAlgorithm());
        else if (mSymmetricAlgorithm == Algorithm.TRIPLE_DES)
            // returns the first 24 bytes of the passed secret key
            return new SecretKeySpec(sharedSecret, 0, 24, mSymmetricAlgorithm.getAlgorithm());
            // returns a secret key of default size: 128 bytes)
        else return new SecretKeySpec(sharedSecret, mSymmetricAlgorithm.getAlgorithm());
    }

    private KeyPair getKeyPairFromParam(DHParameterSpec dhParamSpec)
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGen.initialize(dhParamSpec);
        return keyPairGen.generateKeyPair();
    }
}

