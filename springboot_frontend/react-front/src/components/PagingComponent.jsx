import axios from 'axios';
import React, { useEffect, useState } from 'react'

const PagingComponent = () => {

    //1. 상태(state)설정
    const[posts,setPosts] = useState([]);   //전체데이터
    const[currentPage, setCurrentPage] = useState(1);   //현재 페이지 번호
    const postPerPage = 5;      //한페이지에 보여줄 게시글의 개수
    const pageNumberLimit = 5;  //한번에 보여줄 페이지 번호의 개수 (그룹)

    //2. 데이터 가져오기(API 호출) => JPA Pagenation
    useEffect(()=>{
        axios.get('https://jsonplaceholder.typicode.com/posts')
            .then(res =>{
                setPosts(res.data);
            })
    },[]);

    //3. 페이지 계산 로직
    // 1) 현재 페이지에 보여줄 게시글 인덱스 계산
    const indexOfLastPost = currentPage * postPerPage;  //현재 페이지의 마지막 인덱스
    const indexOfFirstPost = indexOfLastPost - postPerPage;     //현재 페이지의 첫번째 인덱스
    const currentPosts = posts.slice(indexOfFirstPost, indexOfLastPost);    //현재 화면에서 데이터 추출

    //2) 전체 페이지 수 계산
    const totalPages = Math.ceil(posts.length / postPerPage);

    //3) 페이지 번호 그룹 계산(예 1~5, 6~10)
    const currentGroup = Math.ceil(currentPage/pageNumberLimit);
    const lastNumber = currentGroup*pageNumberLimit;
    const firstNumber = lastNumber - (pageNumberLimit-1);

    //4) 실제 마지막 페이지 번호 보정(예: 전체 페이지 17인데 그룹 20인 경우)
    const actualLastNumber = lastNumber > totalPages ? totalPages : lastNumber;

    //5) 페이지 이동 처리 핸들러
    const paginate = (pageNumber) => {setCurrentPage(pageNumber)}

  return (
    <div className='container mt-5'>
        <header className='alert alert-info text-center'>
            <h3>페이징 처리 연습(jsonplaceholder)</h3>
        </header>

        <main>

            {/*게시글 리스트 출력 */}
            {
                currentPosts.map(({id,title,body})=>(
                    <article key={id} className='card mb-3'>
                        <div className='card-body'>
                            <h5 className='card-title text-primary'>{id},{title}</h5>
                            <p className='card-text'>{body}</p>
                        </div>
                    </article>
                ))
            }
        </main>

        <nav>
            <ul className='pagination justify-content-center'>
                {/* 맨앞으로 */}
                <li className={`page-item ${currentPage==1 ? 'disabled' : ''}`}>
                    <button className='page-link' onClick={()=> paginate(1)}>{"<<"}</button>
                </li>
                {/* 이전그룹 */}
                <li className={`page-item ${firstNumber == 1} ? 'disabled': ''`}>
                    <button className='page-link' onClick={()=> paginate(firstNumber-1)}>{"<"}</button>

                </li>

                {/* 숫자 페이지 번호  (value,index)에서 문법상 빈값을 넣기 위해서 _ 사용*/}
                {
                    Array.from({length:actualLastNumber - firstNumber+1},(_,i)=>firstNumber+i).map(num=>(
                        <li key={num} className={`page-item ${currentPage == num ? 'active' : ''}`}>
                            <button className='page-link' onClick={()=>paginate(num)}>{num}</button>
                        </li>
                    ))
                }


                {/* 다음 그룹 */}
                <li className={`page-item ${lastNumber >= totalPages ? 'disabled':''}`}>
                    <button className='page-link' onClick={()=>paginate(lastNumber+1)}>{">"}</button>
                </li>

                {/* 맨 뒤로 */}
                <li className={`page-item ${currentPage == totalPages ? 'disabled':''}`}>
                    <button className='page-link' onClick={()=>paginate(totalPages)}>{">>"}</button>
                </li>
            </ul>
        </nav>

    </div>
  )
}

export default PagingComponent