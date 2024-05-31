package com.example.shop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shop.config.CustomUserDetails;
import com.example.shop.entity.CartItem;
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
    public String addToCart(@RequestParam("pid") Long pid,
                            @RequestParam("quantity") int quantity,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		
        // 현재 인증된 사용자 정보를 가져옵니다.
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName(); // 현재 사용자의 아이디를 가져옵니다.
	      String username = customUserDetails.getUsername(); // 현재 사용자의 아이디를 가져옵니다.
        // 사용자 아이디와 상품 아이디를 이용하여 장바구니에 해당 상품이 이미 있는지 확인합니다.
        Optional<CartItem> existingCartItemOptional = cart.findByProductIdAndUsername(pid, username);
        
        if (existingCartItemOptional.isPresent()) {
            // 장바구니에 이미 해당 상품이 존재하는 경우에는 수량을 증가시킵니다.
            CartItem existingCartItem = existingCartItemOptional.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            
            cart.save(existingCartItem); // 장바구니 엔티티를 업데이트합니다.
            
        } else {
            // 장바구니에 해당 상품이 존재하지 않는 경우에는 새로운 장바구니 아이템을 추가합니다.
            Optional<Product> productOptional = prod.findById(pid);
            
            if (productOptional.isPresent()) {
                // 상품 정보를 가져와서 새로운 장바구니 아이템을 생성합니다.
                Product product = productOptional.get();
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(product);
                newCartItem.setQuantity(quantity);
                newCartItem.setMember(memb.findByUsername(username));
                
                // 새로운 장바구니 아이템을 저장합니다.
                
                cart.save(newCartItem);
            }
                return "/cart/cartList"; // 홈페이지로 리다이렉션합니다.
        }

        // 장바구니 페이지로 리다이렉션합니다.
        return "/cart/cartList";
    }

	@GetMapping("/cartList")
	public String cart(Model model) {
		
		List<CartItem> cartItem = cart.findAll();
		System.out.println(cartItem);
		model.addAttribute("cartItem", cartItem);
		return "/member/cartList";
	}
	
}
