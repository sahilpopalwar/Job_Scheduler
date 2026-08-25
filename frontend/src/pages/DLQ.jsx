const deadLetters = [
  { id: 'DLQ-991', queue: 'Email', reason: 'Validation failed', retries: 5, firstSeen: '04:21 UTC' },
  { id: 'DLQ-994', queue: 'SMS', reason: 'Provider timeout', retries: 3, firstSeen: '05:11 UTC' },
  { id: 'DLQ-1002', queue: 'Reports', reason: 'Schema mismatch', retries: 2, firstSeen: '07:42 UTC' },
];

function DLQ() {
  return (
    <div>
      <div className="topbar">
        <div>
          <p className="eyebrow">Recovery</p>
          <h1>Dead letter queue</h1>
        </div>
      </div>

      <div className="panel full-width-panel">
        <div className="panel-header">
          <h3>Messages requiring review</h3>
          <span>{deadLetters.length} events</span>
        </div>

        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Queue</th>
              <th>Reason</th>
              <th>Retries</th>
              <th>First seen</th>
            </tr>
          </thead>
          <tbody>
            {deadLetters.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.queue}</td>
                <td>{item.reason}</td>
                <td>{item.retries}</td>
                <td>{item.firstSeen}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default DLQ;
