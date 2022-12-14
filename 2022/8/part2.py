def get_scenic_score(tree_lines, i, j):
    tree = tree_lines[i][j]
    viewing_distance_left = 0
    viewing_distance_right = 0
    viewing_distance_top = 0
    viewing_distance_bottom = 0
    tree_line = [*tree_lines[i]]
    tree_column = []
    for line in tree_lines:
        tree_column.append([*line][j])
    is_blocked_left = j == 0
    for pos_left in range(j - 1, -1, -1):
        if not is_blocked_left:
            tree_pos = tree_line[pos_left]
            if int(tree_pos) >= int(tree):
                is_blocked_left = True
            viewing_distance_left += 1
    is_blocked_right = j == len(tree_line)
    for pos_right in range(j + 1, len(tree_line)):
        if not is_blocked_right:
            tree_pos = tree_line[pos_right]
            if int(tree_pos) >= int(tree):
                is_blocked_right = True
            viewing_distance_right += 1
    is_blocked_top = i == 0
    for pos_top in range(i - 1, -1, -1):
        if not is_blocked_top:
            tree_pos = tree_column[pos_top]
            if int(tree_pos) >= int(tree):
                is_blocked_top = True
            viewing_distance_top += 1
    is_blocked_bottom = i == len(tree_column)
    for pos_bottom in range(i + 1, len(tree_column)):
        if not is_blocked_bottom:
            tree_pos = tree_column[pos_bottom]
            if int(tree_pos) >= int(tree):
                is_blocked_bottom = True
            viewing_distance_bottom += 1
    scenic_score = viewing_distance_top * viewing_distance_right * viewing_distance_left * viewing_distance_bottom
    return scenic_score


with open('./input.txt', 'r') as f:
    tree_lines = f.read().strip().split('\n')
    max_scenic_score = 0
    for i in range(len(tree_lines)):
        length = len(tree_lines[0])
        for j in range(length):
            tree_scenic_score = get_scenic_score(tree_lines, i, j)
            if tree_scenic_score > max_scenic_score:
                max_scenic_score = tree_scenic_score
    print("Max scenic score : " + max_scenic_score.__str__())
