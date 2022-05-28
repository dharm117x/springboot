package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class ASymetricEncryp {

	public static String encrypt(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, readPublicKey());

		byte[] doFinal = cipher.doFinal(data.getBytes());

		return Base64.getEncoder().encodeToString(doFinal);

	}

	public static String decrypt(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
		cipher.init(Cipher.DECRYPT_MODE, readPrivateKey());

		byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(data.getBytes()));
		return new String(doFinal);

	}

	public static RSAPrivateKey readPrivateKey() throws Exception {
		File file = new File("src/main/resources/certificate/localhostPrivate.pem");
		KeyFactory factory = KeyFactory.getInstance("RSA");
		try (FileReader keyReader = new FileReader(file); PemReader pemReader = new PemReader(keyReader)) {
			PemObject pemObject = pemReader.readPemObject();
			byte[] content = pemObject.getContent();
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
			return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
		}
	}

	public static RSAPublicKey readPublicKey() throws Exception {
		File file = new File("src/main/resources/certificate/localhostPublic.pem");
		KeyFactory factory = KeyFactory.getInstance("RSA");
		Provider provider = factory.getProvider();
		System.out.println(provider);
		try (FileReader keyReader = new FileReader(file); PemReader pemReader = new PemReader(keyReader)) {
			PemObject pemObject = pemReader.readPemObject();
			byte[] content = pemObject.getContent();
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
			return (RSAPublicKey) factory.generatePublic(pubKeySpec);
		}
	}

	public static String sign(String plainText, PrivateKey privateKey) throws Exception {
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(privateKey);
		privateSignature.update(plainText.getBytes("UTF-8"));

		byte[] signature = privateSignature.sign();

		return Base64.getEncoder().encodeToString(signature);
	}

	public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(publicKey);
		publicSignature.update(plainText.getBytes("UTF-8"));

		byte[] signatureBytes = Base64.getDecoder().decode(signature);

		return publicSignature.verify(signatureBytes);
	}

	private static Key getPublicKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

		String publicKeyPEM = key.replace("-----BEGIN PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "")
				.replace("-----END PUBLIC KEY-----", "");

		byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	private static Key getPrivateKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
		String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----", "").replaceAll(System.lineSeparator(), "")
				.replace("-----END PRIVATE KEY-----", "");

		byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

}
