package np.blockchain.session2;
import com.sun.javafx.runtime.VersionInfo;


public class RC4 {
	int[] seed;
	// static variables for RNG function
	int index_i=0;
	int index_j=0;
	
	//to use different seed_length.
	public RC4(int seed_length){
		seed=new int[seed_length];
	}
	public RC4(){
		seed=new int[256];
	}
	
	public void setKey(int[] key){	
		int i=0,j=0;
		do{
			seed[i]=i;
		}while(++i < seed.length);
		
		for(i=0;i<seed.length;i++){
			j += j+seed[i]+key[i%key.length];
			j %= seed.length;	
			//swap
			int tmp=seed[i];
			seed[i]=seed[j];
			seed[j]=seed[i];
		}	
		index_i=0;index_j=0;
	}
	public void setKey(String s){
		int[] keys=s.chars().toArray();
		setKey(keys);
	}
	
	/**
	 * 
	 * @return next pseudo random number for 'xor'ing.
	 */
	private int nextRandomNumber(){
		
		index_i++;
		index_i%=seed.length;
		
		index_j+=seed[index_i];
		index_j%=seed.length;
		
		int tmp=seed[index_i];
		seed[index_i]=seed[index_j];
		seed[index_j]=tmp;
		return seed[(seed[index_i]+seed[index_j])%seed.length];
	}
	
	//function for both encrypting and decryptng
	
	public int crypt(int n){
		return n+nextRandomNumber();
	}
	public char crypt(char c){
		return (char)((int)((char)nextRandomNumber())^ (int)c );
	}
	public String crypt(String s){	
		char[] ret=s.toCharArray();
		
		for(int i=0;i<s.length();i++){
			ret[i]=crypt(ret[i]);
		}
		return new String(ret);
	}
	
	static public void main(String[] args){
		
		RC4 rc4=new RC4();
		
		rc4.setKey("system");
		String input="RC4 Encryption is amazing";
		String encrypted=rc4.crypt(input);
		System.out.println("Input string :"+input);
		System.out.println("Encrypted string :"+encrypted);
		
		
		rc4.setKey("system"); // the same class can be used to decrypt
		
		String decrypted=rc4.crypt(encrypted);
		
		
		System.out.println("Decrypted string :"+decrypted);
				
	}
}
