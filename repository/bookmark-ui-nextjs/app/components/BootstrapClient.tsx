'use client'
import React, { useEffect } from 'react'

const BootstrapClient = () => {

    useEffect(()=>{
        import('bootstrap/dist/js/bootstrap.min.js');
    });
    return null;
}
export default BootstrapClient