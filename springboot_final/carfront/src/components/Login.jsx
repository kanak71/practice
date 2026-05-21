import React, { useState } from 'react'
import CarList from './CarList';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const Login = () => {

    //로그인을 위한 username, password 입력 객체
    const [user, setUser] = useState({
        username:'',
        password:'',

    })

    //인증상태를 나타내는 boolean 객체
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    //필드에서 useState(user)에 값을 전달하는 함수
    const handleChange = (event) =>{
        setUser({...user, [event.target.name]:event.target.value})
    }

    //로그인 정보를 REST로 요청하는 함수
    const login = () =>{
        fetch('http://localhost:8080/login',
            {
                method: 'post',
                headers: {"Content-Type":"application/json"},
                body: JSON.stringify(user),
            }
        )
        .then(response =>{
            const jwtToken = response.headers.get("Authorization");
            console.log(jwtToken)
            if(jwtToken !== null){
                sessionStorage.setItem("jwt", jwtToken);    //브라우저에 저장될 token의 이름(jwt)과 값
                setIsAuthenticated(true);
            }
        })
        .catch(error=>console.log(error))
    }

    if(isAuthenticated){   //인증정보가 있을 경우 CarList.jsx 컴포넌트 입력 받고
        return <CarList/> 
    }else{  //인증정보 생성을 위한 로그인 form 화면

        return(
            <div>
                {/*자식요소의 css 설정 간격 2배, 가운데정렬, margin-top */}
                <Stack spacing={2} alignItems='center' mt={2}>
                    <TextField
                        name='username'
                        value={user.username}
                        onChange={handleChange}
                    />

                    <TextField
                        name='password'
                        value={user.password}
                        onChange={handleChange}
                    />

                    <Button variant='outline' color='primary' onClick={login}>로그인</Button>

                </Stack>
            </div>
        )
    }



  return (
    <div>Login</div>
  )
}

export default Login