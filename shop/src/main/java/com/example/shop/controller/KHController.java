package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shop.entity.Member;
import com.example.shop.repository.ICommentRepository;
import com.example.shop.repository.IMemberRepository;
import com.example.shop.repository.IProductRepository;
import com.example.shop.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class KHController {
	
	@Autowired
	ICommentRepository comm;
	
	@Autowired
	IMemberRepository memb;
	
	@Autowired
	IProductRepository prod;
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/index")
	public void mainPage() {
		
	}
	
	@GetMapping("/member/loginForm")
	public void loginForm() {
	
	}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, Model model, HttpSession session) {
		
		String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        System.out.println(memberService.authenticate(userName, password));
        if (memberService.authenticate(userName, password)) {
            // 로그인 성공 시 처리
    		session.setAttribute("userName", userName);
    		
            return "redirect:/mainPage";
        
        } else {
            // 로그인 실패 시 처리
            return "redirect:/mainPage";
        }
	}
	
	@RequestMapping("/member/regForm")
	public void regForm() {
		
	}
	
	@RequestMapping("/member/regMember")
	public String regMember(Member member) {
		
		memb.save(member);
		
		return "redirect:/member/loginForm";
	}
	
}
