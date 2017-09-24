package com.twofactor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.twofactor.service.Login;

@Controller
@RequestMapping("/twoFactor")
public class LoginController {
	
	private static final  Log LOG=LogFactory.getLog(LoginController.class);
	@Autowired
	private Login login;

	@GetMapping("/{id}/{code}")
	public @ResponseBody String login(@PathVariable("id") String id,@PathVariable("code") String code)
	{
		Gson gson=new Gson();
		int co=Integer.parseInt(code);
		LOG.info("Login: id: "+id+" code: "+code);
		try {
			
			if(login.codeIsOk(id, co))
			{
				LOG.info("Login is OK for user "+id);
				return gson.toJson("OK");
			}else
			{	
				return gson.toJson("NOK");

			}
			
		} catch (Exception e) {
			
			LOG.error("Problem when generate hash for user "+id);
			return gson.toJson("NOK");

		}
		
		

	}
	

}
