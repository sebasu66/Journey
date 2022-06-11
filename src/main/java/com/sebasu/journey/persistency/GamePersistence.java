package com.sebasu.journey.persistency;

import com.sebasu.journey.gameManager.Game;

import java.util.Map;

public class GamePersistence {

    private static Map <String,Game>dummyGameTable = new java.util.HashMap<>();

    public static void saveGame(Game game) {
        dummyGameTable.put(game.getId(), game);
    }

    public static Game loadGame(String id) {
        return dummyGameTable.get(id);
    }

}
