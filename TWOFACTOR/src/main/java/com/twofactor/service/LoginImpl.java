package com.twofactor.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.twofactor.model.User;
import com.twofactor.repository.UserJPARepository;
import com.twofactor.security.Cifrador;
import com.twofactor.security.SHA256;

@Service("login")
public class LoginImpl implements Login{
	
	
	@Autowired
	@Qualifier("GoogleAPI")
	private GoogleAPI api;

	@Autowired
	private SHA256 encrypt;

	@Autowired
	private Cifrador cifra;

	@Autowired
	private UserJPARepository dataBase;
	
	private static final Log LOG = LogFactory.getLog(LoginImpl.class);


	@Override
	public boolean codeIsOk(String idUser, int code) throws Exception {
		
		String has = encrypt.getHash(idUser);
		LOG.info("Method codeIsOk: "+idUser+" Code: "+code);
		User find=searchUser(has);
		if(find==null)
			throw new Exception("User not exist");
		else
		{
			String seed=cifra.des(find.getSeed(), find.getId().substring(64));
			return api.login(code, seed);
		}
	}
	
	
	private User searchUser(String user) {
		User res = null;

		List<User> users = (List<User>) dataBase.findAll();

		for (int i = 0; i < users.size() && res == null; i++) {
			String idActual = users.get(i).getId().substring(0, 64);
			if (idActual.equalsIgnoreCase(user))
				res = users.get(i);

		}

		return res;

	}
	
	
	

}
