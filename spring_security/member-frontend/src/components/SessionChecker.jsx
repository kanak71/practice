import axios from 'axios';
import React, { useEffect } from 'react'

//React에서 서버의 Session 정보를 확인하는 Component : 10초마다
const SessionChecker = ({ isLoggedIn, onLogout}) => {

    useEffect(()=>{
        if(isLoggedIn){ //로그인된 상태에서만 세션 확인
            const checkSession = async () =>{
                try{
                    await axios.get("http://localhost:8080/checkSession",{withCredentials:true})
                }catch(error){
                    if(error.response && error.response.status === 403){
                        //세션 만료처리
                        alert("세션이 만료되었습니다. 다시 로그인 해주세요");
                        onLogout(); //HeaderComponent.jsx의 onLogout함수 호출
                    }
                }
            };  //확인작업 checkSession끝

            const intervalId = setInterval(checkSession, 10000);    //10초마다 세션 확인
            return () => clearInterval(intervalId)  //컴포넌트 언마운트 시 인터벌 제거
            


        }

    },[isLoggedIn, onLogout]);


  return null;
}

export default SessionChecker