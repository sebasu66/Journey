package com.sebasu.journey.components;

import com.sebasu.journey.components.rules.GameRules;
import com.sebasu.journey.gameManager.GAdmin;
import com.sebasu.journey.gameManager.GameStepBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameMap {

    private MapTile[][] tileMatrix;
    GameStepBuilder.playerCountEnum playerCount;

    public GameMap(GameStepBuilder.mapSizeEnum mapSize, GameStepBuilder.playerCountEnum playerCount) {
        this.playerCount = playerCount;
        this.tileMatrix = new MapTile[mapSize.getSize()][mapSize.getSize()];
        MapGenerator.generateMap(mapSize,playerCount);
    }

    public static class TilePath {
        private MapTile target;
        private Boolean isValid;
        private Integer dificulty;
        private MapTile current;


        public TilePath(MapTile target, MapTile current){
        this.target = target;
        this.current = current;
        this.isValid = target.terrain.equals(GameComponents.Terrain.SEA) ? false : true;
        this.dificulty = target.terrain.getHeight() - current.terrain.getHeight();
                }
    }

    public static class MapTile {
        GameComponents.Terrain terrain;
        List<GameComponents.RawResource> resourceList;
        List<GameComponents.Building> buildings;
        Player owner;
        int x;
        int y;
        int maxBuildings;
        TilePath pathNorth, pathSouth, pathEast, pathWest;
        //java.util.Map<GameComponents.ProductionResource, ValueRange> resourceProductionRanges;

        public MapTile(GameComponents.Terrain terrain, List<GameComponents.RawResource> resources) {
            this.terrain = terrain;
            this.resourceList = resources;

        }

        //getters
        public GameComponents.Terrain getTerrain() {
            return terrain;
        }

        public List<GameComponents.RawResource> getRawResourceList() {
            return resourceList;
        }

        public List<GameComponents.Building> getBuildings() {
            return buildings;
        }

        public Player getOwner() {
            return owner;
        }

        public String getCoordenates() {
            return "x = " + x + " y = " + y;
        }

        //setters
        public void setOwner(Player owner) {
            this.owner = owner;
        }

        public void setCoordenates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * creating a new building is permanent
         * consumes the required resource
         * produces the produced resource as maximum
         *
         * @param building
         */

        public void addBuilding(GameComponents.Building building) {
            if (isSpaceAvailable()) {
                this.buildings.add(building);
            }
        }

        //is there enough space for a new building?
        public boolean isSpaceAvailable() {
            if (buildings.size() < maxBuildings) {
                return true;
            } else {
                return false;
            }
        }


    }
}
class MapGenerator{

    private static Stack<GameComponents.Terrain> terrainList = new Stack<>();
    private static GameMap.MapTile theSea = new GameMap.MapTile(GameComponents.Terrain.SEA, null);

    public static GameMap.MapTile[][] generateMap(GameStepBuilder.mapSizeEnum mapSize, GameStepBuilder.playerCountEnum playerCount){
        GameMap.MapTile[][] map = new GameMap.MapTile[mapSize.getSize()][mapSize.getSize()];
        int totalTiles = mapSize.getSize() * mapSize.getSize();
        //compose a finite list of terrain types based on the map size
        //terrain type distribution is based on a game rule
                GameRules.TerrainComposition.keySet().forEach(key -> {
           //calculate the amount of each terrain type to add based on % of the total tiles
              int terrainAmount = (int) Math.ceil(totalTiles * GameRules.TerrainComposition.get(key)/100);
            for (int i = 0; i < terrainAmount; i++) {
               terrainList.push(key);
              }
        });
                //shuffle the terrain list
        Collections.shuffle(terrainList);

        for(int i = 0; i < mapSize.getSize(); i++){
            for(int j = 0; j < mapSize.getSize(); j++){
                map[i][j] = generateTile(i, j,map);
            }
        }
        generatePaths(map);
        if(GAdmin.getInstance().isDebugMode()){
            printMap(map);
        }
        return map;
    }

    private static void printMap(GameMap.MapTile[][] map){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                System.out.print(map[i][j].getTerrain() + " ");
            }
            System.out.println();
        }
    }

    private static void generatePaths(GameMap.MapTile[][] map){
        //for each tile, generate a path to the north, south, east and west
        //all tiles in the border of the map are connected to the sea
        //all tiles in the center of the map are connected to each other

        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++) {
                GameMap.MapTile tile = map[x][y];

                if(x == 0){
                    tile.pathNorth = new GameMap.TilePath(theSea, tile);
                }else{
                    tile.pathNorth = new GameMap.TilePath(map[x-1][y], tile);
                }
                if(x == map.length - 1){
                    tile.pathSouth = new GameMap.TilePath(theSea, tile);
                }else {
                    tile.pathSouth = new GameMap.TilePath(map[x+1][y], tile);
                }
                if(y == 0){
                    tile.pathWest = new GameMap.TilePath(theSea, tile);
                }else {
                    tile.pathWest = new GameMap.TilePath(map[x][y-1], tile);
                }

                if(y == map[0].length - 1){
                    tile.pathEast = new GameMap.TilePath(theSea, tile);
                }else
                {
                    tile.pathEast = new GameMap.TilePath(map[x][y+1], tile);
                }
            }}

    }

    //generate a map tile following rules
    //
    private static GameMap.MapTile generateTile(int x, int y, GameMap.MapTile[][] map){
        GameMap.MapTile tile = new GameMap.MapTile(terrainList.pop(), null);
        return tile;
    }


}
