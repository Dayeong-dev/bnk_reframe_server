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

import com.example.reframe.entity.Qna;
import com.example.reframe.entity.Userr;
import com.example.reframe.repository.QnaRepository;
import com.example.reframe.repository.UserrRepository;

@RequestMapping("/qna")
@Controller
public class QnaController {

	@Autowired
	QnaRepository qnaRepository;
	
	@Autowired
	UserrRepository userRepository;
	
	@PostMapping("/qnaRegist")
	public String registBoard(Qna qna, RedirectAttributes redirectAttributes) {

		
		Userr user = userRepository.findById("user01")
              .orElseThrow(() -> new RuntimeException("로그인된 사용자 없음"));
		//유저엔티티 받으면 그 코드로 수정하기
		qna.setUser(user);
		qnaRepository.save(qna);
		redirectAttributes.addFlashAttribute("qnaSuccess", true);
		return "redirect:/qna/qna";
	}
	@GetMapping("/qna")
	public String qna(Model model) {
		List<Qna> qnalist = qnaRepository.findByUser_Username("user01");
		System.out.println(qnalist);
		model.addAttribute("qnalist", qnalist);
		return "user/qna";
	}
	
	@GetMapping("/detail/{qnaId}")
    public String detail(@PathVariable("qnaId") Integer qnaId, Model model) {
        Optional<Qna> qnaOpt = qnaRepository.findById(qnaId);
        if (qnaOpt.isPresent()) {
            model.addAttribute("qna", qnaOpt.get());
            return "user/qnaDetail";
        } else {
            return "redirect:/qna/qna";
        }
    }
	
	
	
	
}
