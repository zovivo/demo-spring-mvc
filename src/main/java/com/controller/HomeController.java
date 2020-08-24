package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = { "","/page", "/page/home"})
	public String home() {
		return "../pages/home";
	}
	
	@RequestMapping(value = { "/page/room" })
	public String room() {
		return "../pages/room";
	}
	
	@RequestMapping(value = { "/page/singleroom"})
	public String singleroom() {
		return "../pages/singleroom";
	}
	
	@RequestMapping(value = { "/page/blog"})
	public String blog() {
		return "../pages/blog";
	}
	
	@RequestMapping(value = { "/page/singleblog"})
	public String singleblog() {
		return "../pages/singleblog";
	}
	
	@RequestMapping(value = { "/page/about"})
	public String about() {
		return "../pages/about";
	}
	
	@RequestMapping(value = { "/page/contact"})
	public String contact() {
		return "../pages/contact";
	}
}
