"""
    match:
        1. simple string match method
        2. with wildcard of fnmatch
        3. regex. re.match
"""
print('2, 3, 4'.startswith(('2', '3')))

import fnmatch

print(fnmatch.fnmatch('foo9.txt', 'foo[0-9].txt'))

import re

groups = re.compile(r'(\d+)').findall('123d456d789')
print(groups)



