import React from 'react'

function HeaderComponent() {
  return (
    <>
        <header className='header-container' style={{ border:'4px dotted yellow'}}>
            <nav className='navbar navbar-expand-md navbar-dark bg-dark'>
                <div>
                    <a href="https://www.google.com" className='navbar-brand'>React Academy</a>
                </div>

            </nav>

        </header>
    </>
  )
}

export default HeaderComponent