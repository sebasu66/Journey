import { Client } from 'boardgame.io/client';
import { GameDef } from '../games/tateti/Game.js';
import { INVALID_MOVE } from 'boardgame.io/core';
import * as UI from './UI/Main.js';
import { SocketIO } from 'boardgame.io/multiplayer';



// game client definition, documentation:
// https://boardgame.io/documentation/#/api/Client


class GameDefClient {
  constructor() {
    this.client = this.defineGameClient();
    this.client.start();
    this.storeGlobalVariables(); 
  }

  defineGameClient() {
    return Client({ 
      game: GameDef, 
      multiplayer: SocketIO({ server: 'http://localhost:8080' }),
      playerID
    });
  }

  storeGlobalVariables() {
    //store global variables for easy access of core objects
    global.gameClient = this.client;
    global.gameDef = gameClient.game;
    global.GUI = new UI.PixiUI();
  }

  update(state) { 
    if(state === null) {
     console.log("state is null");
      return;
    }
  }

}

const app = new GameDefClient();