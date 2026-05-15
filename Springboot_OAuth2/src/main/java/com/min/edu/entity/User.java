package com.min.edu.entity;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "apiuser")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String name;
	
	private String nickname;		//별명
	private String profileImage;	//프로파일 사진
	private String gender;			//성별
	private String birthday;		//생일(MM-DD)
	private String ageRange;		//연령대
	private String phoneNumber;		//전화번호
	
	@Column(nullable = false)
	private String role;
	
	//소셜 로그인의 반환된 결과는 User객체로 변환해주는 static 메소드
	public static User createUser(Map<String, Object> attributes) {
		return User.builder()
				.email((String)attributes.get("email"))
				.name((String)attributes.get("name"))
				.nickname((String)attributes.get("nickname"))
				.profileImage((String)attributes.get("profile image"))
				.gender((String)attributes.get("gender"))
				.ageRange((String)attributes.get("age"))
				.birthday((String)attributes.get("birthday"))
				.phoneNumber((String)attributes.get("mobile"))
				.role("USER")	//기본적으로 USER 권한 부여
				.build();
	}
	
	//수정시 사용하는 Update
	public User updateUser(Map<String, Object> attributes) {
		this.nickname = (String)attributes.get("nickname");
		this.profileImage = (String)attributes.get("profile image");
		this.gender = (String)attributes.get("gender");
		this.birthday = (String)attributes.get("birthday");
		this.ageRange = (String)attributes.get("age");
		this.phoneNumber = (String)attributes.get("mobile");
		return this;
	}
	
	
	

}
