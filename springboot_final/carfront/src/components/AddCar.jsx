import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import React, { useState } from 'react'

const AddCar = (props) => {

    const [open, setOpen] = useState(false);
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
        setOpen(true);
    }

    // 모달이 닫길때 이전 값을 가지고 있기 때문에 초기화 해주는 함수
    const handleClinkClose = () =>{
        setOpen(false);
        setCar({
            brand: '',
            model: '',
            color: '',
            price: '',
            registerNumber: '',
            productYear: '',
        });
    }

    // 저장을 하기 위한 : props를 통한 저장 실행
    const handleClickSave = ()=>{
        props.addCar(car);
        handleClinkClose();
    }

    // 화면에서 input의 값을 입력한다 => 글을입력 변화를 감지해서 useState => onChange
    const handleChange = (event)=>{
        setCar({...car, [event.target.name]:event.target.value}); // ... 스프레드 표기법을 사용한다. 이전에 있는 값은 유지하고, 새로운 값은 추가 
    }

    return (
        <div>
            <Button onClick={handleClikOpen}>자동차 등록</Button>
            <Dialog open={open} onClose={handleClinkClose} disableRestoreFocus>
                <DialogTitle>자동차 등록 정보</DialogTitle>
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
                    <Button onClick={handleClickSave}>저장</Button>
                </DialogActions>           
            </Dialog>

        </div>
    )
}

export default AddCar