import { fetchBookmarks } from '@/services/api/fetchBookmarks'
import React from 'react'
import Bookmarks from '../components/Bookmarks';
import Pagination from '../components/Pagination';

// 서버요청을 위한 컴포넌트
/*
  "searchParams"는 URL 쿼리 문자열을 추출하여 javascript 객체 형태로 만들어 준다

  예시> URL http://example.com/page?param1=value1&param2=value2 인 경우
  => searchParams는 {param1:value1,  param2:value2} 와 같은 형태로 변경해 준다
  

*/

/*
  Nextjs에서 App Router에서 Props타입을 정의
  URL에서 Query String을 객체형태로 전달하기 위해서 searchParams
  => page?: 는 속성이 있을 수도 있고 없을 수도 있음을 의미한다. 있으면 string으로 변환해준다 
*/
type Props = {
  searchParams: Promise<{
    page?: string;
  }>;
};

const Home = async ({ searchParams }: Props) => {

  const { page } = await searchParams;
  const pageNumber = page ? parseInt(page,10):1;

  const bookmarks = await fetchBookmarks(pageNumber);// 서버사이트 데이터 패칭

  return (
    <>
      <h2>Welcom to Bookmark</h2>
      <Pagination bookmarks={bookmarks}/>

      <ul>
        <Bookmarks bookmarks={bookmarks} />
      </ul>
    </>
  )
}

export default Home