const BASE_URL = '';

async function request(path, options = {}) {
  const token = localStorage.getItem('token');
  const headers = {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...(options.headers || {}),
  };

  const response = await fetch(`${BASE_URL}${path}`, {
    credentials: 'include',
    ...options,
    headers,
  });

  if (!response.ok) {
    const text = await response.text().catch(() => '');
    throw new Error(text || `Request failed with status ${response.status}`);
  }

  if (response.status === 204) return null;
  const contentType = response.headers.get('content-type') || '';
  return contentType.includes('application/json') ? response.json() : response.text();
}

export default {
  login: ({ username, password }) => request('/auth/login', {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  }),
  getJobs: () => request('/jobs'),
  getWorkers: () => request('/workers'),
  getQueues: () => request('/organizations'),
  getDeadLetters: () => request('/dead-letter-queue'),
};
