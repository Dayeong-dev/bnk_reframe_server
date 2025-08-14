package com.example.reframe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reframe.entity.Faq;
import com.example.reframe.repository.FaqRepository;

@RequestMapping("/faq")
@Controller
public class FaqController {

	@Autowired
	FaqRepository faqRepository;
	

	
	@GetMapping("/faq")
	public String qna(Model model) {
		List<Faq> faqlist = faqRepository.findAllByOrderByFaqIdAsc();
		System.out.println(faqlist);
		model.addAttribute("faqlist", faqlist);
		return "user/faq";
	}
	
	@GetMapping("/detail/{faqId}")
    public String detail(@PathVariable("faqId") Integer faqId, Model model) {
        Optional<Faq> faqOpt = faqRepository.findById(faqId);
        if (faqOpt.isPresent()) {
            model.addAttribute("faq", faqOpt.get());
            return "user/faqDetail";
        } else {
            return "redirect:/faq/faq";
        }
    }
	
	
	
	
}
