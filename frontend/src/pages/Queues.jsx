const queueRows = [
  { name: 'Email Queue', priority: 'P1', status: 'ACTIVE', throughput: '4.1K/hr', workers: 21 },
  { name: 'SMS Queue', priority: 'P2', status: 'PAUSED', throughput: '1.2K/hr', workers: 11 },
  { name: 'Analytics Queue', priority: 'P1', status: 'ACTIVE', throughput: '3.6K/hr', workers: 18 },
  { name: 'Report Queue', priority: 'P3', status: 'DRAINING', throughput: '780/hr', workers: 7 },
];

function Queues() {
  return (
    <div>
      <div className="topbar">
        <div>
          <p className="eyebrow">Infrastructure</p>
          <h1>Queues</h1>
        </div>
      </div>

      <div className="panel full-width-panel">
        <div className="panel-header">
          <h3>Queue inventory</h3>
          <span>{queueRows.length} active queues</span>
        </div>

        <table className="data-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Priority</th>
              <th>Status</th>
              <th>Throughput</th>
              <th>Workers</th>
            </tr>
          </thead>
          <tbody>
            {queueRows.map((queue) => (
              <tr key={queue.name}>
                <td>{queue.name}</td>
                <td>{queue.priority}</td>
                <td>
                  <span className={`status-badge ${queue.status.toLowerCase()}`}>{queue.status}</span>
                </td>
                <td>{queue.throughput}</td>
                <td>{queue.workers}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Queues;
