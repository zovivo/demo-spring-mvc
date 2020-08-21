package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = { "/page" })
	public String home() {
		return "../pages/home";
	}
	
	@RequestMapping(value = { "/page/room" })
	public String room() {
		return "../pages/room";
	}
}
