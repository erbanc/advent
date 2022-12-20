def extract_map():
    map_raw_rows = f.read().splitlines()
    map_raw_rows.reverse()
    map_to_return = []
    for i in range(len(map_raw_rows)):
        map_to_return.append([*map_raw_rows[i]])
    return map_to_return


def find_letter_coordinates(input_map, letter):
    for y in range(len(input_map)):
        for x in range(len(input_map[0])):
            if input_map[y][x] == letter:
                return x, y


def get_possibilities(current_path, map):
    possibilities = []
    current_coord = current_path[len(current_path) - 1]
    previous = (0, 0)
    if len(current_path) > 1:
        previous = current_path[len(current_path) - 2]
    x = current_coord[0]
    y = current_coord[1]
    v = map[y][x]
    if v == 'S':
        v = 'a'
    if ord(v) + 1 >= ord(map[y][x - 1]):
        possibilities.append((x - 1, y))  # left
    if ord(v) + 1 >= ord(map[y][x + 1]):
        possibilities.append((x + 1, y))  # right
    if ord(v) + 1 >= ord(map[y + 1][x]):
        possibilities.append((x, y + 1))  # up
    if ord(v) + 1 >= ord(map[y - 1][x]):
        possibilities.append((x, y - 1))  # down
    # remove if it is not in the map or previous
    valid_possibilities = []
    for possibility in possibilities:
        if 0 < possibility[0] < len(map[0]) and 0 < possibility[1] < len(map) and possibility != previous:
            valid_possibilities.append(possibility)
    return valid_possibilities


def continue_path(current_path, map, map_coord_with_bestpath, end):
    current_coord = current_path[len(current_path) - 1]
    if current_coord == end:
        print('Found an end in ' + (len(current_path) - 1).__str__() + " steps")
    else:
        # looking for current in map coord with bestpath, if it is in, kill it if not the best, keep it otherwise
        found_in_previous = False
        for i in range(len(map_coord_with_bestpath)):
            coord_path = map_coord_with_bestpath[i]
            if coord_path[0] == current_coord:
                found_in_previous = True
                if len(coord_path[1]) < len(current_path):
                    return
                else:
                    map_coord_with_bestpath[i] = (current_coord, current_path)
        if not found_in_previous:
            map_coord_with_bestpath.append((current_coord, current_path))
        possibilities = get_possibilities(current_path, map)
        for possibility in possibilities:
            current_path.append(possibility)
            continue_path(current_path, map, map_coord_with_bestpath, end)


with open('./input.txt', 'r') as f:
    input_map = extract_map()
    # find starting coordinates
    start = find_letter_coordinates(input_map, 'S')
    end = find_letter_coordinates(input_map, 'E')
    there_are_still_unexplored_paths = True
    current_path = [start]
    map_coord_with_bestpath = []
    all_paths = [[]]
    steps = 0
    while there_are_still_unexplored_paths:
        continue_path(current_path, input_map, map_coord_with_bestpath, end)
