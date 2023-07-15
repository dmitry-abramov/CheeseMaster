package com.example.cheese.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

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
	
	@GetMapping("/update/{id}")
	public String showUpdate(@PathVariable("id") Long id, Model model) {
		var cheese = cheeseRepository.findById(id).orElseThrow();
		model.addAttribute("cheese", cheese);
		return "cheeseUpdate";
	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") Long id, @Valid Cheese cheese, BindingResult result, Model model) {
		if (result.hasErrors()) {
            return "cheeseUpdate";
        }
        
        cheeseRepository.save(cheese);
		return "redirect:/cheese";
	}
	
	@GetMapping("/add")
	public String showAdd(@Valid Cheese cheese, BindingResult result, Model model) {
		if (result.hasErrors()) {
            return "cheeseAdd";
        }
        
        cheeseRepository.save(cheese);
        return "redirect:/cheese";
	}
	
	@PostMapping("/add")
	public String add(@Valid Cheese cheese, BindingResult result, Model model) {
		if (result.hasErrors()) {
            return "cheeseAdd";
        }
        
        cheeseRepository.save(cheese);
        return "redirect:/cheese";
	}
	
	@GetMapping("/{id}")
	public String showView(@PathVariable("id") Long id, Model model) {
		var cheese = cheeseRepository.findById(id).orElseThrow();
		model.addAttribute("cheese", cheese);
		return "cheeseView";
	}
	
	// TODO: use delete http verb 
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {	    
	    cheeseRepository.deleteById(id);
	    return "redirect:/cheese";
	}
}
