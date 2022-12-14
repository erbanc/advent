class Knot:

    def __init__(self, parent, child, number):
        self.x = 0
        self.y = 0
        self.parent = parent
        self.child = child
        self.number = number
        self.all_positions = {(0, 0)}

    def add_knots(self, n):
        current_knot = self
        for i in range(n):
            new_knot = Knot(current_knot, None, i + 1)
            current_knot.child = new_knot
            current_knot = new_knot


def change_pos(knot, x_parent, y_parent):
    if (x_parent - knot.x) > 1:
        if abs(y_parent - knot.y) >= 1:
            knot.y = y_parent
        knot.x += 1
    elif (x_parent - knot.x) < 1:
        if abs(y_parent - knot.y) >= 1:
            knot.y = y_parent
        knot.x -= 1
    if (y_parent - knot.y) > 1:
        if abs(x_parent - knot.x) >= 1:
            knot.x = x_parent
        knot.y += 1
    elif (y_parent - knot.y) < 1:
        if abs(x_parent - knot.x) >= 1:
            knot.x = x_parent
        knot.y -= 1
    knot.all_positions.add((knot.x, knot.y))
    print('Node ' + knot.number.__str__() + ' position : ' + (knot.x, knot.y).__str__())
    if knot.child is not None:
        change_pos(knot.child, knot.x, knot.y)

with open('./input.txt', 'r') as f:
    head_movements = f.read().strip().split('\n')
    head = Knot(None, None, 'H')
    # init knots
    head.add_knots(9)
    for head_movement in head_movements:
        direction = head_movement.split()[0]
        nb_steps = int(head_movement.split()[1])
        for n in range(nb_steps):
            if direction == "U":
                head.y += 1
            if direction == 'D':
                head.y -= 1
            if direction == 'L':
                head.x -= 1
            if direction == 'R':
                head.x += 1
            head.all_positions.add((head.x, head.y))
            change_pos(head.child, head.x, head.y)
    knot_9 = head.child.child.child.child.child.child.child.child.child
    print('Node ' + knot_9.number.__str__() + ' has been on ' + len(knot_9.all_positions).__str__() + ' positions.' )
