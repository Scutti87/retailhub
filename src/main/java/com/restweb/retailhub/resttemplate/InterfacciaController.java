package com.restweb.retailhub.resttemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterfacciaController {
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

}