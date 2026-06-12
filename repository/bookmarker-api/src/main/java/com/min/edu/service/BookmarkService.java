package com.min.edu.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.RequestBody;


import com.min.edu.domain.Bookmark;
import com.min.edu.dto.BookmarkDto;
import com.min.edu.dto.BookmarksDto;

import com.min.edu.mapper.BookmarkMapper;
import com.min.edu.repository.BookmarkRepository;


import com.min.edu.dto.CreateBookmarkRequest;
import com.min.edu.mapper.BookmarkMapper;
import com.min.edu.repository.BookmarkRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkRepository repository;
	private final BookmarkMapper bookmarkMapper;
	
	@Transactional(readOnly = true)
	public BookmarksDto getBookmarks(Integer page){
		
		int pageNo = page <1 ? 0 : page-1;
		
		// jpa page를 사용한다면 Pageable에 담아서 JPA 메소드에 전달
		Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
		
//		return new BookmarksDto(repository.findAll(pageable));
		
		
		
		// 1방법 : Entity -> 값을 꺼내서 DTO에 담아준다
//		Page<BookmarkDto> bookmarkPage = 
//				repository.findAll(pageable).map(bookmark -> bookmarkMapper.toDto(bookmark));
//		return new BookmarksDto(bookmarkPage);
		
		// 2방법 : Repository에 JPQL을 작성해서 직접 결과를 BookmarkDto에 매핑해서 java객체를 반환
		Page<BookmarkDto> bookmarkPage = repository.findByBookmarks(pageable);
		return new BookmarksDto(bookmarkPage);
		
	}

	@Transactional(readOnly = true)
	public BookmarksDto searchBookmarks(String query, Integer page) {
		int pageNo = page<1 ? 0 : page-1;
		
		Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
		Page<BookmarkDto> bookmarkPage = repository.searchByBookmarks(query, pageable);
		
		return new BookmarksDto(bookmarkPage);
	}
	
	public BookmarkDto createBookmark(@RequestBody @Valid CreateBookmarkRequest request) {
		Bookmark bookmark = new Bookmark(null, request.getTitle(), request.getUrl(), Instant.now()) ;
		
		Bookmark savedBookmark = repository.save(bookmark);
		
		return bookmarkMapper.toDto(savedBookmark);
	}
}













