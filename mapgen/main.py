import json
import argparse
# Use relative imports if main.py is part of the mapgen package
from .map_generator import MapGenerator
from .map_renderer import MapRenderer
from .foundry_data_extractor import FoundryDataExtractor

def main():
    parser = argparse.ArgumentParser(description="Generate maps with various features for Foundry VTT.")
    parser.add_argument('--width', type=int, default=30, help="Map width in tiles (default: 30)")
    parser.add_argument('--height', type=int, default=20, help="Map height in tiles (default: 20)")
    parser.add_argument('--tile_size', type=int, default=100, help="Tile size in pixels for rendering and Foundry data (default: 100)")
    parser.add_argument('--type', type=str, default="mixed",
                        choices=["mixed", "building", "forest", "caves"],  # Updated from "cave" to "caves" to match MapGenerator
                        help="Type of map to generate (default: mixed)")
    parser.add_argument('--output_image', type=str, default="generated_map.png", help="Filename for the rendered map image (default: generated_map.png)")
    parser.add_argument('--output_foundry', type=str, default="foundry_scene_data.json", help="Filename for the Foundry VTT scene data (default: foundry_scene_data.json)")

    args = parser.parse_args()

    print(f"Generating a '{args.type}' map of size {args.width}x{args.height} with tile size {args.tile_size}px.")

    map_gen = MapGenerator(args.width, args.height)
    map_gen.generate_map(generator_type=args.type) # Use the 'type' argument passed
    grid = map_gen.get_grid()

    if not grid or (args.height > 0 and not grid[0]): # Check if grid is empty or first row is missing
        print("Error: Generated grid is empty or invalid.")
        return

    print(f"Rendering map image to {args.output_image}...")
    renderer = MapRenderer(tile_size_px=args.tile_size)
    renderer.render(grid, args.output_image)
    print(f"Map image saved to {args.output_image}")

    print(f"Extracting Foundry VTT data for {args.output_foundry}...")
    extractor = FoundryDataExtractor(tile_size_px=args.tile_size)
    foundry_data = extractor.extract(grid)

    # Add scene dimensions and grid size to foundry_data for convenience
    foundry_data['scene_width'] = args.width * args.tile_size
    foundry_data['scene_height'] = args.height * args.tile_size
    foundry_data['grid_size'] = args.tile_size
    # Add other scene-level data that might be useful
    foundry_data['name'] = f"Generated {args.type.capitalize()} Map ({args.width}x{args.height})"
    foundry_data['padding'] = 0.25 # Example padding, if your extractor doesn't set it
    foundry_data['grid_type'] = 1 # Square grid
    foundry_data['grid_units'] = "px"
    foundry_data['grid_color'] = "0x000000"
    foundry_data['grid_alpha'] = 0.2
    foundry_data['shift_x'] = 0
    foundry_data['shift_y'] = 0
    # Ensure walls and lights are empty lists if not populated by extractor
    if 'walls' not in foundry_data:
        foundry_data['walls'] = []
    if 'lights' not in foundry_data:
        foundry_data['lights'] = []


    with open(args.output_foundry, 'w') as f:
        json.dump(foundry_data, f, indent=4)
    print(f"Foundry VTT data saved to {args.output_foundry}")

    print("\nProcess Complete.")

if __name__ == "__main__":
    main()
