
import EmployeeService from '../services/EmployeeService';
import { useNavigate, useParams } from 'react-router-dom'
import React, { useEffect, useState } from 'react'

const UpdateEmployeeComponent = () => {

  //react-router-dom의 <Route>를 요청하기 위한 Navigate
  const navigate = useNavigate();

  //useParams()를 통해서 id를 먼저 가져온다
  const{ id } = useParams(); 
  //값으로 사용될 state 객체를 초기화, 객체 방식으로 작성하고 ...(스프레드) 방식으로 입력 받아봄(event.target.name, event.target.value)
  const [formData, setFormData] = useState({
    firstName:'',
    lastName:'',
    emailId:''
  });

  //수정할 데이터를 가져오는 useEffect(hook)
  useEffect(()=>{
    if(id){ //객체가 있다면 true, param이 잘 전달되었다면
      EmployeeService.getEmployeeById(id)
        .then(res=>{
          setFormData({
              firstName:res.data.firstName,
              lastName:res.data.lastName,
              emailId:res.data.emailId
          });
        }).catch(err=>{
          console.log('ERROR Axios employee Data', err);
        })

    }
  },[id]);  //id가 변경될 때마다 데이터를 새로 로딩

  //화면에서 입력된 수정 정보를 state 입력해주는 handler 함수
  const useChangeHandler = (event) =>{
    const {name,value} = event.target;  //event.target.name , event.target.value

    setFormData({
      ...formData,
      [name]:value
    });

    //위와 같다
    // setFormData({
    //   ...formData,
    //   [event.target.name]:event.target.value
    // });

  }

  //취소 버튼 navigate를 통해서 처음(/ 혹은/employees)으로 이동
  const cancel = ()=>{
    navigate("/")
  }

  //초기화(reset 버튼)를 통해서 조회된 처음 값을 다시 formData에 담아 주는 작업
  const resetValue = (event) =>{
    event.preventDefault();
      EmployeeService.getEmployeeById(id)
          .then(res=>{
            setFormData({
                firstName:res.data.firstName,
                lastName:res.data.lastName,
                emailId:res.data.emailId
            });
          }).catch(err=>{
            console.log('ERROR Axios employee Data', err);
          })

  }

  const updateEmployee = (event) => {
    event.preventDefault();
    const{firstName, lastName, emailId} = formData; //formData에서 값 추출
    let employee = {firstName, lastName, emailId};  //수정된 값으로 employee 객체를 생성

    console.log("전달되는 employee객체 : ", employee);

    //REST API 호출 위치
    EmployeeService.updateEmployee(id, employee)
      .then(res =>{
        navigate("/");
      })
      .catch(err=>{
        console.error("ERROR Update Employee ," , err)
      })
  }

  
  return (
    <div className='container' style={{margin:"10px"}}>
      <div className='row'>
        <div className='card col-md-6 offset-md-3 offset-md-3'>
          <h3 className='text-center'>회원정보 수정</h3>
          <form onSubmit={updateEmployee}>
            <div className='form-group'>
              <label>First Name:</label>
              <input placeholder='First Name'
                      name='firstName'
                      className='form-control'
                      value={formData.firstName}
                      onChange={useChangeHandler} />
            </div>

             <div className='form-group'>
              <label>Last Name:</label>
              <input placeholder='Last  Name'
                      name='lastName'
                      className='form-control'
                      value={formData.lastName}
                      onChange={useChangeHandler} />
            </div>

             <div className='form-group'>
              <label>Email ID:</label>
              <input placeholder='Email ID'
                      name='emailId'
                      className='form-control'
                      value={formData.emailId}
                      onChange={useChangeHandler} />
            </div>

            <div style={{textAlign:"center", marginTop:"20px"}}>
              <button className='btn btn-success' onClick={cancel}>취소</button>
              <button className='btn btn-danger' onClick={resetValue}>초기화</button>
              <button className='btn btn-primary' type='submit'>수정입력</button>

            </div>
          </form>

        </div>
      </div>

    </div>
  )
}

export default UpdateEmployeeComponent