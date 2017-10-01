package com.twofactor.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twofactor.model.ScratchCode;
import com.twofactor.model.User;
import com.twofactor.repository.ScratchCodeJPARepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;


@Service("GoogleAPI")
public class GoogleAPI {
	
	
	private static final Log LOG = LogFactory.getLog(GoogleAPI.class);

	
	@Autowired
	private ScratchCodeJPARepository database;

	
	public Object[] generarNuevaSemilla()
	{
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		GoogleAuthenticatorKey key=gAuth.createCredentials();
		List<Integer> fullCodes=key.getScratchCodes();
		String semilla=key.getKey();
		Object[] res=new Object[2];
		res[0]=semilla;
		res[1]=fullCodes;
		return res;
	}
	
	
	public boolean login(int codeUser,String seed,User idUser)
	{
		boolean r=false;
		int[] codes=codesOk(seed,idUser);
		for (int i = 0; i < codes.length&&!r; i++) {
			if(codeUser==codes[i])
			{
				
				if(i>=3)
				{
					LOG.info("User actually"+" used emergency code "+codeUser);
					ScratchCode used=database.findByUserAndCode(idUser, codeUser);
					if(!used.isUsed())
					{
						used.setUsed(true);
						database.save(used);
						r=true;
					}else
						break;
						
					
				}else
				{
					r=true;
				}
				
			}
			
			
			
		}
		
		return r;
	}
	
	private int[] codesOk(String seed,User idUser)
	{
		List<ScratchCode> codesF=database.findAllByUser(idUser);
		int[] res=new int[3+codesF.size()];
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
		for(int i=3, j=0;i<res.length;i++,j++)
		{
			res[i]=codesF.get(j).getCode();
		}
		return res;
		
	}
	
	public static String getQRBarcodeURL(String user,String host,String secret)
	 {
	
	  String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
	
	  return String.format(format, user, host, secret);
	
	}
	
	
	
	
	
	
	

}
