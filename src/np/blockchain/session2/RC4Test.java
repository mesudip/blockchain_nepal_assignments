package np.blockchain.session2;
import static org.junit.Assert.*;

import org.junit.Test;
import np.blockchain.session2.RC4;
public class RC4Test {
	String []test_strings={"sudip",
			"RC4 is fun",
			"computer is not intelligent"
			
	};
	String []key_strings={
			"sample key",
			"next key",
			"my secret key",
	};
	
	
	

	@Test
	public void test() {
		RC4 rc4= new RC4();

		// encrypt each string with each key and test for equality after decrypting
		for(String key:key_strings){
            rc4.setKey(key);
			for(String test:test_strings){

				String encrypted=rc4.crypt(test);
				String decrypted=rc4.crypt(encrypted);

				if(!decrypted.equals(test)){
					System.out.println("\nKey    :"+key);
					System.out.println("Message:"+test);
					System.out.println("Encrypted:"+encrypted);
					System.out.println("Decrypted:"+decrypted);
					fail("Test string and Decrypted string match failed");
				}
			}
		}
		
	}
	
}
