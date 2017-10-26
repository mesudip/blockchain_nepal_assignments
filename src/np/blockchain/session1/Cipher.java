package np.blockchain.session1;

import java.security.InvalidKeyException;

public abstract class Cipher {
    public abstract byte[] encrypt(byte[]data) throws InvalidKeyException;
    public abstract byte[] decrypt(byte[] data);
    public void setKey(byte [] data){

    }
    // set key length.
    public void setKey(String s){
        setKey(s.getBytes());
    }
    public byte[] encrypt(String message) throws InvalidKeyException {
        return encrypt(message.getBytes());
    }
    public String decryptToString(byte[] encMessage){
        return new String(decrypt(encMessage));
    }

}

