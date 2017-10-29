package np.blockchain.session6;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import np.blockchain.session4.AES;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * After writing if-else everywhere,
 * I realized that making separate class for Hmac and cmac would have been a lot
 * better decision
 */
public class MACCipher extends np.blockchain.session1.Cipher{

    public enum Type{
        HMAC,CBCMAC;
    }
    static public class Instance<Type>{
        private Type t;

    }
    Type type;
    // Key may be KeyParameter or SecretKeySpec. so make it Object instead
    Object key;

    // the mac may be javax.crypto.Mac or org.bouncycastle.crypto.Mac
    // so let's make it object :D
    Object mac;

    private void init()throws InitializationError{
        if(this.type==Type.HMAC){
            try {
                this.mac=javax.crypto.Mac.getInstance("HmacSha1");
            } catch (NoSuchAlgorithmException e) {
                throw new InitializationError();
            }

        }
        else if(this.type==Type.CBCMAC){
            BlockCipher cipher = new AESEngine();
            Mac mac=new CBCBlockCipherMac(cipher);
            this.mac=mac;
        }
    }
    public MACCipher(Type type) throws InitializationError {
        this.type=type;
        this.init();
    }

    public MACCipher() throws InitializationError {
        this.type=Type.HMAC;
        this.init();
    }
    private javax.crypto.Mac hmac(){
       return (javax.crypto.Mac)mac;
    }
    private org.bouncycastle.crypto.Mac cbcmac(){
        return (org.bouncycastle.crypto.Mac) mac;
    }
    public void setKey(byte[] key) throws InvalidKeyException {
        if(this.type==Type.HMAC) {
            hmac().init(new SecretKeySpec(key, "HmacSha1"));
        }
        else if(this.type==Type.CBCMAC){

            cbcmac().init(new KeyParameter(key));
        }
    }

    // to just get mac data.
    public byte[] getMac(byte[] data){
        if(type==Type.HMAC)
            return hmac().doFinal(data);
        else if (type==Type.CBCMAC) {
            byte[] macBytes = new byte[8];
            cbcmac().update(data, 0, data.length);
            cbcmac().doFinal(macBytes,0);
            return macBytes;
        }
        //this won't happen
        return new byte[0];
    }


    /**
     * computes mac and appends it to the start of data along with it's length
     */
    @Override
    public byte[] encrypt(byte[] data)  {
        if(type==Type.HMAC) {
            byte[] mac = hmac().doFinal(data);
            byte[] enc=new byte[mac.length+data.length+1];
            // the first byte of encrypted data represents the length of the mac data.
            enc[0]=(byte)mac.length;
            System.arraycopy(mac,0,enc,1,mac.length);
            System.arraycopy(data,0,enc,mac.length+1,data.length);

            return enc;
        }
        else if (type==Type.CBCMAC) {
            byte[] enc =new byte[data.length+9];
            enc[0]=(byte)8;
            cbcmac().update(data, 0, data.length);
            cbcmac().doFinal(enc,1);
            System.arraycopy(data,0,enc,9,data.length);
            return enc;
        }
        //this won't ever happen
        return new byte[0];
    }

    @Override
    /**
     * What decrypt does is read the mad data appended at the begining of data
     * validate the data with mac then return the data part only
     */
    public byte[] decrypt(byte[] data) throws DecryptionError {

        // the length of mac plus the length byte
        int maclength=(int)data[0]+1;

        // not the best way to do it but let's try this
        byte[] main_data=new byte[data.length-maclength];
        System.arraycopy(data,maclength,main_data,0,data.length-maclength);

        // the first byte is the length of mac

        // macbyte
        if(type==Type.HMAC) {
            byte[] mac = hmac().doFinal(main_data);

            for(int i=0;i<mac.length;){
                if(mac[i]!=data[i+1]){
                    throw new DecryptionError();
                }
            }
            // return the main part of data
            return main_data;
        }
        else if (type==Type.CBCMAC) {

            byte[] mac=new byte[maclength];
            cbcmac().update(mac, 1,maclength-1);
            cbcmac().doFinal(data,maclength);
            for(int i=0;i<mac.length;){
                if(mac[i]!=data[i+1]){
                    throw new DecryptionError();
                }
            }
        }
        //this won't ever happen
        return main_data;
    }
}
