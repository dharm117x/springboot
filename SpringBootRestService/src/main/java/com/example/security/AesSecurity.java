package com.example.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.springframework.stereotype.Component;

@Component
public class AesSecurity {

	private static final String UTF_8 = "UTF-8";
	private static final String AES_GCM_CMP_NO_PADDING = "AES/GCM/NoPadding";

	public String encrypt(String data, Key key) {
		try {
			Cipher cipher = Cipher.getInstance(AES_GCM_CMP_NO_PADDING);
			byte[] src = new byte[16];
			GCMParameterSpec spec = new GCMParameterSpec(128, src);
			cipher.init(Cipher.ENCRYPT_MODE, key, spec);
			byte[] doFinal = cipher.doFinal(data.getBytes(UTF_8));
			return Base64.getEncoder().encodeToString(doFinal);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String data, Key key) {
		try {
			Cipher cipher = Cipher.getInstance(AES_GCM_CMP_NO_PADDING);
			byte[] src = new byte[16];
			GCMParameterSpec spec = new GCMParameterSpec(128, src);
			cipher.init(Cipher.DECRYPT_MODE, key, spec);
			byte[] decode = Base64.getDecoder().decode(data.getBytes(UTF_8));
			byte[] doFinal = cipher.doFinal(decode);
			return new String(doFinal);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Key getAESKey()  {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(256);
			SecretKey secretKey = generator.generateKey();
			return secretKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
