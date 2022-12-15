class Knot:

    def __init__(self, parent, child, number):
        self.x = 0
        self.y = 0
        self.parent = parent
        self.child = child
        self.number = number
        self.all_positions = {(0, 0)}

    def update_childs(self):
        change_pos(self.child, self)

    def add_knots(self, n):
        current_knot = self
        for i in range(n):
            new_knot = Knot(current_knot, None, i + 1)
            current_knot.child = new_knot
            current_knot = new_knot

def change_pos(knot, parent):
    if parent.x - knot.x > 1:
        # parent goes right with gap
        if parent.y - knot.y >= 1:
            knot.y += 1
        if parent.y - knot.y <= -1:
            knot.y -= 1
        # bring child right
        knot.x += 1
    elif knot.x - parent.x > 1:
        # parent goes left with gap
        if parent.y - knot.y >= 1:
            knot.y += 1
        if parent.y - knot.y <= -1:
            knot.y -= 1
        # bring child right
        knot.x -= 1
    if parent.y - knot.y > 1:
        # parent goes up with gap
        if parent.x - knot.x >= 1:
            knot.x += 1
        if parent.x - knot.x <= -1:
            knot.x -= 1
        knot.y += 1
    elif parent.y - knot.y < -1:
        # parent goes down with gap
        if parent.x - knot.x >= 1:
            knot.x += 1
        if parent.x - knot.x <= -1:
            knot.x -= 1
        # bring child down
        knot.y -= 1
    # Add current knot position to list of all positions
    knot.all_positions.add((knot.x, knot.y))
    if knot.child is not None:
        # update child
        knot.update_childs()

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
            head.update_childs()
    knot = head
    for _ in range(10):
        print('Knot ' + knot.number.__str__() + ' has been on ' + len(knot.all_positions).__str__() + ' positions.')
        knot = knot.child
