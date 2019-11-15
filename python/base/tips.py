#
import collections
import heapq

# 批量赋值
a, b = [1, 2]
c, *other = [1, 2, 3, 4]
print(a, b, c, other)

*_, want = ['do not want1', 'do not want2', 'want']
print(want)

# deque 双端队列用于保存关心的最近几条信息
deque = collections.deque(maxlen=10)

# heapq 堆, 优先队列
heap = [1, 2, -1, 4, 5, 6]
heapq.heapify(heap)


class PriorityQueue:

    def __init__(self) -> None:
        self.queue = []
        self._idx = 0

    def push(self, item):
        heapq.heappush(self.queue, (item.priority, self._idx, item))
        self._idx += 1

    def pop(self):
        return heapq.heappop(self.queue)


# multi dict
from collections import defaultdict

multi_dict = defaultdict(set)
multi_dict['a'].add(1)
multi_dict['a'].add(1)
multi_dict['a'].add(2)
print(multi_dict)

# 利用切片内置函数提高可读性
str1 = '12345hefei.....'
name = slice(5, 10)
print(str1[name])


# 操作符重载
