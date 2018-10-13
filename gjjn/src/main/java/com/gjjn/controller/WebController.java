package com.gjjn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
	
	@RequestMapping(value="/game",method=RequestMethod.GET)
	public String game() {
		return "game";
	}
	
	@RequestMapping(value="/alljn",method=RequestMethod.GET)
	public String alljn() {
		return "alljn";
	}
	
	@RequestMapping(value="/ad",method=RequestMethod.GET)
	public String ad() {
		return "ad";
	}
}
