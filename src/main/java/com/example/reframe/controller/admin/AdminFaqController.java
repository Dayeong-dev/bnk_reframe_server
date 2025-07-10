package com.example.reframe.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reframe.entity.Faq;
import com.example.reframe.repository.FaqRepository;

@Controller
@RequestMapping("/admin")
public class AdminFaqController {
	
	@Autowired
	FaqRepository faqRepository;
	
	@GetMapping("/index2")
	public String index2(Model model) {
		List<Faq> faqlist = faqRepository.findAll();
		System.out.println(faqlist);
		model.addAttribute("faqlist", faqlist);
		return "admin/faqIndex";
	}
}
