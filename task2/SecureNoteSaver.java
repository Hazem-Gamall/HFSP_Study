package task2;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

class VaultEntry {
    public VaultEntry(byte[] salt, byte[] iv, byte[] cipherText, byte[] checksum) {
        // ...
    }
}

// task 2
public class SecureNoteSaver {

    public VaultEntry saveNote(char[] password, String title, String content) throws Exception {
       // your code here



       // e.g. return new VaultEntry(salt, iv, cipherText, checksum);
    }
}