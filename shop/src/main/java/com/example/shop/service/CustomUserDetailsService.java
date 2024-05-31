package com.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.shop.config.CustomUserDetails;
import com.example.shop.entity.Member;
import com.example.shop.repository.IMemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private IMemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByUsername(username);
		
		if(member != null) {
			return new CustomUserDetails(member);
		}
		return null;
	}
}
