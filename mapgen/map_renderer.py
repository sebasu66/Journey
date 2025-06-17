from PIL import Image, ImageDraw

class MapRenderer:
    def __init__(self, tile_size_px=16):
        self.tile_size = tile_size_px

    def render(self, map_grid, output_filename="map.png"):
        if not map_grid:
            return
        height = len(map_grid)
        width = len(map_grid[0])
        img = Image.new("RGB", (width * self.tile_size, height * self.tile_size))
        draw = ImageDraw.Draw(img)
        for y, row in enumerate(map_grid):
            for x, tile in enumerate(row):
                x1 = x * self.tile_size
                y1 = y * self.tile_size
                x2 = x1 + self.tile_size
                y2 = y1 + self.tile_size
                draw.rectangle([x1, y1, x2, y2], fill=tile.color)
        img.save(output_filename)
