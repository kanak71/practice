import React, { useEffect, useState } from 'react'
import EmployeeService from '../services/EmployeeService';

function ListEmployeeComponent() {

    const [employees,setEmployees] = useState([]);

    //useEffect 함수는 React 컴포넌트가 렌더링 될 때 마다 틀정 작업을 실행 할 수 있도록 하는 hook
    //[]없으면 계속해서 렌더링을 한다. []빈 배열의 의미는 처음 한번만 실행
    useEffect(()=>{
        //axios를 통해서 값 생성
        EmployeeService.getEmployees().then((res)=>{
            setEmployees(res.data);
        });


    },[]);
  return (
    <div>
        <h2 className='text-center'>Employees List</h2>
        <div className='row'>
            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>Employee First Name</th>
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
                                    <td>{employee.firstName}</td>
                                    <td>{employee.lastName}</td>
                                    <td>{employee.emailId}</td>
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