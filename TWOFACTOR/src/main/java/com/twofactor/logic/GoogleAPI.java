package com.twofactor.logic;

import org.springframework.stereotype.Service;

import com.warrenstrange.googleauth.GoogleAuthenticator;


@Service("GoogleAPI")
public class GoogleAPI {

	
	public String generarNuevaSemilla()
	{
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		String semilla=gAuth.createCredentials().getKey();
		return semilla;
	}
	
	
	public boolean login(int codeUser,String idUser)
	{
		boolean correcto=false;
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		String semilla=""; //AQUÍ SE LLAMA A LA BASE DE DATOS PARA OBTENER LA SEMILLA DEL idUser
		int codeA=gAuth.getTotpPassword(semilla);
		if(codeA==codeUser)
			correcto=true;
		
		return correcto;
		
	}
	
	public static String getQRBarcodeURL(String user,String host,String secret)
	 {
	
	  String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
	
	  return String.format(format, user, host, secret);
	
	}
	
	
	
	
	
	
	

}
