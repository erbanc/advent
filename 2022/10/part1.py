with open('./input.txt', 'r') as f:
    instructions = f.read().strip().split('\n')
    x = 1
    current_cycle = 0
    signal_strength = 0
    for instruction in instructions:
        current_cycle += 1
        if (current_cycle - 20) % 40 == 0:
            print("x value for loop " + current_cycle.__str__() + ": " + x.__str__())
            signal_strength += (x * current_cycle)
        if instruction != 'noop':
            current_cycle += 1
            if (current_cycle - 20) % 40 == 0:
                print("x value for loop " + current_cycle.__str__() + ": " + x.__str__())
                signal_strength += (x * current_cycle)
            x += int(instruction.split()[1])
    print(signal_strength.__str__())

