package com.example.cheese.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cheese")
public class CheeseController {
	@Autowired
	private CheeseRepository cheeseRepository;

	@GetMapping()	
	public String index(Model model) {
		model.addAttribute("cheeses", cheeseRepository.findAll());
		return "cheeseList";
	}
	
	@GetMapping("/list")	
	public String list(Model model) {
		model.addAttribute("cheeses", cheeseRepository.findAll());
		return "cheeseList";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cheese", cheeseRepository.findById(id).get());
		return "cheeseEdit";
	}
	
	@GetMapping("/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cheese", cheeseRepository.findById(id).get());
		return "cheeseView";
	}
	
	@PostMapping()
	public String create() throws Exception {			
		return "redirect:cheese";
	}
}
