package com.akanbiad.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.akanbiad.beans.Lawyer;

@Controller
public class LawyerController {
	
	private final String REST_URL = "http://localhost:8085/lawyers";
	
	@GetMapping("/")
	public String getHome() {
		return "home.html";
	}
	
	
	@GetMapping("/viewLawyers")
	public String viewLawyers(Model model, RestTemplate restTemplate) {
		
		ResponseEntity<Lawyer[]> responseEntity = restTemplate.getForEntity(REST_URL, Lawyer[].class);
		model.addAttribute("lawyers", responseEntity.getBody());
		return "viewLawyers.html";
	}
	

	@GetMapping("/addLawyer")
	public String addLawyer() {
		return "addLawyer.html";
	}
	
	
	@PostMapping("/addLawyer")
	public String processAddLawyer(@RequestParam String name, RestTemplate restTemplate) {
		Lawyer lawyer = Lawyer.builder().name(name).build();
		
		
		
		restTemplate.postForLocation(REST_URL, lawyer);
		return "redirect:/viewLawyers";
	}
	
	
	

}
