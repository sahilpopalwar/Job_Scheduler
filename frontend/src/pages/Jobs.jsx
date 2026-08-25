const jobs = [
  { jobId: 'JOB-2101', queue: 'Email', status: 'RUNNING', attempts: 2, nextRun: '00:03:14' },
  { jobId: 'JOB-2102', queue: 'Reports', status: 'QUEUED', attempts: 0, nextRun: '00:12:51' },
  { jobId: 'JOB-2108', queue: 'SMS', status: 'RETRY', attempts: 5, nextRun: '00:05:12' },
  { jobId: 'JOB-2113', queue: 'Analytics', status: 'DONE', attempts: 1, nextRun: 'Completed' },
];

function Jobs() {
  return (
    <div>
      <div className="topbar">
        <div>
          <p className="eyebrow">Operations</p>
          <h1>Jobs</h1>
        </div>
      </div>

      <div className="panel full-width-panel">
        <div className="panel-header">
          <h3>Execution log</h3>
          <span>{jobs.length} active items</span>
        </div>

        <table className="data-table">
          <thead>
            <tr>
              <th>Job ID</th>
              <th>Queue</th>
              <th>Status</th>
              <th>Attempts</th>
              <th>Next run</th>
            </tr>
          </thead>
          <tbody>
            {jobs.map((job) => (
              <tr key={job.jobId}>
                <td>{job.jobId}</td>
                <td>{job.queue}</td>
                <td>
                  <span className={`status-badge ${job.status.toLowerCase()}`}>{job.status}</span>
                </td>
                <td>{job.attempts}</td>
                <td>{job.nextRun}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Jobs;
