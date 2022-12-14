def is_tree_visible(tree_lines, i, j):
    tree = tree_lines[i][j]
    is_tree_visible_left = True
    is_tree_visible_right = True
    is_tree_visible_top = True
    is_tree_visible_bottom = True
    tree_line = [*tree_lines[i]]
    tree_column = []
    for line in tree_lines:
        tree_column.append([*line][j])
    for posx in range(len(tree_line)):
        if posx < j:
            tree_pos = tree_line[posx]
            if int(tree_pos) >= int(tree):
                is_tree_visible_left = False
        elif posx > j:
            tree_pos = tree_line[posx]
            if int(tree_pos) >= int(tree):
                is_tree_visible_right = False
    for posy in range(len(tree_column)):
        if posy < i:
            tree_pos = tree_column[posy]
            if int(tree_pos) >= int(tree):
                is_tree_visible_top = False
        elif posy > i:
            tree_pos = tree_column[posy]
            if int(tree_pos) >= int(tree):
                is_tree_visible_bottom = False
    if is_tree_visible_right or is_tree_visible_bottom or is_tree_visible_left or is_tree_visible_top:
        return True
    else:
        return False


with open('./input.txt', 'r') as f:
    tree_lines = f.read().strip().split('\n')
    total_visible_trees = 0
    total_invisible_trees = 0
    total_trees = len(tree_lines) * len([*tree_lines[0]])
    for i in range(len(tree_lines)):
        length = len(tree_lines[0])
        for j in range(length):
            if is_tree_visible(tree_lines, i, j):
                total_visible_trees += 1
            else:
                total_invisible_trees += 1
    print(
        "Total trees : " + total_trees.__str__()
        + ".\nVisible : " + total_visible_trees.__str__()
        + ".\nInvisible : " + total_invisible_trees.__str__())
