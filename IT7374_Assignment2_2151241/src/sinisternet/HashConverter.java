package sinisternet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import org.apache.commons.codec.digest.DigestUtils;


public class HashConverter {
	
	public String getHash(String password, String algorithm) {

		try {
			byte[] hash = new byte[64];

			MessageDigest message = MessageDigest.getInstance(algorithm);

			message.update(password.getBytes("iso-8859-1"), 0, password.length());
			hash = message.digest();
			
			if(algorithm.equals("MD5")){
				//return DigestUtils.md5Hex(hash);
				return("pass");
			} else if(algorithm.equals("SHA-1")){
				//return DigestUtils.sha1Hex(hash);
				return("pass");
			} else if(algorithm.equals("SHA-256")){
				//return DigestUtils.sha256Hex(hash);
				return("pass");
			} else {
				//return DigestUtils.sha512Hex(hash);
				return("pass");
			}
		} catch (NoSuchAlgorithmException e) {
			return "Invalid algorithm";
		} catch (Exception e) {
			return "Hash Converter Exception";
		}
	}
}
