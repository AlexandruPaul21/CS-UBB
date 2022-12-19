class EventException(Exception):
    pass

class InvalidEventException(EventException):
    """
    Clasa de exceptie custom
    """

    def __init__(self, msg):
        self.__msg = msg

    def __str__(self):
        return self.__msg

