'use client'
import React from 'react'

const AddToCart = () => {
  return (
    <div>
        <button className='btn btn-primary' onClick={()=> console.log('Click')}>Add to Card</button>
        <div className='bg-red-50 text-red-700 p-4 rounded'>
            빨간색 50 배경과 700 글자색
        </div>
    </div>
  )
}

export default AddToCart