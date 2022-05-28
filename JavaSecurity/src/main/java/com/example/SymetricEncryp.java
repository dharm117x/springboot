package com.example;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymetricEncryp {
	
	private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
//	static Key key = new SecretKeySpec("12345678123456781234567812345678".getBytes(), "AES");
	
	public static String encrypt(String data, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
		byte[] IV = new byte[16];
		SecureRandom random = new SecureRandom();
//		random.nextBytes(IV);
		IvParameterSpec spec = new IvParameterSpec(IV);
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
		
		byte[] doFinal = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(doFinal);
		
	}
	
	public static String decrypt(String data, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
		byte[] IV = new byte[16];
		SecureRandom random = new SecureRandom();
//		random.nextBytes(IV);
		IvParameterSpec spec = new IvParameterSpec(IV);
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
		byte[] decode = Base64.getDecoder().decode(data.getBytes());
		byte[] doFinal = cipher.doFinal(decode);
		return new String(doFinal);
		
	}
	
	public static Key getAESKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	}
}
