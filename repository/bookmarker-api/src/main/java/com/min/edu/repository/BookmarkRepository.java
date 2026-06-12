package com.min.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import com.min.edu.dto.BookmarksDto;




@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	
	// Entity가 아닌 BookmarkDto에 값을 처리하는 방법의 JPQL(Java Persistence Query Language)을 작성
	
	@Query("""
			select new com.min.edu.dto.BookmarkDto(b.id, b.title, b.url, b.createdAt) from Bookmark b
			""")
	Page<BookmarkDto> findByBookmarks(Pageable pageable);

	
	@Query("""
			select new com.min.edu.dto.BookmarkDto(b.id, b.title, b.url, b.createdAt) from Bookmark b
			where lower(b.title) like lower(concat('%',:query,'%'))
			""")
	Page<BookmarkDto> searchByBookmarks(String query, Pageable pageable);
}









