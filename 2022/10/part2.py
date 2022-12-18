def printPixel(is_lit):
    if is_lit:
        return "#"
    else:
        return "."


def increment_cycle(c, sprite_middle, crt):
    c = c + 1
    sprite_middle_corrected = sprite_middle + 1
    is_lit = False
    if c <= 40:
        if sprite_middle_corrected - 1 <= c <= sprite_middle_corrected + 1:
            is_lit = True
        crt[0] += printPixel(is_lit)
    elif c <= 80:
        if sprite_middle_corrected - 1 <= c - 40 <= sprite_middle_corrected + 1:
            is_lit = True
        crt[1] += printPixel(is_lit)
    elif c <= 120:
        if sprite_middle_corrected - 1 <= c - 80 <= sprite_middle_corrected + 1:
            is_lit = True
        crt[2] += printPixel(is_lit)
    elif c <= 160:
        if sprite_middle_corrected - 1 <= c - 120 <= sprite_middle_corrected + 1:
            is_lit = True
        crt[3] += printPixel(is_lit)
    elif c <= 200:
        if sprite_middle_corrected - 1 <= c - 160 <= sprite_middle_corrected + 1:
            is_lit = True
        crt[4] += printPixel(is_lit)
    elif c <= 240:
        if sprite_middle_corrected - 1 <= c - 200 <= sprite_middle_corrected + 1:
            is_lit = True
        crt[5] += printPixel(is_lit)
    return c


with open('./input.txt', 'r') as f:
    instructions = f.read().strip().split('\n')
    crt = ["", "", "", "", "", ""]
    sprite_middle = 1
    current_cycle = 0
    for instruction in instructions:
        current_cycle = increment_cycle(current_cycle, sprite_middle, crt)
        if instruction != 'noop':
            current_cycle = increment_cycle(current_cycle, sprite_middle, crt)
            sprite_middle += int(instruction.split()[1])
    print(crt[0] + "\n" + crt[1] + "\n" + crt[2] + "\n" + crt[3] + "\n" + crt[4] + "\n" + crt[5])

