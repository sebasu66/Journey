import json

class FoundryDataExtractor:
    def __init__(self, tile_size_px=100):
        self.tile_size = tile_size_px

    def _tile_center(self, x, y):
        return (x * self.tile_size + self.tile_size // 2,
                y * self.tile_size + self.tile_size // 2)

    def extract(self, map_grid):
        walls = []
        doors = []
        lights = []
        height = len(map_grid)
        width = len(map_grid[0]) if height else 0

        for y, row in enumerate(map_grid):
            for x, tile in enumerate(row):
                if tile.emits_light:
                    cx, cy = self._tile_center(x, y)
                    light = {
                        "x": cx,
                        "y": cy,
                        **tile.emits_light
                    }
                    lights.append(light)

                if tile.blocks_movement:
                    # Check each edge (N,S,E,W) to see if a wall is needed
                    if y > 0 and not map_grid[y-1][x].blocks_movement:
                        x1, y1 = x * self.tile_size, y * self.tile_size
                        x2, y2 = x1 + self.tile_size, y1
                        walls.append([x1, y1, x2, y2])
                    if y < height-1 and not map_grid[y+1][x].blocks_movement:
                        x1, y1 = x * self.tile_size, (y+1) * self.tile_size
                        x2, y2 = x1 + self.tile_size, y1
                        walls.append([x1, y1, x2, y2])
                    if x > 0 and not map_grid[y][x-1].blocks_movement:
                        x1, y1 = x * self.tile_size, y * self.tile_size
                        x2, y2 = x1, y1 + self.tile_size
                        walls.append([x1, y1, x2, y2])
                    if x < width-1 and not map_grid[y][x+1].blocks_movement:
                        x1, y1 = (x+1) * self.tile_size, y * self.tile_size
                        x2, y2 = x1, y1 + self.tile_size
                        walls.append([x1, y1, x2, y2])

                    if tile.is_door:
                        cx, cy = self._tile_center(x, y)
                        doors.append({"x": cx, "y": cy})

        return {"walls": walls, "doors": doors, "lights": lights}
