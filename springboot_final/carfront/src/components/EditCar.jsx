import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import React, { useState } from 'react'

const EditCar = (props) => {

    // 모달의 처음 상태 값 
    const [open, setOpen] = useState(false);

    // 자동차의 정보를 담는 객체
    const [car,setCar] = useState({
            brand: '',
            model: '',
            color: '',
            price: '',
            registerNumber: '',
            productYear: '',
        });

    // 모달 창 오픈
    const handleClikOpen = ()=>{
        console.log("props로 전달받은 값 :", props);
        // props로 전달받은 값을 useState의 객체에 전달
        setCar({
            brand: props.data.row.brand,
            model: props.data.row.model,
            color: props.data.row.color,
            price: props.data.row.price,
            registerNumber: props.data.row.registerNumber,
            productYear: props.data.row.productYear,
        })
        setOpen(true);
    }

    // 모달이 닫길때 이전 값을 가지고 있기 때문에 초기화 해주는 함수
    const handleClinkClose = () =>{
        setOpen(false);
    }

    // 화면에서 input의 값을 입력한다 => 글을입력 변화를 감지해서 useState => onChange
    const handleChange = (event)=>{
        setCar({...car, [event.target.name]:event.target.value}); // ... 스프레드 표기법을 사용한다. 이전에 있는 값은 유지하고, 새로운 값은 추가 
    }

    const handleSave = ()=>{
        props.updateCar(car, props.data.id);
        handleClinkClose();
    }

    return (
        <div>
            <Button onClick={handleClikOpen}>자동차 수정</Button>
            <Dialog open={open} onClose={handleClinkClose} disableRestoreFocus>
                <DialogTitle>자동차 정보 수정</DialogTitle>
                <DialogContent>
                    <input name="brand" placeholder='브렌드' value={car.brand} onChange={handleChange}/><br/>
                    <input name="model" placeholder='모델'  value={car.model} onChange={handleChange}/>    <br/>
                    <input name="color" placeholder='색상' value={car.color} onChange={handleChange}/>    <br/>
                    <input name="price" placeholder='가격' value={car.price} onChange={handleChange}/>    <br/>
                    <input name="registerNumber" placeholder='등록번호' value={car.registerNumber} onChange={handleChange}/>    <br/>
                    <input name="productYear" placeholder='년식' value={car.productYear} onChange={handleChange}/>        <br/>
                </DialogContent> 
                <DialogActions>
                    <Button onClick={handleClinkClose}>취소</Button>
                    <Button onClick={handleSave}>저장</Button>
                </DialogActions>           
            </Dialog>
        </div>
    )
}

export default EditCar