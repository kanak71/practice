'use client'

import { useRouter } from 'next/navigation'
import React, { useState } from 'react'

const SearchForm = () => {

    const router = useRouter();
    const [query, setQuery] = useState('');

    const handleSearch = async (e:React.SyntheticEvent)=>{
        e.preventDefault();
        if(query == ""){
            router.push('/bookmarks');
            return;
        }

        router.push(`/bookmarks?page=1&query=${query}`)
    }

  return (
    <div className="pb-4">
            <form className="d-flex" role="search" onSubmit={handleSearch}>
                <input className="form-control me-2" type="search" placeholder="Search" value={query} onChange={e => setQuery(e.target.value)}/>
                <button className="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div> 
  )
}

export default SearchForm