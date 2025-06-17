#!/bin/bash

# Get the directory where the script is located to robustly find requirements.txt
SCRIPT_DIR_RELATIVE=$(dirname "$0")
SCRIPT_DIR=$(cd "$SCRIPT_DIR_RELATIVE" && pwd)

# Navigate to the script's directory to ensure main.py and other modules are found correctly
cd "$SCRIPT_DIR" || exit

echo "Current working directory: $(pwd)"
echo "Contents of current directory:"
ls

# Install dependencies
echo "Installing dependencies from requirements.txt..."
pip install -r "requirements.txt"
# Use pip3 if pip points to Python 2, e.g. pip3 install -r requirements.txt

# Run the main application using python3
echo "Running map generator with default settings..."
python3 main.py

# Example usages (commented out):
# echo "Running map generator for a large forest map..."
# python3 main.py --type forest --width 100 --height 75 --output_image large_forest.png --output_foundry large_forest_foundry.json

# echo "Running map generator for a small building map..."
# python3 main.py --type building --width 20 --height 15 --tile_size 50 --output_image small_building.png --output_foundry small_building_foundry.json

# echo "Running map generator for a cave system..."
# python3 main.py --type caves --width 60 --height 40 --output_image cave_system.png --output_foundry cave_system_foundry.json

# echo "Running map generator for a mixed map with custom names..."
# python3 main.py --type mixed --width 50 --height 50 --output_image my_mixed_map.png --output_foundry my_mixed_foundry.json

echo "Script finished."
