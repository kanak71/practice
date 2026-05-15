package com.min.edu.security;

import java.util.Map;

import org.springframework.security.oauth2.client.annotation.ClientRegistrationId;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.min.edu.entity.User;
import com.min.edu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//네이버 로그인 요청시 기존 사용자 조회, 없을 경우 회원정보를 입력
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId =
			    userRequest.getClientRegistration().getRegistrationId();
			
		if(registrationId.equals("naver")) {
			//네이버에서 반환한 정보
			Map<String, Object> attributes = (Map<String, Object>)oAuth2User.getAttribute("response");
			System.out.println("네이버에서 반환한 사용자 정보:" + attributes);
			
			//DB에서 값이 존재하는지 확인
			String email = (String)attributes.get("email");
			System.out.println("입력할 User객체 : " + User.createUser(attributes));	//User엔티티에서 쉽게 요청된 결과 Map(attributes)를 입력해주는 메소드(static)
			
			//만약에 존재한다면, 수정된 사항이 있을 경우 업데이트
			//존재하지 않는다면, save
			User user = userRepository.findByEmail(email)
							.map(existUser ->{
								//영속성 컨텍스트 내에 존재하면 자동으로 변경사항이 감지되어서 저장한다. 하지만 안전하게 하기 위해서 save를 한번 더 호출
								existUser.updateUser(attributes);	//기존 사용자의 업데이트
								return userRepository.save(existUser);
							})
							.orElseGet(() ->userRepository.save(User.createUser(attributes)));
			
			return new Custom_OAuth2_User(user, attributes);
			
			
		} else if(registrationId.equals("google")) {
			//구글에서 반환한 정보
			Map<String, Object> attributes =oAuth2User.getAttributes();
			System.out.println("구글에서 반환한 사용자 정보:" + attributes);
			
			//DB에서 값이 존재하는지 확인
			String email = (String)attributes.get("email");
			System.out.println("입력할 User객체 : " + User.createUser(attributes));	//User엔티티에서 쉽게 요청된 결과 Map(attributes)를 입력해주는 메소드(static)
			
			//만약에 존재한다면, 수정된 사항이 있을 경우 업데이트
			//존재하지 않는다면, save
			User user = userRepository.findByEmail(email)
							.map(existUser ->{
								//영속성 컨텍스트 내에 존재하면 자동으로 변경사항이 감지되어서 저장한다. 하지만 안전하게 하기 위해서 save를 한번 더 호출
								existUser.updateUser(attributes);	//기존 사용자의 업데이트
								return userRepository.save(existUser);
							})
							.orElseGet(() ->userRepository.save(User.createUser(attributes)));
			
			return new Custom_OAuth2_User(user, attributes);
		}
		
		return null;
		
		
	}
	
}
