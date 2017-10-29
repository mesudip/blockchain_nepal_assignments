package np.blockchain.session1;


import java.security.InvalidKeyException;

public abstract class Cipher {

    public static class CryptError extends Exception{ }
    public static class EncryptionError extends CryptError{ };
    public static class DecryptionError extends CryptError{ };
    public static class InitializationError extends CryptError{};


    public abstract byte[] encrypt(byte[]data) throws EncryptionError;
    public abstract byte[] decrypt(byte[] data) throws DecryptionError;
    public void setKey(byte [] data) throws InvalidKeyException {

    }
    // set key length.
    public void setKey(String s){
        setKey(s.getBytes());
    }
    public byte[] encrypt(String message) throws EncryptionError {
        return encrypt(message.getBytes());
    }
    public String decryptToString(byte[] encMessage) throws DecryptionError {
        return new String(decrypt(encMessage));
    }

}

