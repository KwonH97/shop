package com.example.shop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shop.entity.Product;
import com.example.shop.repository.IProductRepository;

@Controller
public class KHController {
	
//	@Autowired
//	ICommentRepository comm;
//	
//	@Autowired
//	IMemberRepository memb;
//	
	@Autowired
	IProductRepository prod;
//	
//	@Autowired
//	ICartItemRepository cart;
//	
//	@Autowired
//	MemberService memberService;
//	
//	@Autowired
//	ProductService productservice;
	
//	private final Path rootLocation = Paths.get("/upload");
	
	@GetMapping("/index")
	public void index() {
		
	}
	
	@GetMapping("/mainPage")
	public void mainPage(Model model) {
		
		List<Product> prodList = prod.findAll();
		
		model.addAttribute("prodList", prodList);		
	}
	
	@GetMapping("/loginForm")
	public void loginForm() {
	
	}
	
	@GetMapping("/productDetail/{pid}")
	public String detail(@PathVariable(name ="pid") Long pid, Model model) {
		
		Optional<Product> productOptional = prod.findById(pid);
		Product product = productOptional.get();
		
		model.addAttribute("product", product);
		
		return "/detail";
	}
	
//	@GetMapping("/member/loginForm")
//	public void loginForm() {
//	
//	}
	
//	@PostMapping("/login")
//	public String login(HttpServletRequest request, Model model, HttpSession session) {
//		
//		String userName = request.getParameter("userName");
//        String password = request.getParameter("password");
//        
//        if (memberService.authenticate(userName, password)) {
//            session.setAttribute("userName", userName);
//            session.setAttribute("role", memberService.isAdmin(userName) ? "ADMIN" : "USER");
//
//            if ("ADMIN".equals(session.getAttribute("role"))) {
//                return "redirect:/admin/dashboard"; // 관리자 페이지로 이동
//            } else {
//                return "redirect:/mainPage"; // 일반 페이지로 이동
//            }
//        }
//
//        return "redirect:/member/loginForm"; // 로그인 폼으로 리다이렉트
//    }
	
//	@RequestMapping("/member/regForm")
//	public void regForm() {
//		
//	}
	
//	@RequestMapping("/member/regMember")
//	public String regMember(Member member) {
//		
//		memb.save(member);
//		
//		return "redirect:/member/loginForm";
//	}
	
//	@GetMapping("/productDetail/{pid}")
//	public String detail(@PathVariable(name ="pid") Long pid, Model model) {
//		
//		Optional<Product> productOptional = prod.findById(pid);
//		Product product = productOptional.get();
//		
//		model.addAttribute("product", product);
//		
//		return "member/productDetail";
//	}
	
//	@PostMapping("/addToCart")
//	public String addToCart(@RequestParam("pid") Long pid, @RequestParam("userName") String userName) {
//	  
//		Optional<Product> productOptional = prod.findById(pid);
//	    Optional<Member> memberOptional = memb.findById(userName);
//
//	    if (productOptional.isPresent() && memberOptional.isPresent()) {
//	        Product product = productOptional.get();
//	        Member member = memberOptional.get();
//	        
//	        // 장바구니에 추가할 상품과 회원 정보를 이용하여 CartItem 객체 생성
//	        CartItem cartItem = CartItem.builder()
//	                                    .product(product)
//	                                    .member(member) 
//	                                    .quantity(1) 
//	                                    .build();
//	        
//	        cart.save(cartItem);
//	    }
//
//	    return "redirect:/cart"; // 장바구니 페이지로 리다이렉션
//	}
	
//	@GetMapping("/cart")
//	public void cart(Model model) {
//		
//		List<CartItem> cartItem = cart.findAll();
//		System.out.println(cartItem);
//		model.addAttribute("cart", cartItem);
//		
//	}
	
}