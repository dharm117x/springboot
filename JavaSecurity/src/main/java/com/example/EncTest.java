package com.example;

import java.security.Key;

public class EncTest {
	public static void main(String[] args) {
		try {
			String data = "This is a plain text which need to be encrypted by Java AES 256 Algorithm in CBC Mode";
			Key aesKey = SymetricEncryp.getAESKey();
			String encrypt = SymetricEncryp.encrypt(data, aesKey);
			System.out.println(encrypt);
			String decrypt = SymetricEncryp.decrypt(encrypt, aesKey);
			System.out.println(decrypt);
			
			String sign = ASymetricEncryp.sign(data, ASymetricEncryp.readPrivateKey());
			System.out.println("Signature   :"+ sign);
			boolean verify = ASymetricEncryp.verify(data, sign, ASymetricEncryp.readPublicKey());
			System.out.println("Verify : " + verify);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
