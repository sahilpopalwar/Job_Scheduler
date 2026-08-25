import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: 'admin', password: 'admin123' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await fetch('/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });

      if (!response.ok) {
        throw new Error('Invalid username or password');
      }

      const result = await response.json();
      localStorage.setItem('token', result.token || 'demo-token');
      navigate('/dashboard');
    } catch (submitError) {
      setError(submitError.message || 'Unable to sign in');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="login-header">
          <div className="brand-mark">JS</div>
          <div>
            <div className="brand-name">Job Scheduler</div>
            <small>Enterprise control plane</small>
          </div>
        </div>

        <h1>Welcome back</h1>
        <p>Sign in to the automation operations dashboard.</p>

        <form onSubmit={handleSubmit} className="login-form">
          <label>
            Username
            <input
              name="username"
              value={form.username}
              onChange={handleChange}
              placeholder="admin"
            />
          </label>

          <label>
            Password
            <input
              type="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              placeholder="admin123"
            />
          </label>

          {error && <div className="error-box">{error}</div>}

          <button type="submit" disabled={loading}>
            {loading ? 'Signing in...' : 'Sign in'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;
