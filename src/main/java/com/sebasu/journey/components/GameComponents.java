package com.sebasu.journey.components;

import com.sebasu.journey.Interfaces.GenericStats;
import com.sebasu.journey.util.MultiLanguageString;

import java.util.Map;

public class GameComponents {


    //A FUNCTION THAT GENERATES A LITERARY MULTILANG DESCRIPTION OF A PLACE BASED ON KEYWORDS
    public static MultiLanguageString getNarrativeDescriptionOfScene(Scene scene) {

        Map<Scene, MultiLanguageString> map = new java.util.HashMap<>();
        //dummy data
        map.put(new Scene("Alaska,exterior,camp", "dawn", "clear, cold", "plain"), new MultiLanguageString("You 're in a clear net to a camp, it's really early in the morning, the skt y is clear and the air is chilly cold", "$descForest"));

        return map.get(scene);
        //TODO: call gpt3 the first time then store the answer in db for next calls on the same scene

    }


    public enum GameStats implements GenericStats {
        GAME_ID("$gameId"),
        PLAYER_COUNT("$playerCount"),
        MAP_SIZE("$mapSize"),
        CURRENT_TURN("$currentTurn"),
        LANGUAGE("$language");

        private String label;

        GameStats(String label) {
            this.label = label;
        }
    }


    //propiedades de objetos
    /*
    [
    {
        "nombre": "pistola",
        "descripcion": "pistola de 9mm reglamentaria",
        "tipo": "arma de fuego",
        "daño": "numero aleatorio de 1 a 6",
        "unicas acciones posibles": [
            "disparar",
            "cargar",
            "recargar",
        "destrabar"
         ],
        "reglas": [
            "no puede disparar si no tiene balas"
             "maximo de balas: 10"
             "posibilidad de trabarse al disparar: 1/10"
        ]
    }
    ]


*si moris volves a empezar desde tu ultimo salvado
*salvar el juego quita puntos
*morir quita puntos
*llegar primero a encontrar tu padre da puntos
*Escanear geroglificos da puntos, y pistas de la localizacion de tu padre ?
*Ganar combattes da puntos
*craftear ciertos objetos avanzado da puntos



    ser primero +50
    descubrir monumentos +10
    esmeraldas +50
    dinero
    "party wipe" -100



     */
    public enum Terrain {
        PLAIN(0),
        FOREST(0),
        MOUNTAIN_1(10),
        MOUNTAIN_2(35),
        MOUNTAIN_3(60),
        SEA(0),
        LAKE(0);
        private int height;

        public int getHeight() {
            return height;
        }

        Terrain(int height) {
            this.height = height;
        }
    }

    public enum RawResource {
        WOOD("$$wood", Building.WOODCUTTER),
        IRON("$$iron", Building.IRONMINE),
        SOIL("$$fertil_soil", Building.CROPS),
        STONE("$$stone", Building.STONEMINE),
        RUNES("$$runes", Building.LIBRARY),
        NONE("$$none", null),
        BEACH("$$beach", Building.PORT);

        private String description;
        private Building allowedBuilding;

        private RawResource(String description, Building allowedBuilding) {
            this.description = description;
            this.allowedBuilding = allowedBuilding;
        }

    }

    public enum ProductionGoods {
        LOGS("$$logs"),
        IRONBARS("$$ironbars"),
        COBLESTONE("$$cobblestone"),
        HOUSING("$$housing"),
        KNOWLEDGE("$$knowledge"),
        FOOD("$$food"),
        UNKNOWN("$$unknown"),
        NONE("$$none");

        private String description;

        private ProductionGoods(String description) {
            this.description = description;
        }
    }

    public enum Effect {
        NONE()
    }

    public enum Action {
        BUILD,
        EXPLORE,
        NONE,
        EXPEDITION,
        RESEARCH,
        PRODUCE,
        REPRODUCE
    }

    public enum Building {
        HOUSE("$$house", ProductionGoods.LOGS, 3, ProductionGoods.HOUSING, 5, Action.REPRODUCE),
        PORT("$$port", GameComponents.ProductionGoods.LOGS, 3, ProductionGoods.UNKNOWN, 5, Action.EXPEDITION),
        CROPS("$$crops", ProductionGoods.NONE, 0, ProductionGoods.FOOD, 5, Action.PRODUCE),
        LIBRARY("$$library", ProductionGoods.LOGS, 3, ProductionGoods.KNOWLEDGE, 5, Action.RESEARCH),
        IRONMINE("$$ironmine", GameComponents.ProductionGoods.LOGS, 3, ProductionGoods.IRONBARS, 5, Action.PRODUCE),
        STONEMINE("$$stonemine", GameComponents.ProductionGoods.LOGS, 3, ProductionGoods.COBLESTONE, 5, Action.PRODUCE),
        WOODCUTTER("$$woodcutter", GameComponents.ProductionGoods.LOGS, 3, GameComponents.ProductionGoods.LOGS, 5, Action.PRODUCE);

        private String name;
        private ProductionGoods costResource;
        private int cost;
        private ProductionGoods producedResource;
        private int producedResourceAmount;
        private Action action;

        /*public boolean canBeBuilt(Predicate <> predicate) {
            return predicate.test(this);
        }*/

        Building(String name, ProductionGoods resource, int cost, ProductionGoods producedResource, int producedResourceAmount, Action action) {
            this.name = name;
            this.costResource = resource;
            this.cost = cost;
            this.producedResource = producedResource;
            this.producedResourceAmount = producedResourceAmount;
            this.action = action;
        }

        //get available resources
        public ProductionGoods getRequiredResource() {
            return costResource;
        }

        //get cost
        public int getCost() {
            return cost;
        }

        //get produced resource
        public ProductionGoods getProducedResource() {
            return producedResource;
        }

    }


}
