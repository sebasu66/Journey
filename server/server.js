import express from 'express';
import { WebSocketServer } from 'ws';
import fs from 'fs';
import swaggerUi from 'swagger-ui-express';
import swaggerSpec from './swagger.js';

const randomActs = JSON.parse(fs.readFileSync(new URL('./data/randomActs.json', import.meta.url)));
const timeCycles = JSON.parse(fs.readFileSync(new URL('./data/timeCycles.json', import.meta.url)));

const app = express();
app.use(express.json());
app.use('/docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

let extensionSocket = null;
const pending = new Map();
let nextId = 1;

app.get('/api/random-act', (req, res) => {
  const result = randomActs[Math.floor(Math.random() * randomActs.length)];
  res.json({ result });
});

app.get('/api/time-cycle', (req, res) => {
  const result = timeCycles[Math.floor(Math.random() * timeCycles.length)];
  res.json({ result });
});

app.post('/ask', (req, res) => {
  const query = req.body.query;
  if (!query) {
    res.status(400).json({ error: 'Missing query' });
    return;
  }
  if (!extensionSocket || extensionSocket.readyState !== 1) {
    res.status(503).json({ error: 'Extension not connected' });
    return;
  }
  const id = nextId++;
  pending.set(id, res);
  extensionSocket.send(JSON.stringify({ id, query }));
});

const server = app.listen(3001, () => {
  console.log('HTTP server listening on http://localhost:3001');
});

const wss = new WebSocketServer({ noServer: true });

server.on('upgrade', (request, socket, head) => {
  wss.handleUpgrade(request, socket, head, (ws) => {
    extensionSocket = ws;
    console.log('Extension connected');
    ws.on('message', (data) => {
      let msg;
      try {
        msg = JSON.parse(data);
      } catch {
        return;
      }
      if (msg.id && pending.has(msg.id)) {
        const res = pending.get(msg.id);
        pending.delete(msg.id);
        res.json({ answer: msg.answer });
      }
    });
    ws.on('close', () => {
      extensionSocket = null;
    });
  });
});

export default server;
