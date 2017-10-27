package np.blockchain.session4;

import np.blockchain.session1.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.plugin2.message.Message;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Arrays;



public class AES extends np.blockchain.session1.Cipher {

    // In my view these things are better made static
    private static KeyGenerator generator = null;
    private static MessageDigest sha256 = null;
    private static MessageDigest md5 = null;



    static {
        try {
            //for s
            sha256 = MessageDigest.getInstance("SHA-256");
            md5 = MessageDigest.getInstance("md5");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        }
    }

    // :D java enum is quiet weird
    public enum Size { // enums are static by default
        // the lengts are converted to bytes
        aes256(32), aes128(16), aes196(25);

        private int val;

        // construction. enum constructors are always private.
        Size(int val) {
            this.val = val;
        }

        public int toInt() {
            return this.val;
        }

    }
    public enum Mode{
        ECB,CBC,CFB,OFB,CTR
    }
    ;
    // private class instance variables
    private javax.crypto.Cipher aesCipher=null;
    private IvParameterSpec iv;
    private SecretKeySpec key;
    Size ivSize = Size.aes128;

    // default constructor
    public AES() throws InitializationError {

        try {
            aesCipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            throw new InitializationError();
        }

        byte[] iv = new byte[ivSize.toInt()];
        (new SecureRandom()).nextBytes(iv);
        this.iv = new IvParameterSpec(iv);

    }

    // specivy the key size and mode to constructor
    public AES(Size ivSize,Mode mode) throws InitializationError {
        try {
            aesCipher = javax.crypto.Cipher.getInstance("AES/"+mode.toString()+"/PKCS5Padding");
        } catch (Exception e) {
            throw new InitializationError();
        }
        this.ivSize = ivSize;
        byte[] iv = new byte[ivSize.toInt()];
        (new SecureRandom()).nextBytes(iv);
        this.iv = new IvParameterSpec(iv);
    }

    public void setKey(byte[] keybyte) {
        sha256.update(keybyte);
        byte[] key=Arrays.copyOf(md5.digest(),this.ivSize.toInt());
        this.key = new SecretKeySpec(key, "AES");
    }

    @Override
    public byte[] encrypt(byte[] message) throws EncryptionError {
        try {
            aesCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key, iv);
            return aesCipher.doFinal(message);

        } catch (Exception e) {
            throw new EncryptionError();
        }
    }

    @Override
    public byte[] decrypt(byte[] data) throws DecryptionError {
        try {

            aesCipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, iv);
            return aesCipher.doFinal(data);

        } catch (Exception e) {
            throw new DecryptionError();
        }
    }

}
