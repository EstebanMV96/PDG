package com.twofactor.service;

import java.util.List;

import com.twofactor.model.ScratchCode;
import com.twofactor.model.User;

public interface EmergencyCode {
	
	
	public abstract List<Integer> getEmergencyCodes(String idUser)throws Exception;
	
	
	public abstract List<Integer> generateNewCodes(String idUser)throws Exception;

}
