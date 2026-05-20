package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.repository.OwnerRepsitory;
import com.min.edu.vo.Owner;

import jakarta.transaction.Transactional;

@SpringBootTest
class OwnerRepositoryTests {

	@Autowired
	private OwnerRepsitory ownerRepsitory;

	/*
	 * 1) 모든 소유자를 조회 2) 조회된 소유자에서 소유자의 여러개의 자동차 조회
	 */

	@Test
	// @Trancational 어노테이션을 추가 하면 , cars 필드를 접근할 때 세션이 열려있기 때문에 지연로딩이 가능
	@Transactional
	public void ownerRepository_Test() {
//		List<Owner> find = (List<Owner>) ownerRepsitory.findAll();  // @Transactional 처리
//		List<Owner> find = ownerRepsitory.findAllwithCars();	// JOIN FETCH 작성
		List<Owner> find = ownerRepsitory.findAll(); // @EntityGraph
		for (Owner o : find) {
			System.out.println(o.getFirstname() + "/" + o.getLastname());
			// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※/
			System.out.println(o.getCars()); // 소유자의 차량을 조회
		}
		assertNotNull(find);
	}

	

}
