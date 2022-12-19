class DomainException(Exception):
    """
    Clasa de exceptie custom pentru erorile din domeniu
    """
    def __init__(self,msg):
        self.__msg=msg

    def __str__(self):
        return self.__msg

class NotFoundException(Exception):
    """
    Clasa de exceptie custom pentru erorile de tipul not found
    """
    def __init__(self,msg):
        self.__msg=msg

    def __str__(self):
        return self.__msg