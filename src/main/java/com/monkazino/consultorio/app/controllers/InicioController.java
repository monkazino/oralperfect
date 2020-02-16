package com.monkazino.consultorio.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InicioController {

	@RequestMapping(value = {"/inicio", "/"}, method = RequestMethod.GET)
	public String inicio(Model model, HttpServletRequest request) {
		return "inicio";
	}
}
