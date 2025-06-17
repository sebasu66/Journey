import random
from .tile import Tile

class MapGenerator:
    def __init__(self, width, height, floor_tile=None, wall_tile=None):
        self.width = width
        self.height = height
        self.floor_tile = floor_tile or Tile(id="floor", char=".", color=(50,50,50))
        self.wall_tile = wall_tile or Tile(id="wall", char="#", color=(130,110,90), blocks_movement=True, blocks_light=True)
        self.grid = [[self.floor_tile for _ in range(width)] for _ in range(height)]

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
                    # Treat out-of-bounds as walls
                    count += 1
        return count

    def generate_caves(self, fill_probability=0.45, generations=5):
        self.grid = [[self.wall_tile if random.random() < fill_probability else self.floor_tile
                      for _ in range(self.width)] for _ in range(self.height)]

        for _ in range(generations):
            new_grid = [[self.grid[y][x] for x in range(self.width)] for y in range(self.height)]
            for y in range(self.height):
                for x in range(self.width):
                    wall_count = self._count_wall_neighbors(x, y)
                    tile = self.grid[y][x]
                    if tile.blocks_movement:
                        if wall_count >= 4:
                            new_grid[y][x] = self.wall_tile
                        else:
                            new_grid[y][x] = self.floor_tile
                    else:
                        if wall_count >= 5:
                            new_grid[y][x] = self.wall_tile
                        else:
                            new_grid[y][x] = self.floor_tile
            self.grid = new_grid
