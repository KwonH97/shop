package com.example.shop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Member;
import com.example.shop.entity.Product;
import com.example.shop.repository.ICartItemRepository;
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
	ICartItemRepository cart;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ProductService productservice;
	
	private final Path rootLocation = Paths.get("/upload");
	
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
	public void mainPage(Model model) {
		
		List<Product> prodList = prod.findAll();
		
		model.addAttribute("prodList", prodList);
		
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
	public String prouductreg(@RequestParam("imageUrl") MultipartFile file, Product product) {
		
		productservice.saveProduct(product);
		
		try {
            // 만약 업로드할 폴더 없으면 만들기
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            if (file != null && !file.isEmpty()) {
                // 파일업로드
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                String filename = UUID.randomUUID().toString() + extension;
                Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

                // 파일이 이미 존재하면 덮어쓰기 또는 다른 처리를 해야 할 수 있음
                Files.copy(file.getInputStream(), destinationFile);

                String filePath = destinationFile.toString();

                // User 엔티티에 파일 정보 설정
                product.setFileName(filename);
                product.setFilePath(filePath);
                product.setFileSize(file.getSize());

                // User 엔티티 저장
        		productservice.saveProduct(product);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory or save file!", e);
        }
		
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/delete/{pid}")
	public String delete(@PathVariable(name = "pid") Long pid) {
		
		productservice.deleteProduct(pid);
		
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/modifyForm/{pid}")
	public String modifyForm(@PathVariable(name = "pid") Long pid, Model model) {
		
		Optional<Product> productOptional = prod.findById(pid);
		model.addAttribute("product", productOptional.get());
		
		return "admin/modifyForm";
	}
	
	@PostMapping("/modify")
	public String modify(@ModelAttribute Product product) {
		
		prod.save(product);
		
		return "redirect:/admin/dashboard";
		
	}
	
	@GetMapping("/productDetail/{pid}")
	public String detail(@PathVariable(name ="pid") Long pid, Model model) {
		
		Optional<Product> productOptional = prod.findById(pid);
		Product product = productOptional.get();
		
		model.addAttribute("product", product);
		
		return "member/productDetail";
	}
	
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