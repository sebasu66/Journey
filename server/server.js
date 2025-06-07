import express from 'express';
import { WebSocketServer } from 'ws';

const app = express();
app.use(express.json());

let extensionSocket = null;
const pending = new Map();
let nextId = 1;

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
