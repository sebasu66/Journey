package com.sebasu.journey.components;

import com.sebasu.journey.Interfaces.GenericStats;
import com.sebasu.journey.Interfaces.HasStats;
import com.sebasu.journey.Interfaces.PlayerCompanion;
import com.sebasu.journey.Interfaces.TurnListener;
import com.sebasu.journey.components.rules.GameRules;
import com.sebasu.journey.gameManager.Game;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player implements TurnListener, HasStats {
    private String name;
    private GameMap.MapTile currentTile;
    private String id;
    private Game game;
    private List<PlayerCompanion> companionList = new ArrayList<>();
    private Map<playerStats, Integer> stats = GameRules.getDefaultPlayerStats();



    public Player(@NotNull String name) {
        this.name = name;
    }

    public void addToGame(Game game) {
        this.game = game;
     this.id = game.getId() + "-" + name;
    }


    @Override
    public Game getGameInstance() {
        return game;
    }

    @Override
    public void linkToTurnManager() {
        getGameInstance().getTurnManager().addListener(this);
    }

    @Override
    public void unlinkFromTurnManager() {
        getGameInstance().getTurnManager().removeListener(this);
    }

    @Override
    public void onTurnEnd() {
    }

    @Override
    public void onTurnStart() {
stats = GameRules.getDefaultPlayerStats();
    }

    public void applyStatModif(playerStats stat, int value) {
        stats.put(stat, stats.get(stat) + value);
    }

    public Map<playerStats, Integer> getStats() {
        return stats;
    }

    public String getStatsNarration() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<playerStats, Integer> entry : stats.entrySet()) {
            sb.append(entry.getKey().toString()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public void addCompanion(Companion companion) {
        //TODO: check if companion is already in list
        companionList.add(companion);
        companion.attachPlayer(this);
    }

    public enum playerStats implements GenericStats {
        GATHERING("$gathering", "$descGathering"),
        CRAFTING("$crafting", "$descCrafting"),
        FIGHTING("$fighting", "$descFighting"),
        SCOUTING("$scouting", "$descScouting"),
        AGILITY("$agility", "$descAgility"),
        HEALTH("$health", "$descHealth"),
        CARRYING("$carrying", "$descCarrying"),
        RESEARCH("$research", "$descResearch");

        private String label;
        private String description;

        playerStats(String label, String description) {
            this.label = label;
            this.description = description;
        }
    }
}
