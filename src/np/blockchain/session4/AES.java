package np.blockchain.session4;
import np.blockchain.session1.Cipher;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;


public class AES extends np.blockchain.session1.Cipher {
    // In my view these things are better made static
    private static   KeyGenerator generator = null;
    private static javax.crypto.Cipher aesCipher=null;

    static{
        try {
            // this exception should never occur
             generator = KeyGenerator.getInstance("AES");
             aesCipher=javax.crypto.Cipher.getInstance("AES");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        }
    }

    private SecretKey key=null;
    // let's return SecretKey as it will be required.
    public void setKey(){
        this.key=generator.generateKey();

    }
    // truncates or pads key if necessary.
    public void setKey(byte[] keybyte){
        //TODO : may be i should use hasn function instead
        byte[] bytes=new byte[16];
        for (int i=0;i<bytes.length && i< keybyte.length;i++){
            bytes[i]=keybyte[i];
        }
        this.key=new SecretKeySpec(bytes,"AES");
    }
    public SecretKey getKey(){
        return this.key;
    }

    @Override
    public byte[] encrypt(byte[] message) {
        try {
            aesCipher.init(javax.crypto.Cipher.ENCRYPT_MODE,key);
            return aesCipher.doFinal(message);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] data) {
        try {
            aesCipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
            return aesCipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
