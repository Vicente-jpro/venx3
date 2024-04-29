package com.adicionatec.venx3.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemsController {
	
	@GetMapping
	public String hello() {
		return "Hello items controller";
	}

}
