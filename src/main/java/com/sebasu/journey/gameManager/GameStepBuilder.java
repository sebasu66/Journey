package com.sebasu.journey.gameManager;

import com.sebasu.journey.components.GameMap;
import com.sebasu.journey.components.rules.GameRules;

public class GameStepBuilder {

    public enum mapSizeEnum {
        SMALL(8), MEDIUM(10), LARGE(12);

        private int size;

        public int getSize() {
            return size;
        }

        mapSizeEnum(int size) {
            this.size = size;
        }
    }

    public enum playerCountEnum {
        TWO(2), THREE(3), FOUR(4), ONE(1);

        private int count;

        public int getCount() {
            return count;
        }

        playerCountEnum(int count) {
            this.count = count;
        }
    }

    //private constructor because you're supposed to use the wizard facade
    private GameStepBuilder() {

    }

    //region wizard interfaces
    public interface IPlayerCountStep {

        public IMapSizeStep howManyPlayers(playerCountEnum playerCount);

    }

    public interface IMapSizeStep {

        public ISetReadyStep whichMapSize(mapSizeEnum mapSize);

    }

    public interface ISetReadyStep {

        public String setReadyAndReturnID();

    }
    //endregion


    public static class Wizard implements IPlayerCountStep, IMapSizeStep, ISetReadyStep {

        private playerCountEnum playerCount;
        private mapSizeEnum mapSize;
        private String id;

        @Override
        public IMapSizeStep howManyPlayers(playerCountEnum playerCount) {
            this.playerCount = playerCount;
            return this;
        }

        @Override
        public ISetReadyStep whichMapSize(mapSizeEnum mapSize) {
            this.mapSize = mapSize;
            return this;
        }

        @Override
        public String setReadyAndReturnID() {
            id = GameRules.testConstants.get("gameID");
            Game game = new Game();
            game.setId(id);
            game.setPlayerCount(playerCount);
            game.setMap(new GameMap(mapSize, playerCount));
            GAdmin.getInstance().addGame(game);
            return id;
        }

    }
}










