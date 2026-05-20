import React, { useEffect, useState } from 'react'
import { DataGrid } from '@mui/x-data-grid'

const CarList = () => {

    const columns = [
        {field:'brand', headerName:'브렌드', width: 200, headerAlign:'center'},
        {field:'color', headerName:'색상', width: 200, headerAlign:'center'},
        {field:'model', headerName:'모델', width: 200, headerAlign:'center'},
        {field:'productYear', headerName:'년식', width: 200, headerAlign:'center'},
        {field:'price', headerName:'가격', width: 200, headerAlign:'center'},
        {field:'registerNumber', headerName:'등록번호', width: 200, headerAlign:'center'},
    ]

    //React에 모든 것들 :함수 리터럴"로 동작. 많이 사용하는 혼합? Arrow function(익명함수)이다
    //화면에서 값을 정의 할 객체 : useState 객체
    //state 객체는 반드시 초기화 해줘야한다. 단일값은 pirmitive : "",0,false...
    //객체값(여러개가 들어가 있는 것); {key1;"", key2:false}
    //Array값 : []
    const [cars, setCars] = useState([]);

    //화면이 렌더링이 되면 실행되어 API 서버를 요청 useEffect(hook) 작성
    useEffect(()=>{
        fetch("http://localhost:8080/api/vehicles")     //요청 보내는 것 => Postman의 결과를 반드시 확인(Spring REST API 결과(data)는 _embedded)
        .then(response => response.json())     //Promise객체를 형변환. json(), .text()
        .then(data => setCars(data._embedded.cars))     //결과를 처리하는 곳    //Promise 객체는 data라는 이름으로 결과 담아준다
        .catch(err => console.error(err))    //오류가 발생했을 때 처리되는 곳
    },[]);

    //값들을 바인딩하여 출력할 영역 {}=>javascript값이 렌더링된 영역 
    //Array의 값을 출력 .map을 사용한다
    // .map((item,index)=>()), 자동으로 향상된 for문처럼 객체가 한개 나오고 index도 자동으로 만들어진다
    //return의 바인딩에서 ; 없고, {}없다
    //return의 모든 값을 HTML의 기준으로 작성해야 한다
    //table을 만들거나 리스트 출력할때 row의 기준이 되는 key 작성해야 한다. index값을 사용하거나 id 값을 사용 =>SPA(Single Page Application) 구성
  return (
    <div>
        <DataGrid
            row={cars}  //표에 사용될 데이터
            columns={columns}   //표의 열정의, field 데이터에의 해당 열의 값, headerName 헤더에 표시될 텍스트
            getRowId={row => row._links.self.href}

        ></DataGrid>
    </div>
  )
}

export default CarList