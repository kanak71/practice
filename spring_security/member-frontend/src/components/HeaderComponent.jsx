import axios from "axios";
import React from "react";
import { Link, useNavigate } from 'react-router-dom';
import SessionChecker from "./SessionChecker";

const HeaderComponent = ({ isLoggedIn, handleLogout }) => {

    //session Storage에서 사용자 정보를 가져오기
    const loggedInUser = JSON.parse(window.sessionStorage.getItem("user"));  //parse를 통해 JSON객체로 변환

    //useNavigate() 훅을 사용
    const navigate = useNavigate();

    //로그아웃 처리를 위한 함수
    const onLogout = async (e) =>{
        e.preventDefault();

        try{
            const response = await axios.post("http://localhost:8080/logout.do",{},{withCredentials:true})

            if(response.status === 200){
                alert('로그아웃이 되었습니다', response.data);
                //로그아웃을 서버에서 성공한 후, navigate를 통해서 이동하기 전에, sessionStorage를 삭제 한다
                window.sessionStorage.removeItem("user");
                handleLogout(); //App.jsx에서 props 객체를 통해 로그인 정보를 변경하여, 사용하는 Component의 상태를 변경
                navigate("/");  //로그아웃 후 router를 통해서 화면 이동
            }
        }catch(error){
            console.error("로그아웃 실패 :", error)
        }
    }

  return (
    <nav style={{backgroundColor : '#3f515b', padding: '10px', color:'white'}}>
        <div style = {{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
            <h2 style = {{margin:0}}>LikeLion</h2>
            {/*props의 로그인여부(isLoggnedIn) true/false 확인 */}
            {isLoggedIn ?(
                //사용자 정보 로그아웃 기능
                <div style = {{display:'flex', alignItems:'center'}}>
                    <p style={{marginRight:'15px'}}>Hello, {loggedInUser.id}</p>
                    <a href = '/logout.do' onClick={onLogout} style={{color:'orange', textDecoration:'none'}}>로그아웃</a>
                </div>

            ):
            (
                //로그인 기능
                <div>
                    <Link to="/" style={{color:'white', textDecoration:'none'}}>로그인</Link>
                    <Link to="/register" style={{color:'white', textDecoration:'none'}}>회원가입</Link>
                </div>

            )}
        </div>
        {/*SessionChecker에 함수 전달 */}
        <SessionChecker isLoggedIn={isLoggedIn} onLogout={onLogout}/>

    </nav>
  )
}

export default HeaderComponent;
