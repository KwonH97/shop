package com.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.repository.IMemberRepository;

@Service
public class MemberService {
	
	@Autowired
    private IMemberRepository memb;
	
	
	
	
// Security 안쓸 때 쓰는 아이디 비번 확인 코드 @.@
	
//	public boolean authenticate(String userName, String password) {
//	        Optional<Member> mem = memb.findByUserName(userName);
//	        if (mem.isPresent()) {
//	            Member member = mem.get();
//	            return member.getPassword().equals(password);
//	        }
//	        return false;
//	    }
//	    
//    	public boolean isAdmin(String userName) {
//	        Optional<Member> memberOptional = memb.findByUserName(userName);
//	        return memberOptional.map(member -> "Role_ADMIN".equals(member.getRole())).orElse(false);
//	    }
	
	
}
