package com.example.shop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Member;
import com.example.shop.entity.Product;
import com.example.shop.repository.ICartItemRepository;
import com.example.shop.repository.ICommentRepository;
import com.example.shop.repository.IMemberRepository;
import com.example.shop.repository.IProductRepository;
import com.example.shop.service.MemberService;
import com.example.shop.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

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
	
	@PostMapping("/addToCart")
	public String addToCart(@RequestParam("pid") Long pid, @RequestParam("userName") String userName) {
	  
		Optional<Product> productOptional = prod.findById(pid);
	    Optional<Member> memberOptional = memb.findById(userName);

	    if (productOptional.isPresent() && memberOptional.isPresent()) {
	        Product product = productOptional.get();
	        Member member = memberOptional.get();
	        
	        // 장바구니에 추가할 상품과 회원 정보를 이용하여 CartItem 객체 생성
	        CartItem cartItem = CartItem.builder()
	                                    .product(product)
	                                    .member(member) 
	                                    .quantity(1) 
	                                    .build();
	        
	        cart.save(cartItem);
	    }

	    return "redirect:/cart"; // 장바구니 페이지로 리다이렉션
	}
	
	@GetMapping("/cart")
	public void cart(Model model) {
		
		List<CartItem> cartItem = cart.findAll();
		System.out.println(cartItem);
		model.addAttribute("cart", cartItem);
		
	}
	
}
