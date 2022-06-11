package com.sebasu.journey.components;

import com.sebasu.journey.components.rules.GameRules;
import com.sebasu.journey.gameManager.GAdmin;
import com.sebasu.journey.gameManager.Game;
import com.sebasu.journey.gameManager.GameStepBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {




    String gameId=null;

    Player player=null;
    @BeforeEach
    void setUp() {

        gameId = GAdmin.getInstance().setupNewGame().howManyPlayers(GameStepBuilder.playerCountEnum.ONE).whichMapSize(GameStepBuilder.mapSizeEnum.SMALL).setReadyAndReturnID();
        Game game = GAdmin.getInstance().getGame(gameId);
        player = new Player( "Player1");
        try {
            game.addPlayer(player);
        } catch (Game.GameRequirementException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGameInstance() {
        Game game = GAdmin.getInstance().getGame(gameId);
        Assertions.assertEquals(game.getId(), player.getGameInstance().getId());
    }

    @Test
    void linkToTurnManager() {
        player.getGameInstance().getTurnManager().startGame();
        assertEquals(player.getStats(), GameRules.getDefaultPlayerStats());
        Companion companion = new Companion(player.getGameInstance(),"warrior",CompanionType.WARRIOR, new StatModif(Player.playerStats.FIGHTING,2, "Fighter"));
        player.addCompanion(companion);
        player.getGameInstance().getTurnManager().next();
        assertEquals(player.getStats().get(Player.playerStats.FIGHTING),GameRules.getDefaultPlayerStats().get(Player.playerStats.FIGHTING)+ 2);
    }

    @Test
    void unlinkFromTurnManager() {
    }

    @Test
    void onTurnEnd() {
    }

    @Test
    void onTurnStart() {
    }

    @Test
    void applyStatModif() {
    }
}