import axios from 'axios'

//1. axios 인스턴스 생성
const api = axios.create({
    baseURL:'http://localhost:8080',    //모든 요청의 기본 주소

});

//2. 요청 인터셉터 설정 : 모든 요청에 토큰(JWT)를 자동으로 붙여 줌
api.interceptors.request.use(
    (config) =>{
        const token = sessionStorage.getItem("jwt")
        if(token){
            config.headers.Authorization=token;
        }
        return config;
    },
    (error) =>{
        return Promise.reject(error)
    }
);

//3. 응답 인터셉터 설정:401, 403 에러(세션 만료) 공통 처리
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if(error.response && (error.response.status == 401 || error.response.status == 403)){
            alert("세션이 만료되었습니다. 다시 로그인해주세요")
            sessionStorage.clear();
            window.location.href="/";   //로그인 페이지로 리다이렉트

        }
        return Promise.reject(error);

    }
);

export default api;