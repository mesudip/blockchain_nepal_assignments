package np.blockchain.session4;

import np.blockchain.session1.Cipher;
import org.junit.Test;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.fail;

public class AESTest {
    // helper function to generate random byte arrays
    byte[][] randomBytes(int size){
        Random random=new Random();
        byte[][] randoms=new byte[size][100];
        for(int i=0;i<randoms.length;i++){
            random.nextBytes(randoms[i]);
        }
        return randoms;
    }
    // helper function to compare two byte arrays
    boolean equalArrays(byte[] a, byte[] b){
        if (a.length!=b.length){
            return false;
        }
        for(int i=0;i<a.length;i++){
            if (a[i]!=b[i]){
                return false;
            }

        }
        return true;
    }


    @Test
    public void test() throws NoSuchAlgorithmException, NoSuchPaddingException {
        byte[][] set=randomBytes(1000);
        AES aes=new AES(AES.Size.aes128, AES.Mode.OFB);
        // for each byte array in set, make it as key
        // and try encrypting/decripting all other byte     array with it
        for (int i=0;i<set.length;i++){
            aes.setKey(set[i]);
            for (int j=0;j<set.length;j++){

                try {
                    byte[] original=set[j];
                    byte[] encrypted = aes.encrypt(original);
                    byte[] decrypted = aes.decrypt(encrypted);
                    if(!equalArrays(original,decrypted)){
                        System.out.print("Key :"); System.out.println(Arrays.toString(set[i]));
                        System.out.print("Message: ");System.out.println(Arrays.toString(original));
                        System.out.print("EncMessage: ");System.out.println(Arrays.toString(encrypted));
                        System.out.print("DecMessage: ");System.out.println(Arrays.toString(decrypted));
                        fail("Original and Decrypted message mismatch.");
                    }
                }catch (Cipher.CryptError e){
                    System.err.println("Exception in encryption/decryption");
                    fail("Test failed");
                }

            }
        }
    }
}
