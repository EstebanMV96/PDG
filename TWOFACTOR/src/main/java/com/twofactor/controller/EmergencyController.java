package com.twofactor.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.twofactor.service.EmergencyCode;

@Controller
public class EmergencyController {
	
	private static final  Log LOG=LogFactory.getLog(EmergencyController.class);

	@Autowired
	private EmergencyCode serviceCodes;
	
	@GetMapping("emergencyCodes/{id}")
	public @ResponseBody String login(@PathVariable("id") String id)
	{
		Gson gson=new Gson();
		try {
			LOG.info("User "+id+" requested emergency codes");
			List<Integer> codes=serviceCodes.getEmergencyCodes(id);
			return gson.toJson(codes);
		} catch (Exception e) {
			LOG.error("Problems with user "+id+" PROBLEM:"+e.getMessage());
			return gson.toJson("NOK");
		}
		
		
	}

}
