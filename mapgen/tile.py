class Tile:
    def __init__(self, id, char, color=(0,0,0), blocks_movement=False,
                 blocks_light=False, is_door=False, is_window=False,
                 emits_light=None):
        self.id = id
        self.char = char
        self.color = color
        self.blocks_movement = blocks_movement
        self.blocks_light = blocks_light
        self.is_door = is_door
        self.is_window = is_window
        self.emits_light = emits_light

    def __repr__(self):
        return f"Tile(id={self.id!r})"
