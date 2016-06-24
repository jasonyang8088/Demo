package com.zxxk.zss.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController    //same as@Rest  @ResponseBody
public class MainController {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

}
