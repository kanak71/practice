import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import securityImage from '../images/security.png'
import { Link } from "react-router-dom";

const RegistFormComponent = () => {

    //서버에 전송된 데이터
    const [user, setUser] = useState({
        id:'',
        password:''
    });

    //비밀번호 확인 데이터
    const [confirmPw, setConfirmPw] = useState('');

    //비밀번호 유효성 확인 true/false
    const [passwordError, setPasswordError] = useState(false);

    //회원가입 버튼을 두번 누르지 않도록 제출 중인지 상태 관리
    const[isSubmitting, setIsSubmitting] = useState(false);

    //이동 흐름
    const navigate = useNavigate();

    //데이터 입력 handler 작성
    const handleChange = (event) =>{
        setUser({...user, [event.target.name]:event.target.value})
    }

    //비밀번호 확인 handler 작성
    const handleConfirmPwChange = (event) =>{
        setConfirmPw(event.target.value);
        setPasswordError(user.password !== event.target.value);
    }

    //회원가입 AJAX
    const register = async (event) => {
        event.preventDefault();

        if(user.password !== confirmPw){
            setPasswordError(true);
            return;
        }

        setIsSubmitting(true);

        try{
            const response = await axios.post("http://localhost:8080/register", user);
            if(response.status === 200){
                alert("회원가입이 완료되었습니다. 로그인 화면으로 이동합니다");
                navigate("/");
            }

        }catch(error){
            console.error("회원가입 실패", error);
            alert("회원가입에 실패했습니다. 다시 시도해 주세요");
        }finally{
            setIsSubmitting(false); //응답 완료 후 상태 해제
        }

    }


  return (
    <div className='login-container'>
        <div className='login-box'>
            <img src ={securityImage} alt="이미지" className='login-image' />
            <h2 className='login-title'>회원가입</h2>
            <input 
                type='text'
                placeholder='아이디'
                name='id'
                value={user.id}
                onChange={handleChange}
                required
                className='login-input'
            />

            <input 
                type='password'
                placeholder='비밀번호'
                name='password'
                value={user.password}
                onChange={handleChange}
                required
                className='login-input'
            />

            <input 
                type='password'
                placeholder='비밀번호 확인'
                value={confirmPw}
                onChange={handleConfirmPwChange}
                required
                className='login-input'
            />

            {
                passwordError && (<p style={{color:'red', marginTip:'10px'}}>비밀번호가 일치하지 않습니다</p>)
            }

            <button
                type='submit'
                className='login-submit'
                onClick={register}
                disabled={isSubmitting}  //제출중이면 버튼 비활성화
            >
                {isSubmitting ? '등록중...':'회원가입'}
            </button>
            <Link to={'/'}>
                <button type='button' className='signup-button'>로그인 화면</button>
            </Link>

        </div>
    </div>
  )
}

export default RegistFormComponent