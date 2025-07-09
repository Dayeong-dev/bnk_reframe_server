package com.example.reframe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.reframe.entity.Faq;
import com.example.reframe.entity.Qna;
import com.example.reframe.entity.Userr;
import com.example.reframe.repository.FaqRepository;

@RequestMapping("/faq")
@Controller
public class FaqController {

	@Autowired
	FaqRepository faqRepository;
	
	//관리자용
	@PostMapping("/faqRegist")
	public String registBoard(Faq faq, RedirectAttributes redirectAttributes) {
		faqRepository.save(faq);
		redirectAttributes.addFlashAttribute("faqSuccess", true);
		return "redirect:/faq/faq";
	}
	@GetMapping("/faq")
	public String qna(Model model) {
		List<Faq> faqlist = faqRepository.findAll();
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
