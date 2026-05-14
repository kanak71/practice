package com.min.edu.config;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

//로그인 인증 시 DB의 정보를 BcrypPasswordEncoder를 사용하지 않기 위한 설정
//@Nullable : Null값이 들어 올 수 있다
//메소드는 반환타입이 null일 수 있다
//Parameter null값이 전송될 수 있다
public class NoPasswordEncoder implements PasswordEncoder {

	//Security의 암호화 하는 메소드
	@Override
	public @Nullable String encode(@Nullable CharSequence rawPassword) {
		return rawPassword.toString();
	}

	//암호화를 비교해 같은 값인지 확인
	@Override
	public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

}
