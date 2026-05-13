package com.min.edu.config;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.min.edu.domain.UserEntity;

/*
 * CustomUserDetail은 VO객체를 바로 loadUserByUsername(리턴타입 : UserDetails)에 사용할 수 없기 때문에
 * User 클래스는 재정의하여 VO 객체를 사용할 수 있도록 한다
 */
public class CustomUserDetail extends User {	
	
	private static final long serialVersionUID = -3393644371184684888L;

	//사용할 객체를 선언 **** 반드시 직렬화가 가능한 객체(serialVersionUID 설정)
	private UserEntity userEntity;
	
	//User는 Default 생성자를 가지고 있지 않기 때문에 호출이 방식이 다르다
	//오버라이드된 생성자를 호출 해야 한다
	public CustomUserDetail(String username, 
							@Nullable String password,
							Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	//User에 세개값(username, password, authorities)에 VO의 정보를 넣어 super(부모)에 전달
	public CustomUserDetail(UserEntity vo) {
		
		//vo 정보를 통해서 super 전달 => 인증(Authentication => UserDetails 정보)
		super(vo.getId(), 
				vo.getPassword(), 
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+vo.getRole())));
		//세개의 값 + VO(DTO)를 추가 정보를 Principal에 담을 수 있다
		this.userEntity = UserEntity.builder()
							.id(vo.getId())
							.role(vo.getRole())
							.address(vo.getAddress())
							.build();
	}

	
	//부모에서는 세개값(username, password, authorities)을 가져갈 수 있는 getter/setter가 있다. 하지만 자식이 만들었기 때문에
	//자식 클래스에서 getter 만들지 않으면 UserEntity의 값을 사용할 수 없게 된다
	
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	
	
	

}
