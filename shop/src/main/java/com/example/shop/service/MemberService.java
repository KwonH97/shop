package com.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.entity.Member;
import com.example.shop.repository.IMemberRepository;

@Service
public class MemberService {
	
	@Autowired
    private IMemberRepository memb;
	
	public boolean authenticate(String userName, String password) {
        Member member = memb.findByUserName(userName);
        if (member != null && member.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
