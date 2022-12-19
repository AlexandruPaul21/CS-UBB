from exceptions.exceptions import ProductAlreadyAssignedException


class SaleItemRepoMemory:
    def __init__(self):
        self.__items = []

    def find(self, sale_item):
        """
        Cauta un sale item in lista
        :param sale_item: sale item cautat
        :type sale_item: SaleItem
        :return: SaleItem daca exista in lista, None altfel
        :rtype: SaleItem
        """
        for item in self.__items:
            if item == sale_item:
                return item
        return None

    def store(self, sale_item):
        """
        Adauga un sale item
        :param sale_item: sale item de adaugat
        :type sale_item: SaleItem
        :return:-; se adauga SaleItem in lista
        :raises: ProductAlreadyAssignedException daca exista deja item pentru produs, magazinul dat
        """
        s = self.find(sale_item)
        if s is not None:
            raise ProductAlreadyAssignedException()

        self.__items.append(sale_item)

    def get_all(self):
        """
        Returneaza toti itemii din lista
        :return: lista de itemi
        :rtype: list of SaleItem objects
        """
        return self.__items


def test_store_sale_item():
    pass
