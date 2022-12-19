class ErrorException(Exception):
    pass

class NotFindBug(ErrorException):
    """
    Cream erorarea custom NotFindBug folosindu-ne de mostenire
    """
    def __init__(self,msg):
        """
        Constructorul
        :param msg: mesajul de eroare(pe care il aruncam)
        """
        self.__msg=msg

    def __str__(self):
        """
        Suprascriem metoda str pentru a putea afisa mesajul aruncat
        """
        return self.__msg