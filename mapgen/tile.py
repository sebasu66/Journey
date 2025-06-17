class Tile:
    def __init__(self, id: str, name: str = "untitled tile", texture_char: str = "?",
                 color: tuple[int, int, int] = (0, 0, 0),
                 material_type: str = "unknown", blocks_movement: bool = False,
                 blocks_light: bool = False, is_door: bool = False,
                 is_window: bool = False, emits_light: tuple[int, int, int] | None = None):
        self.id = id
        self.name = name
        self.texture_char = texture_char
        self.color = color
        self.material_type = material_type
        self.blocks_movement = blocks_movement
        self.blocks_light = blocks_light
        self.is_door = is_door
        self.is_window = is_window
        self.emits_light = emits_light

    def __repr__(self):
        return f"Tile(name={self.name!r}, id={self.id!r})"

TILE_DEFINITIONS = {
    "FLOOR_CAVE": {
        "id": "floor_cave",
        "name": "Cave Floor",
        "texture_char": ".",
        "color": (50, 50, 50),
        "material_type": "rock",
        "blocks_movement": False,
        "blocks_light": False,
    },
    "WALL_CAVE": {
        "id": "wall_cave",
        "name": "Cave Wall",
        "texture_char": "#",
        "color": (130, 110, 90),
        "material_type": "rock",
        "blocks_movement": True,
        "blocks_light": True,
    },
    "GRASS_FLOOR": {
        "id": "grass_floor",
        "name": "Grass",
        "texture_char": "\"",
        "color": (34, 139, 34), # Forest green
        "material_type": "organic",
        "blocks_movement": False,
        "blocks_light": False,
    },
    "DIRT_FLOOR": {
        "id": "dirt_floor",
        "name": "Dirt",
        "texture_char": ".",
        "color": (139, 69, 19), # Saddle brown
        "material_type": "earth",
        "blocks_movement": False,
        "blocks_light": False,
    },
    "STONE_WALL": {
        "id": "stone_wall",
        "name": "Stone Wall",
        "texture_char": "#",
        "color": (105, 105, 105), # Dim gray
        "material_type": "stone",
        "blocks_movement": True,
        "blocks_light": True,
    },
    "WOODEN_FLOOR": {
        "id": "wooden_floor",
        "name": "Wooden Floor",
        "texture_char": "=",
        "color": (160, 82, 45), # Sienna
        "material_type": "wood",
        "blocks_movement": False,
        "blocks_light": False,
    },
    "DOOR_WOODEN": {
        "id": "door_wooden",
        "name": "Wooden Door",
        "texture_char": "+",
        "color": (139, 69, 19), # Saddle brown (darker than wooden floor)
        "material_type": "wood",
        "blocks_movement": True, # Initially, can be opened
        "blocks_light": True, # Initially, can be opened
        "is_door": True,
    },
    "TREE_TRUNK": {
        "id": "tree_trunk",
        "name": "Tree Trunk",
        "texture_char": "T",
        "color": (101, 67, 33), # Dark brown
        "material_type": "wood",
        "blocks_movement": True,
        "blocks_light": True,
    },
    "LEAVES": {
        "id": "leaves",
        "name": "Leaves",
        "texture_char": "*",
        "color": (0, 100, 0), # Dark green
        "material_type": "organic",
        "blocks_movement": False, # Or True if dense canopy
        "blocks_light": True, # Canopy blocks light
    },
    "SAND_FLOOR": {
        "id": "sand_floor",
        "name": "Sand",
        "texture_char": "~",
        "color": (244, 164, 96), # Sandy brown
        "material_type": "sand",
        "blocks_movement": False,
        "blocks_light": False,
    },
    "WATER": {
        "id": "water",
        "name": "Water",
        "texture_char": "~",
        "color": (0, 0, 205), # Medium blue
        "material_type": "liquid",
        "blocks_movement": True, # Impassable by default
        "blocks_light": False,
    }
}

# Optional: A helper function to create Tile instances from these definitions
def create_tile_from_definition(def_name: str) -> Tile:
    definition = TILE_DEFINITIONS.get(def_name)
    if not definition:
        raise ValueError(f"Unknown tile definition: {def_name}")

    # Create a dictionary of arguments for the Tile constructor
    # This ensures that if a key is missing in the definition,
    # the default value from the Tile constructor is used.
    tile_args = {
        "id": definition["id"],
        "name": definition.get("name", "untitled tile"), # Default from spec
        "texture_char": definition.get("texture_char", "?"), # Default from spec
        "color": definition.get("color", (0,0,0)),
        "material_type": definition.get("material_type", "unknown"), # Default from spec
        "blocks_movement": definition.get("blocks_movement", False),
        "blocks_light": definition.get("blocks_light", False),
        "is_door": definition.get("is_door", False),
        "is_window": definition.get("is_window", False),
        "emits_light": definition.get("emits_light", None)
    }
    return Tile(**tile_args)
