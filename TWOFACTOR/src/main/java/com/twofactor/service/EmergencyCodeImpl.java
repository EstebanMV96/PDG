package com.twofactor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
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



	@Override
	public List<Integer> generateNewCodes(String idUser) throws Exception {
		
		String has = encrypt.getHash(idUser);
		LOG.info("Method generateNewCodes: User  "+idUser+" need create new codes");
		User find=searchUser(has);
		if(find!=null)
		{
			List<ScratchCode> codesOld=dataBaseC.findAllByUser(find);
			
			if(codesOld.isEmpty())
			{
				List<Integer> l=generateCodes(new ArrayList<Integer>());
				for (int i = 0; i < l.size(); i++) {
					
					dataBaseC.save(new ScratchCode(new Date().getTime(), find, l.get(i), false));
				}
				return l;
			}else
			{
				List<ScratchCode> codesUser=dataBaseC.findAllByUser(find);
				List <Integer> c=new ArrayList<Integer>();
				for (int i = 0; i < codesUser.size(); i++) {
					
					c.add(codesUser.get(i).getCode());
					codesUser.get(i).setUsed(true);
					dataBaseC.save(codesUser.get(i));
				}
				List<Integer> codesFull=generateCodes(c);
				return codesFull;
			}
			
			
			
		}else
		{
			throw new Exception("User not exist");
		}
		
		
		
	}
	
	
	private List<Integer> generateCodes(List<Integer> olds)
	{
		List<Integer> res=new ArrayList<Integer>();
		int num = Integer.parseInt(RandomStringUtils.randomNumeric(8));
		
		for (int i = 0; i < 5; i++) {
			
			if(!res.contains(num)&&!olds.contains(num)&&num+"".length()==8)
				res.add(num);
			else
				i--;
			
			num=Integer.parseInt(RandomStringUtils.randomNumeric(8));
		}
		
		
		
		return res;
	}
	
	
	
	

}
