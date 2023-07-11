// src/server.js
const { Server, Origins } = require('boardgame.io/server');
import { GameDef as TicTacToe }  from '../games/tateti/Game.js';


const server = Server({ games: [TicTacToe],
  //port:8000, 
  //apiPort: 8080,
  origins: [Origins.LOCALHOST, Origins.LAN, Origins.WILDCARD] });
server.run(8080);

