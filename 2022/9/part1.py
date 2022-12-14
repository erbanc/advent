with open('./input.txt', 'r') as f:
    head_movements = f.read().strip().split('\n')
    starting_position = (0, 0)
    positions_visited = {(0, 0)}
    tail_position = starting_position
    head_position = starting_position
    for head_movement in head_movements:
        direction = head_movement.split()[0]
        nb_steps = int(head_movement.split()[1])
        for n in range(nb_steps):
            if direction == "U":
                head_position = (head_position[0], head_position[1] + 1)
                if (head_position[1] - tail_position[1]) > 1:
                    tail_position = (tail_position[0], tail_position[1] + 1)
                    if (head_position[0] - tail_position[0]) == 1:
                        tail_position = (tail_position[0] + 1, tail_position[1])
                    elif (head_position[0] - tail_position[0]) == -1:
                        tail_position = (tail_position[0] - 1, tail_position[1])
            if direction == 'D':
                head_position = (head_position[0], head_position[1] - 1)
                if (head_position[1] - tail_position[1]) < -1:
                    tail_position = (tail_position[0], tail_position[1] - 1)
                    if (head_position[0] - tail_position[0]) == 1:
                        tail_position = (tail_position[0] + 1, tail_position[1])
                    elif (head_position[0] - tail_position[0]) == -1:
                        tail_position = (tail_position[0] - 1, tail_position[1])
            if direction == 'L':
                head_position = (head_position[0] - 1, head_position[1])
                if (head_position[0] - tail_position[0]) < -1:
                    tail_position = (tail_position[0] - 1, tail_position[1])
                    if (head_position[1] - tail_position[1]) == 1:
                        tail_position = (tail_position[0], tail_position[1] + 1)
                    elif (head_position[1] - tail_position[1]) == -1:
                        tail_position = (tail_position[0], tail_position[1] - 1)
            if direction == 'R':
                head_position = (head_position[0] + 1, head_position[1])
                if (head_position[0] - tail_position[0]) > 1:
                    tail_position = (tail_position[0] + 1, tail_position[1])
                    if (head_position[1] - tail_position[1]) == 1:
                        tail_position = (tail_position[0], tail_position[1] + 1)
                    elif (head_position[1] - tail_position[1]) == -1:
                        tail_position = (tail_position[0], tail_position[1] - 1)
            positions_visited.add(tail_position)
    print(len(positions_visited).__str__())
