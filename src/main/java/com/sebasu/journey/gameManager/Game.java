package com.sebasu.journey.gameManager;

import com.sebasu.journey.Interfaces.HasStats;
import com.sebasu.journey.components.GameComponents;
import com.sebasu.journey.components.GameMap;
import com.sebasu.journey.components.Player;
import com.sebasu.journey.components.rules.GameRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game implements HasStats {

//Game properties: Player list, Map, ID

    private final List<Player> players = new ArrayList<>();
    private GameStepBuilder.playerCountEnum playerCount;
    private GameMap map;
    private String id;
    private final TurnManager turnManager;
    private boolean isStarted = false;
    private final Map<GameComponents.GameStats, String> stats = GameRules.getDefaultGameStats();


    public Game() {
        this.turnManager = new TurnManager();
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public void start() throws GameRequirementException {
        //check if already started
        if (isStarted) {
            throw new GameRequirementException("Game already started");
        }
        //check if the map has been initialized otherwise throw an exception
        if (map == null) {
            throw new GameRequirementException("Map has not been initialized");
        }
        //check if the player list size is equal to the player count otherwise throw an exception

        if (players.size() != playerCount.getCount()) {
            throw new GameRequirementException("Player list size is not equal to the player count");
        }
        this.turnManager.startGame();
        this.isStarted = true;
    }

    public void setPlayerCount(GameStepBuilder.playerCountEnum playerCount) {
        this.playerCount = playerCount;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addPlayer(Player player) throws GameRequirementException {
        if (players.size() < playerCount.getCount()) {
            players.add(player);
            player.addToGame(this);
            getTurnManager().addListener(player);
        } else {
            throw new GameRequirementException("Player list is full");
        }
    }

    public Map<GameComponents.GameStats, String> getStats() {
        return stats;
    }

    public String getStatsNarration() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<GameComponents.GameStats, String> entry : stats.entrySet()) {
            sb.append(entry.getKey().toString()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public class GameRequirementException extends Throwable {
        public GameRequirementException(String s) {
        }
    }
}
