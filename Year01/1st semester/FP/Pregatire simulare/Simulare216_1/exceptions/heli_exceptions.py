class Heli_Exceptions(Exception):
    pass

class HeliNotFoundException(Heli_Exceptions):
    """
    Custom exceptions for HeliNotFound
    """
    def __init__(self,msg):
        self.__msg=msg

    def __str__(self):
        return self.__msg