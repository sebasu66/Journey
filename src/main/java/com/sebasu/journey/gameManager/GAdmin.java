package com.sebasu.journey.gameManager;


import com.sebasu.journey.persistency.GamePersistence;

public class GAdmin {

    private static GAdmin instance = null;


    public static GAdmin getInstance() {
        if (instance == null) {
            instance = new GAdmin();
        }
        return instance;
    }
    private GAdmin() {
     }

    protected void addGame(Game game) {
        GamePersistence.saveGame(game);
    }

    public Game getGame(String id) {
        return GamePersistence.loadGame(id);
    }

    public GameStepBuilder.IPlayerCountStep setupNewGame(){
        return new GameStepBuilder.Wizard();
    }

    public boolean isDebugMode() {
        return true;
    }
}





