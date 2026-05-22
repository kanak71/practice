package com.min.edu.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// JwtService는 서명된 JWT를 생성하고 검증하는 클래스
@Service
public class JwtService {

	private String secretKey;
	
	public JwtService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
			System.out.println("JwtService 생성자를 통해서 만들어지 secretKey :" + secretKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	} //JwtService 끝
	
	/*
	 * 이름, 만료일 등을 통한 정보입력
	 * Map으로 생성후에 Jwts.claims 를 통해서 변환
	 * 0.12 이전 버전에서는 setCliam을 통해서 생성 => Builder 패턴으로 변경되었다
	 */
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return Jwts.builder()
				.claims() // claim은 JWT 본문(payload)에 들어가는 데이터(사용자 정보)
				.add(claims)
				.subject(username) // 인증된 사용자의 username이다
				.issuedAt(new Date(System.currentTimeMillis()))// JWT가 발급된 시간
				.expiration(new Date(System.currentTimeMillis()+(60*1000*1))) // 60초*1000밀리세컨드+1
				.and()
				.signWith(getkey())
				.compact()
				;
	}
	
	// 생성된 key를 통해서 사용자 정보를 암호화한 key(secretkey)를 통해서 token을 만들어 낸다
	private SecretKey getkey() {
		byte[] keyByte = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	
	
	// AuthenticationFilter에서 사용하는 Token 분석 --------------------------------------------------
	// Header에서 Token을 추출
	public String extractUsername(String token) {
		//jwt에서 다양한 claims 필드에서 사용자 이름을 추출 할 수 있도록 작성
		return extractClaims(token, Claims::getSubject);
		// return extractClaims(token).getSubject();
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getkey()) //jwt를 검증할 키, 처음만들어 키를 제공하면, jwt의 유효성을 확인 : 내가만든거니?
				.build() // 파서 객체 빌드
				.parseSignedClaims(token) // JWT 파싱(변경), 유효한 경우 Claims객체를 반환
				.getPayload(); // Claims에서 필요한 playlod 부분을 추출 => username으로 사용될 값이 있다 = getSubject()
	}
	
	
	// 사용자 인증과 토큰의 만료 시간 확인
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		// token의 subject 값과 인증객체(UserDetails)와 같은지 확인 && 만료 시간 확인
		return (username.equals(userDetails.getUsername()) && !isTokenExpiration(token));
	}

	//토큰 만료시간 확인시간
	private boolean isTokenExpiration(String token) {
		// 밀리세커드  "iat": 1779240863, = > Wed May 20 2026 10:34:23 GMT+0900 (한국 표준시)
		return extractExpiration(token).before(new Date());
	}

	// 토큰에서 만료시간 추출
	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
}






