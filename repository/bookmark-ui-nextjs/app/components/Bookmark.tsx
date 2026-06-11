import React from 'react'
// 컴포넌트의 이름과 타입의 이름이 같다면 import 시 Confilct 가 발생한다
// import에서 type 을 지정하면 import 타입을 정의 된다
import type { Bookmark } from '../types/bookmark'
import Link from 'next/link';


// 전달받은 Props 타입 정의
interface BookmarkProps{
    bookmark: Bookmark;
}

const Bookmark = ({ bookmark } : BookmarkProps) => {
  return (
      <div className="card shadow-sm mb-3 border-0">
      <div className="card-body">
        <div className="d-flex justify-content-between align-items-start">
          <div>
            <h5 className="card-title mb-1">
              <Link
                href={bookmark.url}
                className="text-decoration-none"
                target="_blank"
              >
                {bookmark.title}
              </Link>
            </h5>
          </div>
          <small className="text-secondary">
           {bookmark.url}
          </small>
        </div>
      </div>
    </div>
  )
}

export default Bookmark