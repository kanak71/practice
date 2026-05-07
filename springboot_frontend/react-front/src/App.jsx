import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import ListEmployeeComponent from './components/ListEmployeeComponent'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import EmployeeLayout from './layout/employeeLayout'

function App() {

  return (
    <>
     {/*BrowserRouter는 react-router-dom 컴포넌트를 감싸는 최상단에 위치*/}
     <BrowserRouter>
      <Routes>
        {/*경로로 시작하는 모든 하위 페이지에 EmployeeLayout.jsx를 적용 */}
        {/*기본 layout이 되는 component */}
            <Route path='/' element={<EmployeeLayout/>}>
              {/*메인(/) 접속 시 바로 목록을 보여준다 */}
              <Route index element={<ListEmployeeComponent/>}/>
              {/*/employess 경로로 접속 시 */}
              <Route path='employees' element={<ListEmployeeComponent/>}/>
            </Route>
        
      </Routes>
     </BrowserRouter>
    </>
    
  )
}

export default App
