package com.mochen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	@GetMapping("/ad")
	public String ad() {
		return "ad";
	}

	@GetMapping("/")
	public String index() {
		return "webgame";
	}

	@GetMapping("/game")
	public String game() {
		return "game";
	}
}
