package com.twofactor.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;



@Service("sha256")
public class SHA256 {
	
	
	
	public String getHash(String id)throws Exception
	{
		String res="";
		MessageDigest digest;
		
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(id.getBytes(StandardCharsets.UTF_8));
			res=new String(hash, StandardCharsets.UTF_8);
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<hash.length;i++){
			    sb.append(String.format("%02x", hash[i]));
			}
			res=sb.toString();
		
		return res;
	}

}
