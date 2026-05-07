import React from 'react'
import HeaderComponent from '../components/HeaderComponent'
import FooterComponent from '../components/FooterComponent'
import { Outlet } from 'react-router-dom';

const EmployeeLayout = () => {
  return (
    <div className='d-flex flex-column min-vh-100'>
        {/*모든 페이지의 공통 헤더 GNB */}
        <HeaderComponent/>

        {/*실제 가변적인 콘텐츠 영역 : 자식 컴포넌트가 입력되는 곳 */}
        <div className='container flex-grow-1 mt-4'>
            <Outlet/>
        </div>

        {/* 모든 페이지의 공통 푸터*/}
        <FooterComponent/>
    </div>
  )
}

export default EmployeeLayout