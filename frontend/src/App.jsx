import { Navigate, Route, Routes } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Dashboard from './pages/Dashboard';
import Jobs from './pages/Jobs';
import Login from './pages/Login';
import Queues from './pages/Queues';
import Workers from './pages/Workers';
import DLQ from './pages/DLQ';

function ProtectedRoute({ children }) {
  const token = localStorage.getItem('token');
  return token ? children : <Navigate to="/login" replace />;
}

function AppLayout({ children }) {
  return (
    <div className="app-shell">
      <Sidebar />
      <main className="content-panel">{children}</main>
    </div>
  );
}

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />

      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <AppLayout>
              <Dashboard />
            </AppLayout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/queues"
        element={
          <ProtectedRoute>
            <AppLayout>
              <Queues />
            </AppLayout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/jobs"
        element={
          <ProtectedRoute>
            <AppLayout>
              <Jobs />
            </AppLayout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/workers"
        element={
          <ProtectedRoute>
            <AppLayout>
              <Workers />
            </AppLayout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/dlq"
        element={
          <ProtectedRoute>
            <AppLayout>
              <DLQ />
            </AppLayout>
          </ProtectedRoute>
        }
      />

      <Route path="/" element={<Navigate to="/dashboard" replace />} />
      <Route path="*" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
}

export default App;
