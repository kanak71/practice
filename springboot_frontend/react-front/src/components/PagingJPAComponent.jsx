import React, { useEffect, useState } from 'react'
import PageService from '../services/PageService'


const PagingJPAComponent = () => {

    const [currentPage,setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(5);

    useEffect(()=>{
        PageService.getPage(currentPage, pageSize).then(res=>{
            console.log("JPA Page 객체 :", res.data);
        })
    }, []);

  return (
    <div></div>
  )
}

export default PagingJPAComponent