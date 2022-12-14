with open('./input.txt', 'r') as f:
    rugsacks = f.read().split('\n')
    all_chars = list()
    total_priorities = 0
    dictionnary = {'a': 1, 'b': 2, 'c': 3, 'd': 4, 'e': 5, 'f': 6, 'g': 7, 'h': 8, 'i': 9, 'j': 10,
                   'k': 11, 'l': 12, 'm': 13, 'n': 14, 'o': 15, 'p': 16, 'q': 17, 'r': 18, 's': 19,
                   't': 20, 'u': 21, 'v': 22, 'w': 23, 'x': 24, 'y': 25, 'z': 26, 'A': 27, 'B': 28, 'C': 29,
                   'D': 30, 'E': 31,
                   'F': 32, 'G': 33, 'H': 34, 'I': 35, 'J': 36,
                   'K': 37, 'L': 38, 'M': 39, 'N': 40, 'O': 41, 'P': 42, 'Q': 43, 'R': 44, 'S': 45,
                   'T': 46, 'U': 47, 'V': 48, 'W': 49, 'X': 50, 'Y': 51, 'Z': 52}

    for group_number in range(len(rugsacks) // 3):
        starting_elf_nb = (group_number * 3)
        rugsack1 = set(rugsacks[starting_elf_nb])
        rugsack2 = set(rugsacks[starting_elf_nb + 1])
        rugsack3 = set(rugsacks[starting_elf_nb + 2])
        common_char = list(rugsack1.intersection(rugsack2).intersection(rugsack3))
        all_chars = all_chars + common_char

    for c in all_chars:
        total_priorities += dictionnary.get(c)

    print(total_priorities)
