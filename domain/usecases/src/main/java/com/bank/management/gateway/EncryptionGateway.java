package com.bank.management.gateway;

public interface EncryptionGateway {
    String encrypt(String plainText);
    String decrypt(String cipherText);
}