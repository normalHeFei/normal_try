dict = {
    'name': 'hefei',
    'age': '30'
}

name = {k for k in dict.keys() if k == 'name'}
print(name)

convert_dict = {k: dict.get(k) for k in dict if k == 'name'}
print(convert_dict)
