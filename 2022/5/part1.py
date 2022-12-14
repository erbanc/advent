with open('./input.txt', 'r') as f:
    crates_matrix = [['N', 'Q', 'L', 'S', 'C', 'Z', 'P', 'T'],
                     ['G', 'C', 'H', 'V', 'T', 'P', 'L'],
                     ['F', 'Z', 'C', 'D'],
                     ['C', 'V', 'M', 'L', 'D', 'T', 'W', 'G'],
                     ['C', 'W', 'P'],
                     ['Z', 'S', 'T', 'C', 'D', 'J', 'F', 'P'],
                     ['D', 'B', 'G', 'W', 'V'],
                     ['W', 'H', 'Q', 'S', 'J', 'N'],
                     ['V', 'L', 'S', 'F', 'Q', 'C', 'R']]

    instructions = f.read().strip().split('\n')[10:]

    for instruction in instructions:
        trimmed_instruction = instruction.replace('move ', '').replace(' from ', ",").replace(' to ', ',').split(',')
        num_col_origin = int(trimmed_instruction[1])
        colonne_origin = list(crates_matrix[num_col_origin - 1])
        colonne_origin.reverse()
        nb_crates = int(trimmed_instruction[0])
        num_col_dest = int(trimmed_instruction[2])
        colonne_dest = list(crates_matrix[num_col_dest - 1])
        colonne_dest.reverse()
        for i in range(nb_crates):
            crate = colonne_origin.pop()
            colonne_dest.append(crate)
        colonne_dest.reverse()
        colonne_origin.reverse()
        crates_matrix[num_col_origin - 1] = colonne_origin
        crates_matrix[num_col_dest - 1] = colonne_dest

    answer = ''
    for colonne in crates_matrix:
        answer += colonne[0]
    print(answer)
