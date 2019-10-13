class Foo:
    def __init__(self) -> None:
        self.__private_field = 'private field value'


foo = Foo()
# can throw an error
foo.__private_field
