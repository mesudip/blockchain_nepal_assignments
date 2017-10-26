package np.blockchain.session1;
import java.util.Enumeration;
import java.util.Hashtable;
public class MonoAlphabeticCipher extends Cipher {
    Hashtable<Byte,Byte> encryptiontable;
    Hashtable<Byte,Byte> decryptiontable;
    public MonoAlphabeticCipher(Hashtable<Byte,Byte> hashtable){

        encryptiontable=hashtable;
        decryptiontable = new Hashtable<>();
        Enumeration<Byte> iterator=encryptiontable.keys();

        // create the decryption table reversing the encryption table.
        while(iterator.hasMoreElements()){
            Byte value=iterator.nextElement();
            decryptiontable.put(encryptiontable.get(value),value);
        }

    }

    @Override
    public byte[] encrypt(byte[] data) {

        byte[] encrypted =new byte[data.length];
        for(int i=0;i<encrypted.length;i++){
            encrypted[i]=encryptiontable.get(encrypted[i]);
        }
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] data) {

        byte[] decrypted =new byte[data.length];
        for(int i=0;i<decrypted.length;i++){
            decrypted[i]=decryptiontable.get(decrypted[i]);
        }
        return new byte[0];
    }

    @Override
    public void setKey(byte[] data) {

    }

}
