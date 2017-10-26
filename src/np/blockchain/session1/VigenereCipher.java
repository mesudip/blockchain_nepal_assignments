package np.blockchain.session1;


import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.InputMismatchException;

public class PolyAlphabeticCipher extends Cipher {

    Hashtable<AbstractMap.SimpleEntry<Byte, Byte>, Byte> encryptiontable;
    Hashtable<AbstractMap.SimpleEntry<Byte, Byte>, Byte> decryptiontable;
    byte[] key;

    /**
     *
     * @param hashtable <Byte,Byte> ==> Byte.
     *                   key   text     cipher
     *
     */

    public PolyAlphabeticCipher(Hashtable<AbstractMap.SimpleEntry<Byte, Byte>, Byte> hashtable) {

        encryptiontable = hashtable;
        decryptiontable = new Hashtable<>();
        Enumeration<AbstractMap.SimpleEntry<Byte, Byte>> iterator = encryptiontable.keys();

        // create the decryption table reversing the encryption table.
        while (iterator.hasMoreElements()) {
            AbstractMap.SimpleEntry<Byte,Byte> key = iterator.nextElement();
            decryptiontable.put(new AbstractMap.SimpleEntry<Byte, Byte>(key.getKey(),encryptiontable.get(key)), key.getValue());
        }

    }

    public void setKey(byte[] key){
        this.key=key;
    }
    @Override
    public byte[] encrypt(byte[] data) {

        if (data.length!=key.length)
            throw new InputMismatchException("Key and Data length missmatch");

        byte[] encrypted = new byte[data.length];
        for (int i = 0; i < encrypted.length; i++) {
            encrypted[i] = encryptiontable.get(new AbstractMap.SimpleEntry<Byte, Byte>(key[i],data[i]));
        }
        return encrypted;
    }

    @Override
    public byte[] decrypt(byte[] data) throws InputMismatchException{

        if (data.length!=key.length)
            throw new InputMismatchException("Key and Data length missmatch");

        byte[] decrypted = new byte[data.length];
        for (int i = 0; i < decrypted.length; i++) {
            decrypted[i] = decryptiontable.get(new AbstractMap.SimpleEntry<Byte, Byte>(key[i],data[i]));
        }
        return decrypted;
    }
}
