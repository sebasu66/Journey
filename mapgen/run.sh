#!/bin/bash

# Get the directory where the script is located
SCRIPT_DIR_RELATIVE=$(dirname "$0")
SCRIPT_DIR=$(cd "$SCRIPT_DIR_RELATIVE" && pwd)

# Print SCRIPT_DIR for debugging
echo "SCRIPT_DIR is: ${SCRIPT_DIR}"
echo "Contents of SCRIPT_DIR:"
ls "${SCRIPT_DIR}"

# Install dependencies
echo "Installing dependencies..."
pip install -r "${SCRIPT_DIR}/requirements.txt"

# Run the main application
echo "Running map generator..."
python -m mapgen.main
