import React from 'react';
import { Outlet, Link } from 'react-router-dom';

const PaginationLayout = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh', backgroundColor: '#f8f9fa' }}>
      
      {/* 새로운 심플 헤더 */}
      <header style={{ background: '#343a40', color: 'white', padding: '1rem' }}>
        <div className="container d-flex justify-content-between align-items-center">
          <h4 style={{ margin: 0 }}>Pagination Practice</h4>
          <nav>
            <Link to="/" style={{ color: '#adb5bd', textDecoration: 'none', marginRight: '15px' }}>Home</Link>
            <Link to="/page" style={{ color: 'white', textDecoration: 'none', fontWeight: 'bold' }}>Practice Area</Link>
          </nav>
        </div>
      </header>

      {/* 가변적인 콘텐츠 영역 */}
      <main style={{ flex: 1, padding: '40px 0' }}>
        <div className="container">
          <Outlet />
        </div>
      </main>

      {/* 새로운 심플 푸터 */}
      <footer style={{ background: '#e9ecef', padding: '20px 0', borderTop: '1px solid #dee2e6' }}>
        <div className="container text-center text-muted">
          <small>© 2026 Pagination Lab - Page State Training</small>
        </div>
      </footer>
      
    </div>
  );
};

export default PaginationLayout;