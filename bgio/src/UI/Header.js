import * as PIXI from "pixi.js";
import * as GS from "./GraphicSettings.js";

    //draw text witg tge game name
    export function draw() {
    let container = new PIXI.Container();
    //put it on the top of the screen
    container.y = 0;
    container.x = 0;
    container.width = global.pixiApp.screen.width;
    container.height = GS.headerHeight;
    global.pixiApp.stage.addChild(container);
    container.addChild(getTitle());
    }
      
    function getTitle() {
        let text = new PIXI.Text(gameDef.name, GS.HeaderStyle);
        text.x = 10;
        text.y = 10;
        return text;
    }
    //draw the player name          
