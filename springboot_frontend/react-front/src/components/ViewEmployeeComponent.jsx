import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import EmployeeService from '../services/EmployeeService'

const ViewEmployeeComponent = () => {

    const navigate = useNavigate();

    //라우터에 요청된 주소의 값을 가져오기 위해서 useParams를 사용한다
    const{ id } = useParams();  

    //useState()에 employee 선언, []로 초기화한다. => 초기화하지 않으면 undefinded가 된다
    const [employee, setEmployee] = useState([]);

    const updateFormEmployee = (id) =>{
        navigate(`/update-employee/${id}`);
    }

    //useEffect()를 통해서 컴포넌트 호출 전에 화면에 뿌려질 값을저장
    useEffect(()=>{
        console.log("param값 :", id);
        EmployeeService.getEmployeeById(id).then(res=>{
            setEmployee(res.data);  //axios에서 Promise의 값을 data에 담겨 있다
        })
    },[]);

    //화면이 한번만 로드되도록 빈 배열을 입력해준다. 만약에 변화될 때 마다 로드가 되길 원한다면 해당 객체를 넣어주면 된다

    const deleteEmployee = (id) =>{
        console.log("회원삭제 ID", id);

        EmployeeService.deleteEmployee(id)
            //axios의 받은 데이터는 data에 들어가 있다
            //Map으로 반환해서 JSON으로 변경되었기 때문에, data.키이름
            //ex) res.data.deleted
            .then(res=>{
                console.log(res.data.deleted);
                if(res.data.deleted == true){
                    alert("회원이 삭제되었습니다");
                    navigate("/")
                }else{
                    alert("회원 삭제 실패하셨습니다");
                    return false;
                }
                
            })
            
    }

  return (
    <div>
        <br/>
        <br/>
        <div className='card col-md-6 offset-md-3'>
            <h3 className='text-center'>직원 상세</h3>
            <div className='card-body'>

                <div className='form-group bg-light'>
                    <label>First Name</label>
                    <span className='form-control'>{employee.firstName}</span>
                </div>

                <div className='form-group bg-light'>
                    <label>Last Name</label>
                    <span className='form-control'>{employee.lastName}</span>
                </div>

                <div className='form-group bg-light'>
                    <label>Email Id</label>
                    <span className='form-control'>{employee.emailId}</span>
                </div>
                <div className='text-center'>
                    <button className='btn btn-danger' onClick={()=>updateFormEmployee(employee.id)}>정보수정</button>
                    <button className='btn btn-info' onClick={() => deleteEmployee(employee.id)}>정보삭제</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default ViewEmployeeComponent