import { useEffect, useState } from "react";

import "./App.css";
import LoginComponent from "./components/LoginComponent";
import HeaderComponent from "./components/HeaderComponent";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import AdminComponent from "./components/AdminComponent";
import UserComponent from "./components/UserComponent";
import axios from "axios";
import RegistFormComponent from "./components/RegistFormComponent";

function App() {
  //로그인 상태를 확인하는 객체
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  //세션이 확인중임을 나타내는 로딩 상태
  const [loading, setLoading] = useState(true);

  //LoginComponent.jsx에서 로그인 sessionStorage를 담고 실행
  const handleLogin = () => {
    console.log("로그인 상태 변경 핸들");
    setIsLoggedIn(true);
  }

  //HeaderComponent.jsx에서 로그아웃 버튼 실행
  const handleLogout = () => {
    setIsLoggedIn(false);
  }



  //useEffect를 통한 세션 체크 : 새로고침 -> useEffect(hook)이 동작된다 => server 세션을 확인한다 => query React(tanStack Query)를 사용 권장 -----------------------------------------------------
  useEffect(()=>{
    const checkAuth = async ()=>{
      // 서버에서 "checkSession"을 호출
      try{

      
        const response = await axios.get("http://localhost:8080/checkSession",{withCredentials:true})

        /*
          {
            "isLoggedIn":"",
            "username":"",
            "auth":""
          }
        */
        if(response.data.isLoggedIn){
            console.log("세션유지중", response.data.username);
            setIsLoggedIn(true);

            const userData = {
              id : response.data.username,
              role : response.data.auth 

            }
            window.sessionStorage.setItem("user",JSON.stringify(userData));
        }else{
            setIsLoggedIn(false);
            window.sessionStorage.removeItem("user")
        }

     }catch(error){
      console.error("세션 확인 실패 : ", error)
      setIsLoggedIn(false);
      window.sessionStorage.removeItem("user")
     }finally{
      setLoading(false)
     }
    };
    checkAuth();
  },[]);

  //로딩 중일때 아무것도 렌더링하지 않거나 로딩 스피너를 보여짐
  //새로고침 시 튕김을 방지할 수 있다
  if(loading){
    return <div className="loading">인증 정보 확인 중...</div>
  }

  return (
    <div className="App">
      <BrowserRouter>
        {/*로그인 상태 props를 통한 Header 출력값 변경 */}
        <HeaderComponent isLoggedIn={isLoggedIn} 
          handleLogout={handleLogout}
        />
        <Routes>
          {/*로그인 상태 props를 통한 로그인 환면 혹은 성공화면, 로그인 후 props 값을 변경하는 함수 전달 */}
          <Route path='/' element={<LoginComponent isLoggedIn={isLoggedIn} handleLogin={handleLogin}/>}/>

          {/**
           *  element={isLoggedIn ? ;'':''}는 주소에서 직접 localhost:8080/admin-page를 호출하면 리다이렉션이 된다
           * 로그인이 된 상태정보를 확인하여 로그인이 되어 있지 않으면 진입할 수 없도록 전제 조건을 작성
           */}
          <Route path='/admin-page' element={isLoggedIn ? <AdminComponent/> : <Navigate to="/"/>}/>
          <Route path='/user-page' element={isLoggedIn ? <UserComponent/> : <Navigate to="/"/>}/>

          <Route path='/register' element={<RegistFormComponent/>}/>

        </Routes>

      </BrowserRouter>
    </div>
  );
}

export default App;
