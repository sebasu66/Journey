import { INVALID_MOVE } from 'boardgame.io/core';
import * as InGameUI from './InGameUI.js';
/*/const TicTacToe ={
  name: 'TicTacToe',

  setup: () => ({ cells: Array(9).fill(null) }),

  moves: {
    clickCell: (G, ctx, id) => {
      if (G.cells[id] !== null) {
    return INVALID_MOVE;
  }
      G.cells[id] = ctx.currentPlayer;
    },
  },

  flow: {
    movesPerTurn: 1,
    endGameIf: (G) => {
      if (isVictory(G.cells)) {
        return { winner: currentPlayer };
      }

      if (isDraw(G.cells)) {
        return { draw: true };
      }
    },
  },
};
*/

//import { gameEvent } from "boardgame.io/dist/types/src/core/action-creators";

export const GameDef = {
  name:"TicTacToe",
  setup: () => ({ cells: Array(9).fill(null) }),
  turn: {
    minMoves: 1,
    maxMoves: 1,
  },
  moves: {
    clickCell: ({ G, playerID }, id) => {
      if (G.cells[id] !== null) {
        return INVALID_MOVE;
      }
      G.cells[id] = playerID;
    },
  },
  
  endIf: ({ G, ctx }) => {
    if (isVictory(G.cells)) {
      return { winner: ctx.currentPlayer };
    }
    if (isDraw(G.cells)) {
      return { draw: true };
    }
  },
  UI_draw:InGameUI.draw  
};

function isVictory(cells) {
  // Check for a winning game state and return true or false
  if (cells[0] === cells[1] && cells[1] === cells[2]) {
    return true;
  }
    if (cells[3] === cells[4] && cells[4] === cells[5]) {
    return true;
    }
    if (cells[6] === cells[7] && cells[7] === cells[8]) {
    return true;
    }
    if (cells[0] === cells[3] && cells[3] === cells[6]) {
    return true;
    }
    if (cells[1] === cells[4] && cells[4] === cells[7]) {   
    return true;
    }
    if (cells[2] === cells[5] && cells[5] === cells[8]) {
    return true;
    }
    if (cells[0] === cells[4] && cells[4] === cells[8]) {
    return true;
    }
    if (cells[2] === cells[4] && cells[4] === cells[6]) {   
    return true;
    }
    return false;

}

function isDraw(cells) {
  // Check for a draw game state and return true or false
    if (cells[0] !== null && cells[1] !== null && cells[2] !== null && cells[3] !== null && cells[4] !== null && cells[5] !== null && cells[6] !== null && cells[7] !== null && cells[8] !== null) {
        return true;
    }
}


