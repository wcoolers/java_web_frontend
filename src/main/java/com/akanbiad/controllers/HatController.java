package com.akanbiad.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.akanbiad.beans.Hat;
import com.akanbiad.beans.Lawyer;

@Controller
public class HatController {
	
	private final String HATS_REST_URL = "http://localhost:8085/hats";
	private final String LAWYERS_REST_URL = "http://localhost:8085/lawyers";
	

	
	@GetMapping("/viewHats")
	public String viewHat(Model model, RestTemplate restTemplate) {
		ResponseEntity<Hat[]> responseEntity = restTemplate.getForEntity(HATS_REST_URL, Hat[].class);
		model.addAttribute("hats", responseEntity.getBody());
		return "viewHats.html";
	}
	

    @GetMapping("/addHat")
        public String addHat(Model model , RestTemplate restTemplate) {
    	
    	model.addAttribute("hat" , new Hat());
    	
    	
    	ResponseEntity<Lawyer[]> responseEntity = restTemplate.getForEntity(LAWYERS_REST_URL, Lawyer[].class);
    	
    	model.addAttribute("lawyers", responseEntity.getBody());
    	
    	return "addHat.html";
    }
    

    
    @PostMapping("/addHat")
	public String processAddHat(@ModelAttribute Hat hat, @RequestParam String name, @RequestParam double price,
			@RequestParam int quantity, @RequestParam String employeeName, RestTemplate restTemplate) {

		hat.setName(name);
		hat.setPrice(price);
		hat.setQuantity(quantity);
		hat.setEmployeeName(employeeName);
		
		// Retrieve the Lawyer object from the database
//		FOR SOME REASON I COULDNT GET THE LAWYER FROM THE BACKEND USING HIS?HER NAME
//		ResponseEntity<Lawyer> responseEntity = restTemplate.getForEntity(LAWYERS_REST_URL, Lawyer.class);
//
//	    if (lawyer != null) {
//	        hat.setLawyer(lawyer);
//	    }
//		
		

		restTemplate.postForLocation(HATS_REST_URL, hat);

		return "redirect:/viewHats";
	}
    
    
    
    
    
    

	@GetMapping("/deleteHat/{id}")
	public String deleteHat(@PathVariable Long id, RestTemplate restTemplate) {
		restTemplate.delete(HATS_REST_URL + "/" + id);
		return "redirect:/viewHats";
	}
	

	
	
	@GetMapping("/purchaseHat/{id}")
	public String purchaseHat(@PathVariable Long id, RestTemplate restTemplate) {
		Hat hat = restTemplate.getForObject(HATS_REST_URL + "/" + id, Hat.class);
		hat.setQuantity(hat.getQuantity() - 1);
		restTemplate.put(HATS_REST_URL + "/" + id, hat);
		return "redirect:/viewHats";
	}

	

}
