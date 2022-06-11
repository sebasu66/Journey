package com.sebasu.journey.components.rules;

import com.sebasu.journey.components.GameComponents;
import com.sebasu.journey.components.Player;

import java.util.Map;

public class GameRules {


    public static Map<String,String> testConstants = Map.of(
            "gameID", "gameId",
            "test2", "test2",
            "test3", "test3"
    );

    //% of each terrain type that is generated
    public static Map <GameComponents.Terrain, Integer>TerrainComposition = Map.of(
            GameComponents.Terrain.PLAIN, 31,
            GameComponents.Terrain.MOUNTAIN_2, 11,
            GameComponents.Terrain.FOREST, 31,
            GameComponents.Terrain.MOUNTAIN_1, 16,
            GameComponents.Terrain.MOUNTAIN_3, 6,
            GameComponents.Terrain.LAKE, 11
    );
    public static Map<Player.playerStats, Integer> getDefaultPlayerStats() {
        Map<Player.playerStats, Integer> stats = new java.util.HashMap<>();
        stats.put(Player.playerStats.AGILITY, 10); //small expedition higher agility
        stats.put(Player.playerStats.CARRYING, 2);//max weight = carrying * 20
        stats.put(Player.playerStats.CRAFTING, 1);//how good in crafting - quality of items, injury risk
        stats.put(Player.playerStats.FIGHTING, 1);//how good in fighting - injury risk
        stats.put(Player.playerStats.GATHERING, 2);//how much raw resources can be gathered per turn
        stats.put(Player.playerStats.HEALTH, 10);//how affected by injury, how much time to heal
        stats.put(Player.playerStats.SCOUTING, 5);//how much information can be gathered per turn about the path
        stats.put(Player.playerStats.RESEARCH, 1);//knowledge gained per turn. knowledge is used to create recipes of tools and +
        return stats;

        //secundary stats:
/*        maxWeight = carrying * 20;
        currentWeight = 0;
        foodRations = 10;
        movementSpeed = agility * 100+ scoutings * 50-currentWeight-foodRations/5;
        gatheringSpeed = gathering * 2;*/

    }

    public static Map<GameComponents.GameStats, String> getDefaultGameStats() {
        Map<GameComponents.GameStats, String> stats = new java.util.HashMap<>();
        stats.put(GameComponents.GameStats.GAME_ID, "gameId");
        stats.put(GameComponents.GameStats.PLAYER_COUNT, "1");
        stats.put(GameComponents.GameStats.MAP_SIZE, "small");
        stats.put(GameComponents.GameStats.CURRENT_TURN, "0");
        return stats;
    }
}
