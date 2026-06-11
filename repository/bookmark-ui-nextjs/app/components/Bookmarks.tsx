import React from 'react'
import { BookmarksResponse } from '../types/bookmark'
import Bookmark from './Bookmark';

//Props 타입의 정의
interface BookmarksProps{
    bookmarks: BookmarksResponse;
}


const Bookmarks = ({ bookmarks }: BookmarksProps) => {
  return (
    <div>
        {
          bookmarks.data.map(bookmark => <Bookmark key={bookmark.id} bookmark={bookmark}/>  )
        }
    </div>
  )
}

export default Bookmarks