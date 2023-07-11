import *  as PIXI from 'pixi.js';
import * as HeaderUI from './Header.js';

export class PixiUI {
    constructor() {
        this.initUI();
        this.draw();
    }
initUI() {
    global.pixiApp = new PIXI.Application({
      background: '#1099bb',
      resizeTo: window,
      resolution: window.devicePixelRatio || 1,
      autoDensity: true
    });
    document.body.appendChild(pixiApp.view);
      }
      draw(){
          HeaderUI.draw();
          gameDef.UI_draw();
      }
    }