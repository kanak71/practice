package com.min.edu.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.min.edu.entity.User;

public class Custom_OAuth2_User implements OAuth2User, UserDetails {
	

	private static final long serialVersionUID = 5654581914397536783L;
	
	private final User user;
	private final Map<String, Object> attributes;
	
	

	public Custom_OAuth2_User(User user, Map<String, Object> attributes) {
		super();
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE " +user.getRole()));
	}

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public @Nullable String getPassword() {
		return null;	//OAuth2는 비밀번호를 사용하지 않음
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	//계정만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//계정잠김 여부
	@Override
	public boolean isAccountNonLocked() {
		//!user.isLocked()	// => 5회 실패시
		return true;
	}
	
	//비밀번호 만료
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//계정 활성 여부
	@Override
	public boolean isEnabled() {
		//user.isEnabled()
		//정지회원 : user.getStatus().equals("ACTIVE")
		return true;
	}
	

}











