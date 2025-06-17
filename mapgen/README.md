# Mapgen Demo

This directory contains a Python-based map generator.

## Running the Demo

To run the feature demo, navigate to this directory (`mapgen`) in your terminal and execute the `run.sh` script:

```bash
./run.sh
```

This script will:
1. **Install dependencies:** Automatically install any necessary Python packages (currently Pillow) from `requirements.txt` using pip.
2. **Generate a cave map layout.**
3. **Render the map as an image (`generated_cave.png`)** in the parent directory.
4. **Extract and save Foundry VTT compatible wall data (`foundry_scene_data.json`)** in the parent directory.

Make sure you have Python and pip installed on your system.
