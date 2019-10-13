# now we need a 'play song ability'

class PlaySongMixin:
    def play_song(self):
        print('play song by  %s ' % self.name)


class Vehicle:
    def travel(self, location):
        pass


class Car(Vehicle, PlaySongMixin):

    def __init__(self) -> None:
        self.name = 'car'

    def travel(self, location):
        print('travel use car')
        self.play_song()


class Boat(Vehicle, PlaySongMixin):

    def __init__(self) -> None:
        self.name = 'boat'

    def travel(self, location):
        print('travel use boat')
        self.play_song()


boat = Boat()
boat.travel('shanghai')
