package com.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	
	@RequestMapping(path="/messages",method=RequestMethod.GET)
	public String message() {
		return "welcome to REST";
	}
	
	@GetMapping("/messages1")
	public String message1() {
		return "welcome to REST New";
	}
	
	@GetMapping("/messages/{empname}")
	public String message3(@PathVariable("empname") String empName) {
		return "welcome to REST - "+ empName;
	}
	
	@GetMapping("/messages/{empname}/{empId}")
	public String message4(@PathVariable("empname") String empName, @PathVariable("empId") int empId) {
		return "welcome to REST - "+ empName + " id : " + empId;
	}

}
