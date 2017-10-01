package com.twofactor.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twofactor.model.ScratchCode;
import com.twofactor.model.User;
import com.twofactor.repository.ScratchCodeJPARepository;
import com.twofactor.repository.UserJPARepository;
import com.twofactor.security.Cifrador;
import com.twofactor.security.SHA256;
@Service("emergencyCodes")
public class EmergencyCodeImpl implements EmergencyCode{
	
	
	@Autowired
	private ScratchCodeJPARepository dataBaseC;
	
	@Autowired
	private SHA256 encrypt;

	@Autowired
	private UserJPARepository dataBase;
	
	private static final Log LOG = LogFactory.getLog(EmergencyCodeImpl.class);


	@Override
	public List<Integer> getEmergencyCodes(String idUser)throws Exception {
		String has = encrypt.getHash(idUser);
		LOG.info("Method getEmergencyCodes: User  "+idUser+"need emergency codes");
		User find=searchUser(has);
		if(find!=null)
		{
			List<Integer> fullCodes=new ArrayList<Integer>();
			List<ScratchCode> codesUser=dataBaseC.findAllByUser(find);
			for (int i = 0; i < codesUser.size(); i++) {
				
				if(!codesUser.get(i).isUsed())
				fullCodes.add(codesUser.get(i).getCode());
			}
			return fullCodes;
		}else
			throw new Exception("User dont exist");
		
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
