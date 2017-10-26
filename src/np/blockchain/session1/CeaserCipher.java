package np.blockchain.session1;

import np.blockchain.session1.Cipher;

public class CeaserCipher extends Cipher{
    byte shift_value;

    CeaserCipher(int shift){
        shift=(byte)shift;
    }

    @Override
    public byte[] encrypt(byte[] data) {

        byte[] result=data.clone();

        for(int i=0;i<data.length;i++) {
            result[i] += shift_value;
        }
        return result;
    }

    @Override
    public byte[] decrypt(byte[] data) {

        byte[] result=data.clone();

        for(int i=0;i<data.length;i++){
            result[i]-= shift_value;
        }
        return result;
    }

}
