package com.twofactor.security;

import java.util.Base64;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service("cifrador")
public class Cifrador {

	private SecretKeySpec key;

	public String cifrar(String nomArch, String pass) throws Exception {

		key = new SecretKeySpec(pass.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64.getEncoder().encodeToString(cipher.doFinal(nomArch.getBytes("UTF-8")));

	}

	public String des(String nomArch, String pass) throws Exception {

		key = new SecretKeySpec(pass.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64.getDecoder().decode(nomArch)), "UTF-8");

	}

}
