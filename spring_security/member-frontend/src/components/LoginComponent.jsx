import React, { useState } from 'react'
import axios from 'axios'
import securityImage from '../images/security.png'
import loadingGif from '../images/loadingGif.gif'

const LoginComponent = () => {

const[user, setUser] = useState({
    username:'',
    password:''
});

//로그인 상태를 확인하는 객체
const [isLoggedIn, setIsLoggedIn] = useState(false);
//remember-me 
const[rememberMe, setRememberMe] = useState(false);

//input text에서 name과 value를 state에 전달하는 함수
const handleChange = (event)=>{
    setUser({...user, [event.target.name]:event.target.value});
}

//로그인 정보를 RESTful APi를 호출하는 함수
//React에서 로그인 비동기 처리 
const login = async ()=>{

    try{
        //Spring Security는 기본적으로 form으로 처리됨
        const formData = new FormData();
        formData.append('username', user.username); //username이 security의 폼필드의 기본 이름
        formData.append('password', user.password); //password가 security의 폼필드의 기본 이름

        const response = await axios.post('http://localhost:8080/loginProcess', formData, {
            withCredentials: true
        });

        if(response.status == 200){
            //성공적으로 로그인한 후 수행할 작업
            console.log("Login Success : ", response.data);
            alert(`${response.data.username}님, 환영합니다`);

            //로그인된 정보를 서버에서 사용할 수 없다
            //로그인된 정보를 세션 저장소(브라우저)에 저장
            const userData = {
                id:response.data.username,
                role:response.data.auth

            };
            window.sessionStorage.setItem("user", JSON.stringify(userData));
            setIsLoggedIn(true);
        }
    }catch(error){
        //401인증 오류 처리 failureHandle 처리 반환 확인
        // console.log("로그인 실패", error)
        if(error.response && error.response.status == 401){
            console.error("로그인 실패", error.response.data);
            alert("로그인 실패 : 아이디나 비밀번호가 잘못되었습니다")
        }else{
            //다른 오류 처리
            console.error("오류 발생 :", error);
            alert("로그인 중 문제가 발생했습니다. 다시 시도해주세요")
        }
    }
}

if(isLoggedIn){   //로그인 성공
    return (
        <div>로그인 성공</div>
    )
}else{  //로그인 화면
    return (
        <div className='login-container'>
            <div className='login-box'>
                <img src={securityImage} alt="Security" className='login-image'/>
                <h2 className='login-title'>Login</h2>
                <div>
                    <input
                        type='text'
                        placeholder='Username'
                        name='username'
                        value={user.username}
                        onChange={handleChange}
                        className='login-input'
                    />

                    <input
                        type='text'
                        placeholder='Password'
                        name='password'
                        value={user.password}
                        onChange={handleChange}
                        className='login-input'
                    />

                    <div className='checkbox' style={{marginRight:200, marginBottom:10, marginTop:-10}}>
                        <label>
                            <input
                                type='checkbox'
                                name='rememberMe'
                                checked={rememberMe}
                                onChange={(e)=>setRememberMe(e.target.checked)} />
                            자동로그인
                        </label>
                    </div>
                    <button type='button' className='login-button' onClick={login}>로그인</button>

                </div>
            </div>

        </div>
    )

}

  
}

export default LoginComponent