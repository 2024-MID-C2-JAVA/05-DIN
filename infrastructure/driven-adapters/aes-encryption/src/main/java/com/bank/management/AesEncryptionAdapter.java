package com.bank.management;

import com.bank.management.gateway.EncryptionGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AesEncryptionAdapter implements EncryptionGateway {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private final SecretKey secretKey;
    private final byte[] iv;


    public AesEncryptionAdapter(@Value("${encryption.key}") String base64Key) {
        byte[] key = Base64.getDecoder().decode(base64Key);
        this.secretKey = new SecretKeySpec(key, "AES");
        this.iv = generateIv();
    }

    // GENERATE VECTOR
    private byte[] generateIv() {
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    @Override
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            byte[] combined = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        try {
            byte[] decoded = Base64.getDecoder().decode(cipherText);
            byte[] iv = new byte[16];
            System.arraycopy(decoded, 0, iv, 0, iv.length);
            byte[] encryptedBytes = new byte[decoded.length - iv.length];
            System.arraycopy(decoded, iv.length, encryptedBytes, 0, encryptedBytes.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}
