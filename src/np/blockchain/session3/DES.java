package np.blockchain.session3;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class DES extends np.blockchain.session1.Cipher{
    javax.crypto.Cipher desCipher;
    Key key;
    public DES(byte[] key) {
        try {
            this.desCipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        this.setKey(key);

    }

    public void setKey(byte[] key){
        byte[]formatted_key= new byte[8];
        byte[] randomByte=new byte[8];

        //if the given key has smaller length, fill it with pseudo random bytes
        if(key.length<formatted_key.length){
            int i=0;
            Random random=new Random(3);
            for(i=0;i<key.length;i++){
                formatted_key[i]=key[i];
            }
            for(i=0;i<formatted_key.length;i++){
                random.nextBytes(randomByte);
                formatted_key[i]=randomByte[0];
            }
            System.err.println("Des.Setkey() :Smaller Key was padded");
        }
        // if the given key length is larger, truncate the key.
        else if(key.length>formatted_key.length){
            formatted_key= Arrays.copyOfRange(key,0,formatted_key.length);
            System.err.println("Des.SetKey() : Larger key was truncated");

        }
        else{
            formatted_key=key;
        }
        // create keyspec
        SecretKeySpec desKey=new SecretKeySpec(formatted_key,"DES");
        this.key=desKey;
    }
    // return the key
    public byte[] getKey(byte[] key){
        return this.key.getEncoded();
    }


    // try to encrypt
    @Override
    public byte[] encrypt(byte[] message) {
        try {
            desCipher.init(javax.crypto.Cipher.ENCRYPT_MODE,this.key);
            return desCipher.doFinal(message);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    // try to decrypt
    @Override
    public byte[] decrypt(byte[] message){
        try {
            desCipher.init(javax.crypto.Cipher.DECRYPT_MODE,this.key);
            return desCipher.doFinal(message);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    static public void main(String[] args) {

        DES des=new DES("sudip bhattarai".getBytes());

        byte[] crypted=des.encrypt("Hello World".getBytes());

        System.out.println(new String(crypted));

        byte[] decrypted=des.decrypt(crypted);

        System.out.println(new String(decrypted));


    }

}
