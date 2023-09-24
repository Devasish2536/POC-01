package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.UsersInfo;
import com.example.demo.repository.MyRepository;

@Service
public class MySecurityService implements UserDetailsService {
	@Autowired
	private MyRepository repository;
	String usersname, password, authority = null;
	List<GrantedAuthority> authorityList = new ArrayList<>();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UsersInfo usersInfo = repository.findByUsername(username);
		usersname = usersInfo.getUsername();
		password = usersInfo.getPassword();
		authority = usersInfo.getAuthority();
		authorityList.add(new SimpleGrantedAuthority(authority));
		return new User(username, password, authorityList);
	}

}
