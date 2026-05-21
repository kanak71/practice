import React, { useEffect, useState } from 'react'
import { DataGrid } from '@mui/x-data-grid'
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import AddCar from './AddCar';
import EditCar from './EditCar';

const CarList = () => {


    // x-data-grid를 통한 Column의 모양 처리
    // UI 프레임웍들은 구성되어 있는 형태로만 javascript 객체를 작성하면 자동으로 그려준다
    const columns = [
        {field:'brand', headerName:'브렌드', flex:1, headerAlign:'center'},
        {field:'color', headerName:'색상', flex:1, headerAlign:'center'},
        {field:'model', headerName:'모델', flex:1, headerAlign:'center'},
        {field:'productYear', headerName:'년식', flex:1, headerAlign:'center'},
        {field:'price', headerName:'가격', flex:1, headerAlign:'center'},
        {field:'registerNumber', headerName:'등록번호', flex:1, headerAlign:'center'},
        {field:'_links.self.href', headerName:'' ,
            sortable: false,
            filterable: false,
            renderCell : row =>
                <Button onClick={()=>{
                    console.log(row);
                    onDeleteClick(row.id);
                }}>
                    삭제
                </Button>
        },
        {field: '_links.car.href', headerName:'',
            sortable:false,
            filterable:false,
            disableColumnMenu: true, // 열 메뉴 아이콘을 비활성화
            renderCell : row =>
                <EditCar data={row} updateCar = {updateCar}/> // 부모로 부터 자식에게 전달하는 props, 현재 row의 값 updateCar 수정하는 함수 전달
            //    console.log(row)
        }
    ]


    // React에 모든 것들 "함수 리터럴"로 동작, 많이 사용하는 문법 Arrow function(익명함수)이다
    //화면에서 값을 정의 할 객체 : useState 객체
    // state는 반드시 초기화 해줘야한다. 
    // 단일값은 pirmitive : "", 0, false...
    // 객체값(여러개들어가 있는 것) ; { key1:"", key2:false}
    // Array값 : [] 
    const [cars, setCars] = useState([]);
    const [open, setOepn ] = useState(false); // 알림을 위한 <SnackBar>

    //4) 수정 정보를 서에 요청
    const updateCar = (car, link) =>{
        const token = sessionStorage.getItem('jwt')
        console.log(car, link);
        fetch(link,{
            method:'put',
            headers : {'Content-Type':'application/json', "Authorization":token},
            body: JSON.stringify(car)
        })
        .then(response =>{
            if(response.ok){
                fetchCars()
            }else{
                alert("수정이 실패하였습니다")
            }
        })
        .catch(error => console.log(error))

    }


    //3) 자동차 정보를 서버에 요청
    const addCar = (car) =>{
        const token = sessionStorage.getItem('jwt')
        console.log(JSON.stringify(car))
        fetch('http://localhost:8080/api/vehicles',
            {
                method:'post',
                headers: {'Content-Type':'application/json', "Authorization":token},
                body: JSON.stringify(car),
            }
        )
        .then(response => {
            if(response.ok){
                fetchCars();
            }else{
                alert("입력에 실패하셨습니다")
            }
        })
        .catch(error => console.log(error))
    }


    //2) 삭제 주소(URL)을 통해서 서버에 요청
    const onDeleteClick = (url) =>{
        const token = sessionStorage.getItem('jwt')
        fetch(url,{
            method:'DELETE',
             headers:{
                "Authorization":token
                }
            })
        .then(response =>{ // stuts코드를 통해서 확인
            if(response.ok){
                fetchCars()   // REST API요청에 따라 삭제가 되면 새로운 리스트를 받는다
                setOepn(true); // 삭제 알람을 알려줌
            }else{
                alert("삭제를 실패하셨습니다"); // 실패 할 경우 alert 메시지를 작성
            }
        }) 
        //.then()
        //.catch()
    }


    //1) 화면 첫번째 렌더링 시 Cars전체조회  
    // AI, Spring REST API는 조회시 size 20개를 기준으로 한다
    // 조회시 size를 1000입력 해줘한다
    const fetchCars = () =>{
        const token = sessionStorage.getItem('jwt')
        fetch("http://localhost:8080/api/vehicles?size=1000", 
            {
                headers:{"Authorization":token}
            }
        )  // 요청 보내는것 => Postman의 결과를 반드시 확인(Spring REST API 결과(data)는 _embeded)
        .then(response => response.json())  // Promise 객체를 형변환 .json() , .text()
        .then(data => setCars(data._embedded.cars))  // 결과는 처리하는 곳 // Promise 객체는 data라는 이름으로 결과 담아 준다
        .catch(err => console.error(err)) // 오류가 발생 했을 때 처리되는 곳
    }


    //화면이 렌더링이 되면 실행되어 API 서버를 요청 useEffect(hook) 작성
    useEffect(()=>{
        fetchCars();
    },[]);
    // useEffect의 두번째 입력값은 useEffect가 동작되는 조건
    // [] :화면이 렌더링 될때 한번
    // cars : cars의 useState값이 변경되었을때 다시 서버를 요청 



    // 값들을 바인딩하여 출력할 영역,  {}=>javascript값이 렌더링된 영역
    // Array의 값을 출력 .map을 사용한다 
    // .map((item,index) => ()) , 자동으로 향상된 for문 처럼 객체가 한개 나오고 index도 자동으로 만들어 진다
    // return의 바인딩에서 ; 없고, {} 없다 
    // return의 모든 값을 HTML의 기준으로 작성해야 한다 => 열림과 닫힘 ex) <br> 안되요 <br/> , <input > 안되요 <input /> ☞ Empty Element
    // table을 만들거나 리스트 출력할때, row의 기준이되는 key 작성해야 한다. index값을 사용하거나 id 값을 사용 => SPA(Single Page Application) 구성
    return (
        <div style={{ height: 700, width: '100%' }}>
            <div style={{ marginBottom:'30px'}}>
                {/* CarList.jsx 에 있는 서버요청을 처리하기 위한 값을 전달받기 위해서 Props로 전달한다  */}
                <AddCar addCar={addCar}/> 
            </div>
            <DataGrid 
                rows={cars} // 표에 사용될 데이터
                columns={columns} // 표의 열정의, field는 데이터에의 해당 열의 값, headerName 헤더에 표실될 텍스트
                getRowId={row => row._links.self.href} //행(row)의 고유한 식별자를 가져오는 함수를 지정
                 pageSizeOptions={[10, 20, 50]}
                    initialState={{
                        pagination: {
                            paginationModel: {
                                pageSize: 10,
                            },
                        },
                    }}
            // Element의 콘텐츠가 없는 경우 Empty Element를 사용하여 닫힘을 작성한다 
            /> 

            <Snackbar
                open={open}
                autoHideDuration={2000}
                onClose={()=> setOepn(false)}
                message ="선택한 자동차가 삭제되었습니다"
            />
           
        </div>
    )
}

export default CarList