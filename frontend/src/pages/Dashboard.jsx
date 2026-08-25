import { useEffect, useMemo, useState } from 'react';
import {
  Area,
  AreaChart,
  Bar,
  BarChart,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from 'recharts';
import MetricCard from '../components/MetricCard';

const trendData = [
  { name: 'Mon', jobs: 240, latency: 180 },
  { name: 'Tue', jobs: 340, latency: 210 },
  { name: 'Wed', jobs: 280, latency: 195 },
  { name: 'Thu', jobs: 420, latency: 240 },
  { name: 'Fri', jobs: 390, latency: 220 },
  { name: 'Sat', jobs: 460, latency: 250 },
  { name: 'Sun', jobs: 520, latency: 270 },
];

const queueHealth = [
  { name: 'Email', value: 82 },
  { name: 'SMS', value: 71 },
  { name: 'Analytics', value: 91 },
  { name: 'Reports', value: 68 },
];

const jobsTable = [
  { id: 'JOB-2048', queue: 'Email', status: 'RUNNING', owner: 'Ops', latency: '03s' },
  { id: 'JOB-2049', queue: 'Reports', status: 'QUEUED', owner: 'BI', latency: '12s' },
  { id: 'JOB-2051', queue: 'SMS', status: 'RETRY', owner: 'Alerts', latency: '21s' },
  { id: 'JOB-2054', queue: 'Analytics', status: 'DONE', owner: 'Data', latency: '00s' },
];

function Dashboard() {
  const [metrics, setMetrics] = useState({
    totalJobs: '12.4K',
    successRate: '99.94%',
    workersOnline: '184',
    backlog: '1.2K',
  });

  useEffect(() => {
    const timer = setInterval(() => {
      setMetrics((current) => ({
        totalJobs: String(Number.parseFloat(current.totalJobs) + 0.3).toFixed(1) + 'K',
        successRate: '99.96%',
        workersOnline: String(Number(current.workersOnline) + 1),
        backlog: String(Number.parseFloat(current.backlog) + 0.2).toFixed(1) + 'K',
      }));
    }, 8000);

    return () => clearInterval(timer);
  }, []);

  const queueBars = useMemo(
    () => queueHealth.map((queue) => ({ ...queue, fill: queue.value > 80 ? '#4ade80' : '#fbbf24' })),
    []
  );

  return (
    <div>
      <div className="topbar">
        <div>
          <p className="eyebrow">Operations dashboard</p>
          <h1>Automation control center</h1>
        </div>
        <button type="button" className="primary-button">
          Trigger workflow
        </button>
      </div>

      <div className="metrics-grid">
        <MetricCard label="Jobs processed" value={metrics.totalJobs} change="+12.4%" tone="positive" />
        <MetricCard label="Success rate" value={metrics.successRate} change="+0.82%" tone="positive" />
        <MetricCard label="Workers online" value={metrics.workersOnline} change="+8" tone="primary" />
        <MetricCard label="Queue backlog" value={metrics.backlog} change="-3.1%" tone="warning" />
      </div>

      <div className="chart-grid">
        <div className="panel">
          <div className="panel-header">
            <h3>Throughput trend</h3>
            <span>Last 7 days</span>
          </div>
          <div className="chart-box">
            <ResponsiveContainer width="100%" height="100%">
              <AreaChart data={trendData}>
                <defs>
                  <linearGradient id="jobsFill" x1="0" x2="0" y1="0" y2="1">
                    <stop offset="5%" stopColor="#58a6ff" stopOpacity={0.8} />
                    <stop offset="95%" stopColor="#58a6ff" stopOpacity={0} />
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" stroke="rgba(148,163,184,0.15)" />
                <XAxis dataKey="name" stroke="#94a3b8" />
                <YAxis stroke="#94a3b8" />
                <Tooltip />
                <Area type="monotone" dataKey="jobs" stroke="#58a6ff" fill="url(#jobsFill)" />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="panel">
          <div className="panel-header">
            <h3>Latency profile</h3>
            <span>Avg ms</span>
          </div>
          <div className="chart-box small">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={trendData}>
                <CartesianGrid strokeDasharray="3 3" stroke="rgba(148,163,184,0.15)" />
                <XAxis dataKey="name" stroke="#94a3b8" />
                <YAxis stroke="#94a3b8" />
                <Tooltip />
                <Bar dataKey="latency" fill="#a78bfa" radius={[6, 6, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>

      <div className="lower-grid">
        <div className="panel">
          <div className="panel-header">
            <h3>Queue health</h3>
            <span>Live</span>
          </div>
          <div className="queue-list">
            {queueBars.map((queue) => (
              <div key={queue.name} className="queue-row">
                <div className="queue-meta">
                  <span>{queue.name}</span>
                  <strong>{queue.value}%</strong>
                </div>
                <div className="bar-track">
                  <div className="bar-fill" style={{ width: `${queue.value}%`, background: queue.fill }} />
                </div>
              </div>
            ))}
          </div>
        </div>

        <div className="panel">
          <div className="panel-header">
            <h3>Recent activity</h3>
            <span>Updated 30s ago</span>
          </div>
          <table className="data-table">
            <thead>
              <tr>
                <th>Job</th>
                <th>Queue</th>
                <th>Status</th>
                <th>Latency</th>
              </tr>
            </thead>
            <tbody>
              {jobsTable.map((job) => (
                <tr key={job.id}>
                  <td>{job.id}</td>
                  <td>{job.queue}</td>
                  <td>
                    <span className={`status-badge ${job.status.toLowerCase()}`}>{job.status}</span>
                  </td>
                  <td>{job.latency}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
