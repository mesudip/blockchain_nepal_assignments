package np.blockchain.session6;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import np.blockchain.session4.AES;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MACCipher extends np.blockchain.session1.Cipher{
    AES aes= new AES();
    @Override
    public byte[] encrypt(byte[] data) throws InvalidKeyException {
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return new byte[0];
    }
    public static void main(String args[]) throws NoSuchAlgorithmException, InvalidKeyException {
        // Message to sign
        String message = "A quick brown fox jumps over the lazy dog why is i tnot working";
    // Key used for signing
        String key = "Some strong password";
// HMAC algorithm
        String HMAC_ALGORITHM = "HmacSha1";
// Generate SecretKey from the user key
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_ALGORITHM);
// Get MAC instance
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
// Initialize the mac with key
        mac.init(signingKey);
// Compute MAC
        byte[] macBytes = mac.doFinal(message.getBytes());
// show MAC value
        String macString = new String(Base64.encode(macBytes));
        System.out.println("MAC:" + macString);
    }
}
