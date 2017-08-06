package com.twofactor.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twofactor.logic.GoogleAPI;



@Controller
public class AppController {
	
	@Autowired
	private GoogleAPI api;

	@RequestMapping(value = "/pdg1", method = RequestMethod.GET)
	public @ResponseBody String prueba()
	{
		
		return api.generarNuevaSemilla();
	}
	
	

}
