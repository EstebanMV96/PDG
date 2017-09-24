package com.twofactor.service;

public interface Login {
	
	
	public abstract boolean codeIsOk(String idUser,int code) throws Exception;
	
	
	

}
