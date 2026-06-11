import React from 'react'

const NavBar = () => {
  return (
    <div className="container">
      <header>
        <nav className="navbar navbar-expand-sm bg-dark navbar-dark">
          <div className="container-fluid">
            <a className="navbar-brand" href="#">Logo</a>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="collapsibleNavbar">
              {/* ms-auto: Bootstrap의 유틸리티 클래스 중 하나로, "margin start auto"를 의미합니다. 이는 해당 요소를 가능한 한 오른쪽으로 정렬합니다. (Bootstrap 5부터 사용됨) */}
              <ul className="navbar-nav ms-auto">
                <li className="nav-item">
                  <a className="nav-link" href="#">Link</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
      </header>
      <main>
        <h2>Welcome to Bookmark</h2>
      </main>
    </div>
  )
}

export default NavBar