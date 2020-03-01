# 方法装饰器

def log_able(level, msg):
    def log_wrap(fun):
        def origin_fun(*args, **kwargs):
            print('origin fun args: %s' % str(args))
            fun()
            print('level:%s, msg: %s' % (level, msg))

        return origin_fun

    return log_wrap


@log_able('DEBUG', 'print log msg')
def hello():
    print('hello')


hello('fun arg value')


# 类装饰器
def add_method(fun_def):
    def wrap_cls(cls):
        cls.test_fun = fun_def
        return cls

    return wrap_cls


def test_method():
    print('added method')


@add_method(test_method)
class ClassNoMethod:
    pass


print(ClassNoMethod.test_fun)

# property

