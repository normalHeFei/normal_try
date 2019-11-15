# 推导式
dict1 = {
    'name': 'dd',
    'age': '30'
}
'''
    {}: 字典推导: 表达式 变量 source
    (): 生成器
    []: 列表    
'''
name = {k for k in dict1.keys() if k == 'name'}
print(name)

add_pre_rst = {'pre_' + k: 'pre_' + v for k, v in dict1.items()}
print(add_pre_rst)


