package com.sebasu.journey.gameManager;

import com.sebasu.journey.Interfaces.TurnListener;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {
    //listeners list
    private List<TurnListener> listeners = new ArrayList<>();
    private int currentTurn = 1;

    public void startGame(){
        startTurn();
    }

    public void next(){
            endTurn();
            startTurn();
            currentTurn++;
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    private void startTurn(){
        for(TurnListener listener : listeners){
            listener.onTurnStart();
        }
    }

    private void endTurn(){
        for(TurnListener listener : listeners){
            listener.onTurnEnd();
        }
    }

    public void addListener(TurnListener listener){
        listeners.add(listener);
    }

    public void onTurnEnd(){
        for (TurnListener listener : listeners) {
            listener.onTurnEnd();
        }
    }

    public void onTurnStart(){
        for (TurnListener listener : listeners) {
            listener.onTurnStart();
        }
    }

    public void removeListener(TurnListener turnListener) {
        listeners.remove(turnListener);
    }
}
