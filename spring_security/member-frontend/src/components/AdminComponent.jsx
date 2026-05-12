import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import loadingGif from '../images/loadingGif.gif'

const AdminComponent = () => {

    //sessionStorage 에서 사용자 정보 가져오기
    const loggedInUser = JSON.parse(window.sessionStorage.getItem("user"));

    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    //Status 판단을 통해서 권한을 확인 => REST API를 호출 상태를 받아 온다
    //렌더링 되기 전에 로그인 권한을 확인
    useEffect(()=>{
        const fetchData = async()=>{
            try{
                //서버에 admin을 처리 할 수 있는 요청인지를 확인 -> 서버에서 상태를 반환
                const response = await axios.get("http://localhost:8080/admin",{withCredentials:true})

                if(response.status == 200){ //1. 로그인 권한이 admin 성공(200 처리)
                    alert("관리자님 환영합니다");
                    setLoading(false);  //처음 로딩상태 이미지

                }

            }catch(error){
                //403, 404 처리
                if(error.response){
                    if(error.response.status === 403){ //1. 로그인 권한이 AccessDeniedHandler 403
                        //접근 권한이 없는 경우 로그인 페이지로 이동
                        alert("접근 권한이 없습니다. 로그인 페이지로 이동합니다");
                        navigate("/")
                    }else if(error.response.status){ //2. 요청 페이지가 없음 404
                        //페이지를 찾을 수 없습니다
                        alert("페이지를 찾을 수 없습니다");
                    }
                }else{
                    //네트워크 오류 등의 일반적인 오류
                    console.error("ERROR Checking User 상태 : ", error)
                    alert("오류가 발생했습니다")
                }
                setLoading(false);
            }

            
        };

        fetchData();
    },[navigate]);  //[]의 값이 변경되었을 때 다시 useEffect 훅을 동작시키겠다. 화면이 이동했다면 .. 다시 권한을 서버에서 확인

    if(loading){
        //로딩중일때 이미지 표시
        return (
            <div style={{textAlign:'center', padding:'20px'}}>
                <img src ={loadingGif} alt="Loading..." style={{width:'100px', height:'100px'}}/>
            </div>
        )
    }

  return (
    <div>
        <h2>접속된 관리자 정보</h2>
        <ul>
            <li>아이디 : {loggedInUser.id}</li>
            <li>권한 : {loggedInUser.role.map((role, index)=>(
                <span key={index}>{role.authority}</span>
             ))}</li>
        </ul>
    </div>
  )
}

export default AdminComponent