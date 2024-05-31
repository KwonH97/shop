package com.example.shop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shop.Dto.MemberDto;
import com.example.shop.entity.Member;
import com.example.shop.entity.Product;
import com.example.shop.repository.ICartItemRepository;
import com.example.shop.repository.ICommentRepository;
import com.example.shop.repository.IMemberRepository;
import com.example.shop.repository.IProductRepository;
import com.example.shop.service.MemberService;
import com.example.shop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	ICommentRepository comm;
	
	@Autowired
	IMemberRepository memb;
	
	@Autowired
	IProductRepository prod;
	
	@Autowired
	ICartItemRepository cart;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ProductService productservice;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping("/regForm")
	public void regForm() {
		
	}
	
	@RequestMapping("/regMember")
	public String regMember(MemberDto memberDto) {
		
		Member member = new Member();
		member.setUsername(memberDto.getUsername());
		
		String newpw = bCryptPasswordEncoder.encode(memberDto.getPassword());
		
		member.setPassword(newpw);
		member.setName(memberDto.getName());
		member.setRole("ROLE_MEMBER");
		member.setTel(memberDto.getTel());
		System.out.println(member);
		memb.save(member);
		
		return "redirect:/member/loginForm";
	}
	
	@GetMapping("/productDetail/{pid}")
	public String detail(@PathVariable(name ="pid") Long pid, Model model) {
		
		Optional<Product> productOptional = prod.findById(pid);
		Product product = productOptional.get();
		
		model.addAttribute("product", product);
		
		return "member/productDetail";
	}
	
}
