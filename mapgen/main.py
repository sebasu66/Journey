from tile import Tile
from map_generator import MapGenerator
from map_renderer import MapRenderer
from foundry_data_extractor import FoundryDataExtractor
import json


def main():
    tiles = {
        "floor": Tile(id="floor", char=".", color=(50, 50, 50), blocks_movement=False, blocks_light=False),
        "wall": Tile(id="wall", char="#", color=(130, 110, 90), blocks_movement=True, blocks_light=True),
    }

    print("Generating map layout...")
    map_gen = MapGenerator(width=50, height=30,
                           floor_tile=tiles["floor"], wall_tile=tiles["wall"])
    map_gen.generate_caves()
    logical_grid = map_gen.grid

    print("Rendering map image...")
    renderer = MapRenderer(tile_size_px=16)
    renderer.render(logical_grid, "generated_cave.png")
    print("Saved map image to generated_cave.png")

    print("Extracting Foundry VTT data...")
    extractor = FoundryDataExtractor(tile_size_px=100)
    foundry_data = extractor.extract(logical_grid)

    with open("foundry_scene_data.json", "w") as f:
        json.dump(foundry_data, f, indent=2)
    print("Saved Foundry data to foundry_scene_data.json")

    print("\nProcess Complete.")

if __name__ == "__main__":
    main()
