def foo(nums):
    sums = sum(nums)
    results = []
    for num in nums:
        result = 100 * num / sums
        results.append(result)
    return results


nums = [1, 2, 3]
# not use inter
print(foo(nums))


# use inter
def next_inter():
    for i in nums:
        yield i


# result is '[]', sum(nums) has been used  next_inter function.
print(foo(next_inter()))

'''
    how to solve?
        1.  wrap collection to an iter (iterator protocol)
'''


class NumsInter(object):

    def __init__(self, nums) -> None:
        self.nums = nums

    def __iter__(self):
        for num in self.nums:
            yield num


print(foo(NumsInter(nums)))


