heights = [2, 0, 4, 2, 0, 3, 2, 5, 0, 3, 0, 2]
left_wall = heights[0]
right_wall = 0
for wall in heights[1:]:
    if wall >= left_wall:
        right_wall = wall
