package com.sebasu.journey.Interfaces;

//classes implementing TurnListener are notified when a player's turn is over

import com.sebasu.journey.gameManager.Game;

//EventListener Design Pattern
public interface TurnListener{


    Game getGameInstance();


    public default void linkToTurnManager(){
    getGameInstance().getTurnManager().addListener(this);
    }

    public default void unlinkFromTurnManager(){
        getGameInstance().getTurnManager().removeListener(this);
    }

    public void onTurnEnd();
    public void onTurnStart();

}
