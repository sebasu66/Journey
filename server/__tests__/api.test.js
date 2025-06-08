import request from 'supertest';
import server from '../server.js';

afterAll(() => {
  server.close();
});

test('GET /api/random-act returns result', async () => {
  const res = await request(server).get('/api/random-act');
  expect(res.status).toBe(200);
  expect(typeof res.body.result).toBe('string');
});

test('GET /api/time-cycle returns result', async () => {
  const res = await request(server).get('/api/time-cycle');
  expect(res.status).toBe(200);
  expect(typeof res.body.result).toBe('string');
});
