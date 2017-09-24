package com.twofactor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.twofactor.security.Cifrador;
import com.twofactor.security.SHA256;
import com.twofactor.service.Registry;



@Controller
@RequestMapping("/registry")
public class RegistryController {
	
	
	private static final  Log LOG=LogFactory.getLog(RegistryController.class);
	
	@Autowired
	private Registry registry;
	
	
	
	@PostMapping("/onAuth/{id}")
	public @ResponseBody String onAuth2(@PathVariable("id") String id)
	{
		Gson gson=new Gson();
		LOG.info("ON AUTH FOR USER WITH ID: "+id);
		
		try {
			registry.onAuth(id);
			return gson.toJson("OK");
		} catch (Exception e) {
			
			return gson.toJson("NOK");

		}
	}
	
	@DeleteMapping("/offAuth/{id}")
	public @ResponseBody String offAuth2(@PathVariable("id") String id)
	{
		Gson gson=new Gson();
		LOG.info("OFF AUTH FOR USER WITH ID: "+id);	
		try {
			registry.offAuth(id);
			return gson.toJson("OK");
		} catch (Exception e) {
			
			return gson.toJson("NOK");
		}
		
	}
	
	@GetMapping("/qr/{id}")
	public @ResponseBody String sendQR(@PathVariable("id")String id)
	{
		Gson gson=new Gson();
		LOG.info("Request QR: "+id);	
		try {
			
			return gson.toJson(registry.generateQr(id));
		} catch (Exception e) {
			
			return gson.toJson("NOK");
		}
	}
	
	
	
	

}
