function MetricCard({ label, value, change, tone = 'primary' }) {
  return (
    <div className="metric-card">
      <div className="metric-header">
        <span>{label}</span>
        <span className={`trend ${tone}`}>{change}</span>
      </div>
      <div className="metric-value">{value}</div>
    </div>
  );
}

export default MetricCard;
