package com.example.shop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.entity.Member;
import com.example.shop.repository.IMemberRepository;

@Service
public class MemberService {
	
	@Autowired
    private IMemberRepository memb;
	
	public boolean authenticate(String userName, String password) {
        Optional<Member> mem = memb.findByUserName(userName);
        Member member = mem.get();
        if (member != null && member.getPassword().equals(password)) {
            return true;
        }
        
        return false;
    }
	
	public boolean isAdmin(String userName) {
	    Optional<Member> memberOptional = memb.findByUserName(userName);
	    if (memberOptional.isPresent()) {
	        Member member = memberOptional.get();
	        String role = member.getRole(); // 사용자의 역할을 문자열로 가져옴
	        
	        return "Role_ADMIN".equals(role); // "ADMIN"과 사용자의 역할을 비교하여 관리자인지 확인	
	        
	    }
	    	return false; // 사용자가 존재하지 않는 경우 false 반환
	}
	
	
	
}
