const workers = [
  { host: 'worker-01', region: 'us-east-1', status: 'HEALTHY', load: 68, capacity: 12 },
  { host: 'worker-02', region: 'us-east-1', status: 'BUSY', load: 86, capacity: 14 },
  { host: 'worker-03', region: 'us-west-2', status: 'DRAINING', load: 42, capacity: 9 },
  { host: 'worker-04', region: 'eu-central-1', status: 'HEALTHY', load: 59, capacity: 11 },
];

function Workers() {
  return (
    <div>
      <div className="topbar">
        <div>
          <p className="eyebrow">Execution</p>
          <h1>Workers</h1>
        </div>
      </div>

      <div className="panel full-width-panel">
        <div className="panel-header">
          <h3>Worker fleet</h3>
          <span>{workers.length} nodes online</span>
        </div>

        <table className="data-table">
          <thead>
            <tr>
              <th>Host</th>
              <th>Region</th>
              <th>Status</th>
              <th>Load</th>
              <th>Capacity</th>
            </tr>
          </thead>
          <tbody>
            {workers.map((worker) => (
              <tr key={worker.host}>
                <td>{worker.host}</td>
                <td>{worker.region}</td>
                <td>
                  <span className={`status-badge ${worker.status.toLowerCase()}`}>{worker.status}</span>
                </td>
                <td>{worker.load}%</td>
                <td>{worker.capacity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Workers;
