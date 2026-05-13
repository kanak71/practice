package com.min.edu.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//TODO 001 로그인 정보를 담고 있는 Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name="app_user")
@Entity
//사용되는 VO객체를 고유한 객체로 선언하기 위해서 serialVersionUID를 선언해야 한다
public class UserEntity implements Serializable{
	
	private static final long serialVersionUID = 6351423366585315074L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
	@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
	private Long memCode;
	
	@Column(name = "id", nullable = false, unique = true)
	private String id;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="role", nullable = false)
	private String role;
	
	//추가할 정보 컬럼을 추가
	@Column(name = "address")
	private String address;
	
	//회원가입 Entity 객체에 role의 기본값을 USER 입력되도록 @Prepersist
	@PrePersist
	public void prePersist() {
		this.role = this.role==null ? "USER": this.role;
	}

	
	public UserEntity(String id, String password, String role) {
		super();
		this.id = id;
		this.password = password;
		this.role = role;
	}
	
	
	
}








