package task1;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;

class SecurePackage {
    public SecurePackage(SecretKey sessionKey, byte[] iv, byte[] cipherText, byte[] signature) {
        // ...
    }
}

// task 1
public class SecureMessenger {

    private static final byte[] MAGIC_HEADER = { 0x53, 0x45, 0x43, 0x5F, 0x56, 0x31 };

    public SecurePackage packageMessage(String message, PrivateKey myIdentityKey) throws Exception {

        // your code here

        
        
        // e.g. return new SecurePackage(sessionKey, iv, cipherText, signature);
    }
}