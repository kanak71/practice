import React, { useEffect, useState } from 'react'
import EmployeeService from '../services/EmployeeService';
import { Link, useNavigate } from 'react-router-dom';

function ListEmployeeComponent() {

    const navigate = useNavigate();

    const [employees,setEmployees] = useState([]);

    const addEmployee = ()=>{
        // useNavigate()를 통해서 React에서는 component를 전환시킨다
        navigate("/add-employee");
    }

    const viewEmployee = (id) =>{
        navigate(`/view-employee/${id}`)
    }

    //useEffect 함수는 React 컴포넌트가 렌더링 될 때 마다 틀정 작업을 실행 할 수 있도록 하는 hook
    //[]없으면 계속해서 렌더링을 한다. []빈 배열의 의미는 처음 한번만 실행
    useEffect(()=>{
        //axios를 통해서 값 생성
        EmployeeService.getEmployees().then((res)=>{
            setEmployees(res.data);
        });


    },[]);

    const deleteEmployee= (id) =>{
        console.log("전달받은 ID 값:", id);

        // 서비스를 호출해서 REST API로 삭제를 진행
        EmployeeService.deleteEmployee(id)
            .then(res=>{
                //삭제된 직원의 목록(employees state)에서 값을 삭제해준다
                //employees의 배열에서 filter를 통해서 삭제
                //삭제 후에 다시 employees에 담아주면 자동으로 react의 화면은 변경된다
                setEmployees(employees.filter(employee => employee.id !== id))
            })
    }

  return (
    <div>
        <h2 className='text-center'>Employees List</h2>
        <div>
            <button className='btn btn-primary' onClick={addEmployee}>직원추가</button>
        </div>
        <div className='row'>
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Employee First Name a태그</th>
                        <th>Employee First Name Link태그</th>
                        <th>Employee Last Name</th>
                        <th>Employee Email Id</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        employees.map(
                            employee => 
                                <tr key={employee.id}>
                                    <td><a href={'/view-employee/'+employee.id}>{employee.firstName}</a></td>
                                    <td><Link to={'/view-employee/'+employee.id}>{employee.firstName}</Link></td>
                                    <td>{employee.lastName}</td>
                                    <td>{employee.emailId}</td>
                                    <td>
                                        <button className='btn btn-success' onClick={()=> viewEmployee(employee.id)}>상세</button>
                                        <button className='btn btn-danger' style={{marginLeft:"25px"}} onClick={()=> deleteEmployee(employee.id)}>삭제</button>
                                    </td>
                                </tr>
                        )
                    }
                </tbody>
            </table>

        </div>
    </div>
  )
}

export default ListEmployeeComponent