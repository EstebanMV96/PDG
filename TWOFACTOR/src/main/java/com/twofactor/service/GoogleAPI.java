package com.twofactor.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
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
	
	
	public boolean login(int codeUser,String seed)
	{
		boolean r=false;
		int[] codes=codesOk(seed);
		for (int i = 0; i < codes.length&&!r; i++) {
			if(codeUser==codes[i])
				r=true;
			
		}
		
		return r;
	}
	
	private int[] codesOk(String seed)
	{
		int[] res=new int[3];
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		Calendar c1=Calendar.getInstance();
		Calendar c2=Calendar.getInstance();
		Calendar c3=Calendar.getInstance();
		Date timeA=c1.getTime();
		c2.add(Calendar.SECOND, 30);
		Date timeA1=c2.getTime();
		c3.add(Calendar.SECOND,-30);
		Date timeA2=c3.getTime();
		int codeA=gAuth.getTotpPassword(seed,timeA.getTime());
		int codeA1=gAuth.getTotpPassword(seed, timeA1.getTime());
		int codeA2=gAuth.getTotpPassword(seed, timeA2.getTime());
		res[0]=codeA;
		res[1]=codeA1;
		res[2]=codeA2;
		return res;
		
	}
	
	public static String getQRBarcodeURL(String user,String host,String secret)
	 {
	
	  String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
	
	  return String.format(format, user, host, secret);
	
	}
	
	
	
	
	
	
	

}
