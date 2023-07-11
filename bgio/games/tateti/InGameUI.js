import * as PIXI from "pixi.js";

export  function draw() {
    var cellList =[];
        
        
    const cellSize = 100;
    const gap =5;
    let lastCellX, lastCellY=0;
    // Create board container
    let board = new PIXI.Container();
    board.x = 50;
    board.y = 150;
    
    pixiApp.stage.addChild(board);
    
    // Create cells
    for (let i = 0; i < 3; i++) {
      for (let j = 0; j < 3; j++) {
        let cell = new PIXI.Graphics();
        cell.beginFill(0xFFFFFF);
        cell.drawRect(0, 0, cellSize, cellSize);
        cell.endFill();
    
        lastCellX = i * cellSize;
        lastCellY = j * cellSize;
    
        cell.x = lastCellX;
        cell.y = lastCellY;
    
        if (i > 0) {
          cell.x = lastCellX + gap * i;
        }
        if (j > 0) {  
          cell.y = lastCellY + gap * j;
        }
    
        
        // Add interactivity
        cell.eventMode = "dynamic";
        cell.buttonMode = true;
    //change mouse pointe ron hover to hand
        cell.on('pointerover', () => cell.alpha = 0.7);
        cell.on('pointerout', () => cell.alpha = 1);  
    
    
        cell.on('pointerdown', () => selectCell(i * 3 + j));
    
        // Add to board
        board.addChild(cell);
      }
    }
}