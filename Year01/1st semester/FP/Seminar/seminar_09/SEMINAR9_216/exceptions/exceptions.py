class CandyStoreException(Exception):
    pass


class ValidationException(CandyStoreException):
    def __init__(self, msgs):
        """
        :param msgs: lista de mesaje de eroare
        :type msgs: msgs
        """
        self.__err_msgs = msgs

    def getMessages(self):
        return self.__err_msgs

    def __str__(self):
        return 'Validation Exception: ' + str(self.__err_msgs)


class RepositoryException(CandyStoreException):
    def __init__(self, msg):
        self.__msg = msg

    def getMessage(self):
        return self.__msg

    def __str__(self):
        return 'Repository Exception: ' + str(self.__msg)


class DuplicateIDException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "ID duplicat.")


class ProductAlreadyAssignedException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Sale Item existent pentru produs si magazin dat.")


class ProductNotFoundException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Produsul nu a fost gasit. ")


class ShopNotFoundException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Magazinul nu a fost gasit. ")


class SaleItemNotFoundException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Acest Sale Item nu a fost gasit. ")


class NotEnoughItemsException(CandyStoreException):
    def __init__(self):
        pass


class CorruptedFileException(CandyStoreException):
    def __init__(self):
        pass