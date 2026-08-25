import { NavLink, useNavigate } from 'react-router-dom';
import { useEffect, useMemo, useState } from 'react';

const navItems = [
  { label: 'Overview', path: '/dashboard' },
  { label: 'Queues', path: '/queues' },
  { label: 'Jobs', path: '/jobs' },
  { label: 'Workers', path: '/workers' },
  { label: 'DLQ', path: '/dlq' },
];

function Sidebar() {
  const navigate = useNavigate();
  const [theme, setTheme] = useState(() => localStorage.getItem('theme') || 'dark');

  useEffect(() => {
    document.body.dataset.theme = theme;
    localStorage.setItem('theme', theme);
  }, [theme]);

  const title = useMemo(() => (theme === 'dark' ? 'Dark' : 'Light'), [theme]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <aside className="sidebar">
      <div className="brand-wrap">
        <div className="brand-mark">JS</div>
        <div>
          <div className="brand-name">Job Scheduler</div>
          <small>Ops Center</small>
        </div>
      </div>

      <nav className="sidebar-nav">
        {navItems.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
          >
            <span>{item.label}</span>
          </NavLink>
        ))}
      </nav>

      <div className="sidebar-footer">
        <button
          type="button"
          className="theme-toggle"
          onClick={() => setTheme((current) => (current === 'dark' ? 'light' : 'dark'))}
        >
          {title} mode
        </button>
        <button type="button" className="logout-button" onClick={handleLogout}>
          Sign out
        </button>
      </div>
    </aside>
  );
}

export default Sidebar;
