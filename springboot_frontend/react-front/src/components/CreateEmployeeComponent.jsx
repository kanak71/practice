import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import EmployeeService from '../services/EmployeeService';

const CreateEmployeeComponent = () => {

    const navigate = useNavigate();

    const [firstName, setFirstName] = useState('');
    const [lastName,setLastName] = useState('');
    const [emailId,setEmailId] = useState('');

    const useChangeFirstNameHandler = (event) =>{
        setFirstName(event.target.value);
    }

    const useChangeLastNameHandler = (event) =>{
        setLastName(event.target.value);
    }

    const useChangeEmailIdHandler = (event) =>{
        setEmailId(event.target.value);
    }

    const cancel = () =>{
        navigate("/employees")
    }

    const saveEmployee = (e) =>{    //form은 기본 submit 이벤트를 막아 준다. = porpagation
        e.preventDefault();
        //JSON 으로 보내줘야 한다
        let employee = {firstName:firstName, lastName:lastName, emailId:emailId}
        console.log('전송값 : employee =>', JSON.stringify(employee));  //javascript의 객체를 JSON형태로 변경하고 서버에서는 @RequestBody를 통해서 JSON을 받는다

        EmployeeService.createEmployee(employee).then(res=>{
            navigate("/employees");
        })
    }

  return (
    <div className='container' style={{margin:"10px"}}>
        <div className='row'>
            <div className='card col-md-6 offset-md-3 offset-md-3'>
                <h3 className='text-center'>회원추가</h3>
                <form>
                    <div className='form-group'>
                        <label>First Name:</label>
                        <input className='form-control' name='firstName' placeholder='First Name' onChange={useChangeFirstNameHandler} />
                    </div>

                    <div className='form-group'>
                        <label>Last Name:</label>
                        <input className='form-control' name='lastName' placeholder='Last Name' onChange={useChangeLastNameHandler} />
                    </div>

                    <div className='form-group'>
                        <label>Email Id:</label>
                        <input className='form-control' name='emailId' placeholder='Email Id' onChange={useChangeEmailIdHandler} />
                    </div>

                    <div>
                        <button className='btn btn-danger' onClick={cancel} style={{marginLeft:"10px"}}>취소</button>
                        <button className='btn btn-success' onClick={saveEmployee}>입력</button>
                    </div>



                </form>
            </div>
        </div>
    </div>
  )
}

export default CreateEmployeeComponent