package com.twofactor.service;

import com.twofactor.model.User;

public interface Registry {
	
	
	public abstract void onAuth(String user)throws Exception;
	
	
	public abstract void offAuth(String user) throws Exception;


	public abstract String generateQr(String idUser) throws Exception;

}
