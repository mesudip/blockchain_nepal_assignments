package np.blockchain.session1;


// symmetric Cipher class will have a crypt funciton to both encrypt and
// decrypt data. The subclass can override the crypt method and the  decrypt/encrypt will also work
public abstract class SymmetricCipher extends Cipher {

    public abstract byte[] crypt(byte[] data);

    @Override
    public byte[] decrypt(byte[] data) {
        return crypt(data);
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return crypt(data);
    }
}
