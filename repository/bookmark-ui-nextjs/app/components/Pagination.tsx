import React from 'react'
import { BookmarksResponse } from '../types/bookmark'
import Link from 'next/link';


// Props에 BookmarksResponse 안에 page에 관련 된 정보가 들어 있다
interface PaginationProps {
    bookmarks: BookmarksResponse;
    query?: string // 선택적 프로퍼티스 ?: 있어도 되고 없어도 되고 
}

const Pagination = ({ bookmarks, query }: PaginationProps) => {

    const path ="/bookmarks";
    /*
        객체 형태로 만듬 : {pathname:path , query: {page:1}}
        <Link href="./bookmarks?page=1"

        NextJs가 자동으로 {pathname:path , query: {page:1}} => ./bookmarks?page=1로 변경해 준다

    */
    const firstPage = {pathname:path , query: {page:1}};
    const previousPage = {pathname:path, query:{page:bookmarks.currentPage-1}};
    const nextPage = {pathname:path, query:{page:bookmarks.currentPage+1}};
    const lastPage = {pathname:path , query: {page:bookmarks.totalPages}};

    return (
        <div>
            <nav aria-label="Page">
                <ul style={{display:'flex', justifyContent:'space-between', padding:0, listStyle:'none'}}  className="pagination">
                    {/* 이전 */}
                    <li className={"page-item" + (bookmarks.hasPrevious ? "" : "disabled")} >
                        <Link className="page-link" href={previousPage}>Previous</Link>
                    </li>

                    {/* 이후 */}
                    <li className={"page-item" + (bookmarks.hasNext ? "" : "disabled")}>
                        <Link className="page-link" href={nextPage}>Next</Link>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default Pagination