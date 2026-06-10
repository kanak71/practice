import React from 'react'
import AddToCart from './AddToCart'
import styles from './ProductCard.module.css'

// ProductCard는 onClick 이벤트가 있기 때문에 반드시 'use client' 가 선언되어야한다
const ProductCard = () => {
  return (
    <div className={styles.cardContainer}>
        <AddToCart />
    </div>
  )
}

export default ProductCard