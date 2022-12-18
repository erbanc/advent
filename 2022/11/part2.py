class Monkey:

    def __init__(self, number, items):
        self.number = number
        self.items = items
        self.total_operations = 0

    def inspect_item(self, old):
        self.total_operations += 1
        if self.number == 0:
            return old * 7
        elif self.number == 1:
            return old + 3
        elif self.number == 2:
            return old + 4
        elif self.number == 3:
            return old + 5
        elif self.number == 4:
            return old * 5
        elif self.number == 5:
            return old * old
        elif self.number == 6:
            return old + 8
        elif self.number == 7:
            return old + 1
        else:
            return old

    def find_monke_for_item(self, x):
        if self.number == 0:
            if x % 2 == 0:
                return 7
            else:
                return 1
        elif self.number == 1:
            if x % 7 == 0:
                return 2
            else:
                return 4
        elif self.number == 2:
            if x % 13 == 0:
                return 5
            else:
                return 4
        elif self.number == 3:
            if x % 19 == 0:
                return 6
            else:
                return 0
        elif self.number == 4:
            if x % 11 == 0:
                return 5
            else:
                return 3
        elif self.number == 5:
            if x % 5 == 0:
                return 6
            else:
                return 3
        elif self.number == 6:
            if x % 3 == 0:
                return 0
            else:
                return 7
        elif self.number == 7:
            if x % 17 == 0:
                return 2
            else:
                return 1
        else:
            return 0


monke_0 = Monkey(0, [62, 92, 50, 63, 62, 93, 73, 50])
monke_1 = Monkey(1, [51, 97, 74, 84, 99])
monke_2 = Monkey(2, [98, 86, 62, 76, 51, 81, 95])
monke_3 = Monkey(3, [53, 95, 50, 85, 83, 72])
monke_4 = Monkey(4, [59, 60, 63, 71])
monke_5 = Monkey(5, [92, 65])
monke_6 = Monkey(6, [78])
monke_7 = Monkey(7, [84, 93, 54])
monkeys = [monke_0, monke_1, monke_2, monke_3, monke_4, monke_5, monke_6, monke_7]


def reduce_item(it):
    return it % 9699690


for _ in range(10000):
    print(_.__str__())
    for monke in monkeys:
        for item in monke.items:
            item = monke.inspect_item(item)
            item = reduce_item(item)
            monke_recipient_nb = monke.find_monke_for_item(item)
            monkeys[monke_recipient_nb].items.append(item)
        monke.items = []
top1 = 0
top2 = 0
for monke in monkeys:
    if monke.total_operations > top1:
        top2 = top1
        top1 = monke.total_operations
    elif monke.total_operations > top2:
        top2 = monke.total_operations
print(top1)
print(top2)
print(top1 * top2)
