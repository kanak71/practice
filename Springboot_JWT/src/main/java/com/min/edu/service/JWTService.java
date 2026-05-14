package com.min.edu.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
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

//TODO 007 JWT를 생성하는 Service 작성
@Service
public class JWTService {
	
	//임의의 Secretkey
	private String secretkey;
	
	//keyGenerator를 통한 Secretkey
	public JWTService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	//연습용 JWT를 사용하겠다
	public String generateToken(String username) {
		//이름, 만료일 등을 통한 정보 입력
		//Map으로 생성 후 jwts.clains를 통해서 반환
		//**** 0.12.xx 이전버전에서는 setCliams 통해서 생성, 이후에서는 Builder Pattern
		//JWT안에 추가정보(payload 데이터) = claims(Map)
		Map<String, Object> claims = new HashMap<String, Object>();
		return Jwts.builder()
				.claims()	//claim은 JWT 본문에 들어가는 데이터정보(사용자 정보)
				.add(claims)
				.subject(username) 	//인증도니 사용자의 username을 대상으로 함
				.issuedAt(new Date(System.currentTimeMillis())) 	//발급한 시간
				.expiration(new Date(System.currentTimeMillis()+(1000*60*60*10)))	//JWT의 만료 시간
				.and()
				.signWith(getKey())	//서명을 생성하는데 사용할 키
				.compact();	//JWT직렬화 하여 문자열 형태로 반환, 즉, JWT 토큰 문자열
	}
	
	//생성된 key를 통해서 사용자의 정보를 암호화한 key를 secretKey를 통해서 키를 만들어 냄
	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
//	//연습용 JWT를 사용한다
//	public String generateToken() {
//		return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6InN1cGVybWFuIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxNTE2MjQ5MDIyfQ.e_LM-uBNJ0G_dN9q5QnEyToO4z2vg0D40PakftRXbNw";
//	}

	//TODO 010 token으로 부터 사용자의 이름을 추출 sub : claims에서 getSubject에서 추출
	public String extractUserName(String token) {
		return extractClaim(token, Claims :: getSubject);	//user01 가져온다
	}
	
	//TODO 011 Claims 객체를 받아서 T 타입의 값을 반환하는 함수
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
		final Claims claims = extractAllClaims(token); //Token으로 Cliam 객체를 가져온다
		return claimResolver.apply(claims); //함수형 인터페이스 apply를 통해서 처리 결과를 반환
	}
	
	//TODO 012 token에서 JWT를 파싱(형변환) 하는 함수
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey()) 	//JWT 서명을 검증, 서명시 사용된 키를 제공하는 메소드, 서명 유효성 확인
				.build()
				.parseSignedClaims(token) //JWT 토큰을 파싱하고, 서명이 유효한 경우 Claim 객체를 반환
				.getPayload(); 	//Claims에 payload 부분(데이터를) 추출
	}

	//TODO 013 사용자 인증상태 확인(토큰 만료 확인)
	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);		
		return (userName.equals(userDetails.getUsername()) && !isTokenExpiration(token));
	}
	
	//TODO 014 JWT 토큰이 만료되었는지 확인하는 메소드 (시간측정)
	private boolean isTokenExpiration(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	//TODO 015 JWT에서 만료시간을 추출하는 메소드
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	

}






