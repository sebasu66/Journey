package com.sebasu.journey.components;

import com.sebasu.journey.Interfaces.PlayerCompanion;
import com.sebasu.journey.gameManager.Game;

import java.util.ArrayList;
import java.util.List;

public class Companion implements PlayerCompanion {
    private String name;
    private String id;
    private Game game;
    private Player player;
    private CompanionType type;
    private List<StatModif> statModifs = new ArrayList<>();

    public Companion(Game game, String name, CompanionType type,StatModif ... statModifiers) {
        this.name = name;
        this.id = game.getId() + "-" + name;
        this.game = game;
        this.type = type;
        for (StatModif statModif : statModifiers) {
            statModifs.add(statModif);
        }
    }


    @Override
    public void attachPlayer(Player player) {
    this.player = player;
    for (StatModif statModif : statModifs) {
        statModif.attachPlayer(player);
    }

    }

    @Override
    public void detachPlayer() {
    for (StatModif statModif : statModifs) {
        statModif.detachPlayer();
    }
        this.player = null;
    }

    @Override
    public boolean isPlayerAttached() {
        return player != null;
    }
}

enum CompanionType {
    SCOUT("$companion_scouting", "$descCompanionScout"),
    WARRIOR("$companion_warrior", "$descCompanionWarrior"),
    SCIENTIST_MEDIC("$companion_scientist_medic", "$descCompanionScientistMedic"),
    CRAFTER_GATHERER("$companion_crafter_gatherer", "$descCompanionCrafterGatherer"),
    MULE_CARRIER("$companion_mule_carrier", "$descCompanionMuleCarrier");

    private String Name;
    private String Description;

    CompanionType(String name, String description) {
        this.Name = name;
        this.Description = description;
    }

}