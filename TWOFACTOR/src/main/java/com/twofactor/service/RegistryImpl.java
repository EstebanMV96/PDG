package com.twofactor.service;

import static org.mockito.Matchers.endsWith;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.twofactor.model.User;
import com.twofactor.repository.UserJPARepository;
import com.twofactor.security.Cifrador;
import com.twofactor.security.SHA256;

@Service("registryService")
public class RegistryImpl implements Registry {

	private static final Log LOG = LogFactory.getLog(RegistryImpl.class);

	@Autowired
	@Qualifier("GoogleAPI")
	private GoogleAPI api;

	@Autowired
	private SHA256 encrypt;

	@Autowired
	private Cifrador cifra;

	@Autowired
	private UserJPARepository dataBase;

	@Override
	public void onAuth(String user) throws Exception {

		String has = encrypt.getHash(user);
		if (searchUser(has) == null) {
			String sal = RandomStringUtils.randomAlphanumeric(16);
			String fullId = has + sal;
			String seed = api.generarNuevaSemilla();
			String seedEncrypt = cifra.cifrar(seed, sal);
			User ne = new User(fullId, seedEncrypt);
			LOG.info("Method onAuth: add user " + ne.toString());
			dataBase.save(ne);
		} else {
			LOG.error("Method onAuth: failed add user " + user + " USER EXIST");
			throw new Exception("User exist");
		}

	}

	@Override
	public String generateQr(String idUser)throws Exception {
		
		User find=searchUser(encrypt.getHash(idUser));
		if(find!=null)
		{
			String seedC=find.getSeed();
			String pass=find.getId().substring(64).trim();
			String seedD=cifra.des(seedC, pass);
			LOG.info("Method generateQr: send link code QR for user "+idUser);
			return api.getQRBarcodeURL(idUser, "twofactor.com", seedD);
		}else
		{
			LOG.error("Method generateQR: user "+idUser+ " not exist.");
			throw new Exception("user not exist");
		}
		
		
		
		
	}

	@Override
	public void offAuth(String user) throws Exception {

		String has = encrypt.getHash(user);
		User find = searchUser(has);
		if (find != null) {
			dataBase.delete(find);
			LOG.info("Method offAuth: User " + user + " Deleted TWOFACTOR");
		} else {
			LOG.error("Method offAuth: User  " + user + " NOT EXIST");
			throw new Exception("User ");
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
