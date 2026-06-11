import { BookmarksResponse } from '@/app/types/bookmark';
import axios from 'axios';
import React from 'react'

const API_BASE_URL = "http://localhost:8080";

// TypeScript는 javascript에서 타입이 정해지지 않는 동적타입을 사용하지 매핑되는 타입을 지정한다
// 타입이 지정되어 있기 때문에 사용시 개발의 정확성을 높힐 수 있다(undefinded가 발생하지 않는다)
export const fetchBookmarks = async (page:number): Promise<BookmarksResponse> => {
    const resp = await axios.get<BookmarksResponse>(`${API_BASE_URL}/api/bookmarks?page=${page}`);
    console.log(resp.data)
    return resp.data;
}

// export async function fetchBookmarkss(page:number, query:string): Promise<BookmarksResponse>{
//     const resp = await fetch(`http://localhost:8080/api/bookmarks?page=${page}&query=${query}`);
//     if(!resp.ok){
//         throw new Error ('잘못된 결과');
//     }
//     return resp.json();
// }

