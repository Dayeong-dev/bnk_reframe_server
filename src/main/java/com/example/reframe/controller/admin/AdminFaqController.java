package com.example.reframe.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reframe.entity.Faq;
import com.example.reframe.repository.FaqRepository;

@Controller
@RequestMapping("/admin")
public class AdminFaqController {
	
	@Autowired
	FaqRepository faqRepository;
	
	@GetMapping("/index2")
	public String index2(Model model) {
		List<Faq> faqlist = faqRepository.findAllByOrderByFaqIdAsc();
		System.out.println(faqlist);
		model.addAttribute("faqlist", faqlist);
		return "admin/faqIndex";
	}
	
	//관리자용
	@ResponseBody
	@PostMapping("/faqRegist")
	public String registBoard(Faq faq) {
		faqRepository.save(faq);
		return "등록 성공";
	}
	
	@GetMapping("/detail/{faqId}")
	@ResponseBody
	public Faq detail(@PathVariable("faqId") Integer faqId) {
	    return faqRepository.findById(faqId).orElse(null);
	}

}
