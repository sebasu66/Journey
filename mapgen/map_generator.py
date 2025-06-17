import random
from .tile import Tile, create_tile_from_definition, TILE_DEFINITIONS

class MapGenerator:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        # Initialize the grid with a default tile, e.g., grass
        # This will be overwritten by generate_map for specific scenarios
        default_fill_tile = create_tile_from_definition("GRASS_FLOOR")
        self.grid = [[default_fill_tile for _ in range(width)] for _ in range(height)]

    def _count_wall_neighbors(self, x, y):
        count = 0
        for dy in (-1, 0, 1):
            for dx in (-1, 0, 1):
                if dx == 0 and dy == 0:
                    continue
                nx, ny = x + dx, y + dy
                if 0 <= nx < self.width and 0 <= ny < self.height:
                    if self.grid[ny][nx].blocks_movement:
                        count += 1
                else:
                    count += 1
        return count

    def _generate_caves_cellular_automata(self, fill_probability=0.45, generations=5):
        wall_cave_tile = create_tile_from_definition("WALL_CAVE")
        floor_cave_tile = create_tile_from_definition("FLOOR_CAVE")
        self.grid = [[wall_cave_tile if random.random() < fill_probability else floor_cave_tile
                      for _ in range(self.width)] for _ in range(self.height)]
        for _ in range(generations):
            new_grid = [[self.grid[y][x] for x in range(self.width)] for y in range(self.height)]
            for y in range(self.height):
                for x in range(self.width):
                    wall_count = self._count_wall_neighbors(x, y)
                    if self.grid[y][x].blocks_movement:
                        new_grid[y][x] = wall_cave_tile if wall_count >= 4 else floor_cave_tile
                    else:
                        new_grid[y][x] = wall_cave_tile if wall_count >= 5 else floor_cave_tile
            self.grid = new_grid

    def generate_room(self, room_x, room_y, room_width, room_height,
                      wall_type_def="STONE_WALL", floor_type_def="WOODEN_FLOOR"):
        wall_tile = create_tile_from_definition(wall_type_def)
        floor_tile = create_tile_from_definition(floor_type_def)
        for y in range(room_y, room_y + room_height):
            for x in range(room_x, room_x + room_width):
                if 0 <= x < self.width and 0 <= y < self.height:
                    is_border = (x == room_x or x == room_x + room_width - 1 or
                                 y == room_y or y == room_y + room_height - 1)
                    self.grid[y][x] = wall_tile if is_border else floor_tile

    def place_door(self, x, y, door_type_def="DOOR_WOODEN"):
        if 0 <= x < self.width and 0 <= y < self.height:
            self.grid[y][x] = create_tile_from_definition(door_type_def)

    def generate_building(self, start_x=0, start_y=0, building_max_width=None, building_max_height=None,
                          num_rooms=3, min_room_size=3, max_room_size=7,
                          wall_type_def="STONE_WALL", floor_type_def="WOODEN_FLOOR",
                          door_type_def="DOOR_WOODEN"):

        if building_max_width is None: building_max_width = self.width - start_x
        if building_max_height is None: building_max_height = self.height - start_y

        generated_rooms_coords = []
        for _ in range(num_rooms):
            room_width = random.randint(min_room_size, max_room_size)
            room_height = random.randint(min_room_size, max_room_size)

            # Ensure room fits within specified building area
            max_rx = start_x + building_max_width - room_width
            max_ry = start_y + building_max_height - room_height

            if max_rx < start_x or max_ry < start_y : # Not enough space for even min_room_size
                continue


            room_x = random.randint(start_x, max_rx)
            room_y = random.randint(start_y, max_ry)

            self.generate_room(room_x, room_y, room_width, room_height, wall_type_def, floor_type_def)
            generated_rooms_coords.append((room_x, room_y, room_width, room_height))

        if not generated_rooms_coords: return

        first_room_x, first_room_y, first_room_width, first_room_height = generated_rooms_coords[0]
        door_placed = False
        # Simplified door placement: try middle of a wall segment
        # Try top wall, then left wall
        # Top wall:
        door_candidate_x = first_room_x + first_room_width // 2
        door_candidate_y = first_room_y
        if 0 < door_candidate_x < self.width and 0 < door_candidate_y < self.height -1: # check map bounds
             # Check if tile above is not blocking (e.g. grass, or outside building)
            if not self.grid[door_candidate_y - 1][door_candidate_x].blocks_movement:
                 if self.grid[door_candidate_y][door_candidate_x].id == wall_type_def.lower().replace(" ","_"):
                    self.place_door(door_candidate_x, door_candidate_y, door_type_def)
                    door_placed = True

        # Left wall (if no door placed yet):
        if not door_placed:
            door_candidate_x = first_room_x
            door_candidate_y = first_room_y + first_room_height // 2
            if 0 < door_candidate_x < self.width -1 and 0 < door_candidate_y < self.height: # check map bounds
                # Check if tile to the left is not blocking
                if not self.grid[door_candidate_y][door_candidate_x - 1].blocks_movement:
                    if self.grid[door_candidate_y][door_candidate_x].id == wall_type_def.lower().replace(" ","_"):
                        self.place_door(door_candidate_x, door_candidate_y, door_type_def)
                        door_placed = True

        # Fallback if specific checks fail but room exists (e.g. corner case)
        if not door_placed and generated_rooms_coords:
            r_x, r_y, r_w, r_h = generated_rooms_coords[0]
            fallback_door_x, fallback_door_y = r_x + r_w // 2, r_y # Middle of top wall
            # Check if it's a wall before placing
            if 0 <= fallback_door_x < self.width and 0 <= fallback_door_y < self.height:
                if self.grid[fallback_door_y][fallback_door_x].id == wall_type_def.lower().replace(" ","_"):
                    self.place_door(fallback_door_x, fallback_door_y, door_type_def)

    def generate_forest(self, forest_x, forest_y, forest_width, forest_height,
                        tree_density=0.2, tree_type_def_name="TREE_TRUNK",
                        ground_type_def_name="GRASS_FLOOR", leaves_type_def_name="LEAVES"):

        ground_tile = create_tile_from_definition(ground_type_def_name)
        tree_tile = create_tile_from_definition(tree_type_def_name)
        leaves_tile = create_tile_from_definition(leaves_type_def_name)

        # Fill the area with ground_type_def
        for y in range(forest_y, forest_y + forest_height):
            for x in range(forest_x, forest_x + forest_width):
                if 0 <= x < self.width and 0 <= y < self.height:
                    self.grid[y][x] = ground_tile

        # Place trees
        for y_tree in range(forest_y, forest_y + forest_height):
            for x_tree in range(forest_x, forest_x + forest_width):
                if 0 <= x_tree < self.width and 0 <= y_tree < self.height: # Ensure within global map
                    if random.random() < tree_density:
                        self.grid[y_tree][x_tree] = tree_tile

                        # Place leaves around the tree
                        for dy in (-1, 0, 1):
                            for dx in (-1, 0, 1):
                                if dx == 0 and dy == 0: # Skip the trunk itself
                                    continue

                                nx, ny = x_tree + dx, y_tree + dy

                                # Check global map boundaries
                                if not (0 <= nx < self.width and 0 <= ny < self.height):
                                    continue
                                # Check forest area boundaries
                                if not (forest_x <= nx < forest_x + forest_width and \
                                          forest_y <= ny < forest_y + forest_height):
                                    continue

                                # Don't overwrite other tree trunks with leaves
                                if self.grid[ny][nx].id != tree_tile.id:
                                    self.grid[ny][nx] = leaves_tile

    def generate_map(self, generator_type="mixed"): # Default to "mixed"
        grass_fill = create_tile_from_definition("GRASS_FLOOR")
        self.grid = [[grass_fill for _ in range(self.width)] for _ in range(self.height)]

        if generator_type == "mixed":
            # Building in the top-left quadrant
            building_width = self.width // 2 - 2 # ensure some margin
            building_height = self.height // 2 - 2

            # Adjust building parameters to fit its designated quadrant
            building_start_x = 1
            building_start_y = 1

            if building_width > 5 and building_height > 5:
                self.generate_building(start_x=building_start_x, start_y=building_start_y,
                                       building_max_width=building_width, building_max_height=building_height,
                                       num_rooms=random.randint(2,4),
                                       max_room_size=min(building_width // 2, building_height // 2, 7)) # cap room size

            # Forest in the bottom-right quadrant
            forest_x = self.width // 2 + 1
            forest_y = self.height // 2 + 1
            forest_w = self.width - forest_x -1 # ensure margin
            forest_h = self.height - forest_y -1

            if forest_w > 0 and forest_h > 0:
                self.generate_forest(forest_x, forest_y, forest_w, forest_h, tree_density=0.25)

        elif generator_type == "building":
            # Fill with grass first, then place building
            self.generate_building(start_x=0, start_y=0, building_max_width=self.width, building_max_height=self.height,
                                   num_rooms=random.randint(3,6),
                                   min_room_size=4,
                                   max_room_size=8)
        elif generator_type == "forest":
            # Fill with grass (already done) then generate forest over whole map
            self.generate_forest(0, 0, self.width, self.height)
        elif generator_type == "caves":
            self._generate_caves_cellular_automata() # This method handles its own grid init
        else:
            # Default: grid is already filled with grass_fill
            pass


    def get_grid(self):
        return self.grid

    def get_tile(self, x, y):
        if 0 <= x < self.width and 0 <= y < self.height:
            return self.grid[y][x]
        return None

    def print_map_to_console(self):
        for y in range(self.height):
            row_str = ""
            for x in range(self.width):
                row_str += self.grid[y][x].texture_char + " "
            print(row_str)
