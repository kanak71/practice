package com.min.edu.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "`user`")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	/*
	 * JPA에서는 무조건 Long id를 기본 식별자로 사용한다.
	 * 의미상 프로젝트에서는 식별자가 아닌 보조식별자로 사용하는 값을 처리 해줘야한다
	 * ㄴ 식별자란? 테이블이 한개의 row를 증명할 수 있는 고유한 값(PK-NN+index+unique)
	 * ㄴ 보조식별자란? 식별자는 아니지만 식별자처럼 테이블에서 사용되는 컬럼(NN + unique)
	 * ㄴ 예시> 주민등록번호, 사원번호, 이메일, 전화번호 => 그중에서 정보가 적고 입력하기 쉬운값을 사용하면 된다 => 사원번호
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role;

	public User(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
}
















