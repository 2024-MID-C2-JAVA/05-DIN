package com.bank.management.usecase;

import com.bank.management.gateway.EncryptionGateway;

public class EncryptionUseCase {

    private final EncryptionGateway encryptionGateway;

    public EncryptionUseCase(EncryptionGateway encryptionGateway) {
        this.encryptionGateway = encryptionGateway;
    }

    public String encryptData(String plainText) {
        return encryptionGateway.encrypt(plainText);
    }

    public String decryptData(String cipherText) {
        return encryptionGateway.decrypt(cipherText);
    }
}
