package com.example.shop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shop.entity.Member;
import com.example.shop.entity.Product;
import com.example.shop.repository.ICommentRepository;
import com.example.shop.repository.IMemberRepository;
import com.example.shop.repository.IProductRepository;
import com.example.shop.service.MemberService;
import com.example.shop.service.ProductService;

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
	
	@Autowired
	ProductService productservice;
	
	
	@GetMapping("/index")
	public void index() {
		
	}
	
	@GetMapping("/member/loginForm")
	public void loginForm() {
	
	}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, Model model, HttpSession session) {
		
		String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        Optional<Member> memberOptional = memb.findByUserName(userName);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            
            if (memberService.authenticate(userName, password)) {
                // 로그인 성공 시 처리
                session.setAttribute("userName", userName);

                // 사용자의 역할 확인하여 관리자이면 관리자 페이지로 이동
                if (memberService.isAdmin(userName)) {
                    return "redirect:/admin/dashboard"; // 관리자 페이지로 이동
                } else {
                    return "redirect:/mainPage"; // 일반 페이지로 이동
                }
            }
        }

        return "redirect:/member/loginForm"; // 로그인 폼으로 리다이렉트
    }
	
	@RequestMapping("/member/regForm")
	public void regForm() {
		
	}
	
	@RequestMapping("/member/regMember")
	public String regMember(Member member) {
		
		memb.save(member);
		
		return "redirect:/member/loginForm";
	}
	
	@GetMapping("/mainPage")
	public void mainPage() {
		
	}
	
	@RequestMapping("/admin/dashboard")
	public String dashboard(Model model) {
		List<Product> products = productservice.getAllProducts();
		
		model.addAttribute("products", products);
		
		return "admin/dashboard";
	}
	
	@GetMapping("/admin/pregForm")
	public void pregForm(){
	
	}
	
	@PostMapping("/preg")
	public String prouductreg(Product product) {
		
		productservice.saveProduct(product);
		
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/delete/{pid}")
	public String delete(@PathVariable(name = "pid") Long pid) {
		
		productservice.deleteProduct(pid);
		
		return "redirect:/admin/dashboard";
	}
	
}