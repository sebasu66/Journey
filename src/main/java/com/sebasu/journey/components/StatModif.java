package com.sebasu.journey.components;

import com.sebasu.journey.Interfaces.PlayerAttachable;
import com.sebasu.journey.Interfaces.TurnListener;
import com.sebasu.journey.components.Player.playerStats;
import com.sebasu.journey.gameManager.Game;

public class StatModif implements TurnListener, PlayerAttachable {

    Player.playerStats stat;
    int value;
    Integer duration = null;
    String description;
    Player player = null;

    public StatModif(playerStats stat, int value, String description) {
        this(stat, value, description, null);
    }


    public StatModif(playerStats stat, int value, String description, Integer duration) {
        this.stat = stat;
        this.value = value;
        this.duration = duration;
        this.description = description;
    }

    @Override
    public Game getGameInstance() {
        if (player != null) {
            return player.getGameInstance();
        }
        return null;
    }

    @Override
    public void linkToTurnManager() {
        if (getGameInstance() != null) {
            getGameInstance().getTurnManager().addListener(this);
        }
    }


    @Override
    public void unlinkFromTurnManager() {
        if (getGameInstance() != null) {
            getGameInstance().getTurnManager().removeListener(this);
        }
    }

    @Override
    public void onTurnEnd() {
        if (null != duration) {
            duration--;
            if (duration == 0) {
                unlinkFromTurnManager();
            }
        }
    }

    @Override
    public void onTurnStart() {
        if (player != null) {
            player.applyStatModif(stat, value);
        }
    }

    @Override
    public void attachPlayer(Player player) {

        this.player = player;
        linkToTurnManager();
    }

    @Override
    public void detachPlayer() {

        this.player = null;
        unlinkFromTurnManager();
    }

    @Override
    public boolean isPlayerAttached() {
        return this.player != null;
    }

}
