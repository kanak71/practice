import React from 'react'
import HeaderComponent from './HeaderComponent'
import '../css/FooterComponentStyle.css'
import styles from '../css/FooterComponent.module.css'

function FooterComponent() {
  return (
    <footer className='footer'>
      <span className={`text-white footer-font ${styles.footerBg}`}>
        멋쟁이 사자처럼
      </span>
    </footer>
  )
}

export default FooterComponent