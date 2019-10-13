#  generator
def generator():
    for i in [1, 2, 3]:
        yield i


# generator exp
gen = (i for i in range(10))

print(generator())
print(gen)
# 0
print(next(gen))
# 1
print(next(gen))

# use enumerate when need idx
flavors = ['music', 'food']
for idx, value in enumerate(flavors):
    print('idx : %s value:%s' % (idx, value))

