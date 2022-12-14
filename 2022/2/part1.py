with open('./input.txt', 'r') as f:
    manches = f.read().split('\n')
    total_points = 0
    for manche in manches:
        points_manche = 0
        if manche.startswith("A"):
            if manche.endswith("X"):
                points_manche += 4
            elif manche.endswith("Y"):
                points_manche += 8
            elif manche.endswith("Z"):
                points_manche += 3
        elif manche.startswith("B"):
            if manche.endswith("X"):
                points_manche += 1
            elif manche.endswith("Y"):
                points_manche += 5
            elif manche.endswith("Z"):
                points_manche += 9
        elif manche.startswith("C"):
            if manche.endswith("X"):
                points_manche += 7
            elif manche.endswith("Y"):
                points_manche += 2
            elif manche.endswith("Z"):
                points_manche += 6
        total_points += points_manche
    print(total_points)
