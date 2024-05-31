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

import com.example.shop.entity.Product;
import com.example.shop.repository.ICartItemRepository;
import com.example.shop.repository.ICommentRepository;
import com.example.shop.repository.IMemberRepository;
import com.example.shop.repository.IProductRepository;
import com.example.shop.service.MemberService;
import com.example.shop.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminCotroller {
	
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
	
	@RequestMapping("/dashboard")
	public String dashboard(HttpServletRequest request, Model model) {
		
	        List<Product> products = productservice.getAllProducts();
	        model.addAttribute("products", products);
	        return "admin/dashboard";
	}
	        
	
	@GetMapping("/pregForm")
	public void pregForm(){
	
	}
	
	@PostMapping("/preg")
	public String prouductreg(@RequestParam("imageUrl") MultipartFile file, Product product) {
		
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

                
                product.setFileName(filename);
                product.setFilePath(filePath);
                product.setFileSize(file.getSize());

                
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

	        // 제품이 반드시 존재한다는 가정 하에
	        model.addAttribute("product", productOptional.get());
	        return "admin/modifyForm";
	   
	}
	
	@PostMapping("/modify")
	public String modify(@RequestParam("imageUrl") MultipartFile file, @ModelAttribute Product product) {
		Product p = productservice.findById(product.getPid());
		product.setFileName(p.getFileName());
		product.setFilePath(p.getFilePath());
		product.setFileSize(p.getFileSize());
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

                product.setFileName(filename);
                product.setFilePath(filePath);
                product.setFileSize(file.getSize());

        		productservice.saveProduct(product);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory or save file!", e);
        }
		
		return "redirect:/admin/dashboard";
		
	}
	
}
