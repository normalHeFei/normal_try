from typing import Any

print((1, 'b') > (1, 'd'))


# 可比较 (运算符重载)
class Item:
    def __lt__(self, other):
        return self.idx < other.idx

    def __init__(self, idx):
        self.idx = idx



print(Item(1) > Item(2))

