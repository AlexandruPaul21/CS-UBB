from domain.dtos import ProductPrice
from domain.entities import SaleItem
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

    def delete_all(self):
        self.__items = []


class SaleItemRepoFile(SaleItemRepoMemory):
    def __init__(self, filename):
        SaleItemRepoMemory.__init__(self)
        self.__filename = filename
        self.__load_from_file()

    def __load_from_file(self):
        """
        Incarca sale items din fisier
        """
        try:
            f = open(self.__filename, 'r')
        except IOError:
            return

        lines = f.readlines()
        for line in lines:
            product_id, shop_id, price, stock = [token.strip() for token in line.split(';')]
            sale_item = SaleItem(product_id, shop_id, float(price),int(stock))
            SaleItemRepoMemory.store(self, sale_item)

        f.close()

    def __save_to_file(self):
        """
        Salveaza sale items in fisier
        """
        all_items = SaleItemRepoMemory.get_all(self)
        f = open(self.__filename, 'w')
        for item in all_items:
            item_string = str(item.getProductId()) + ';' + str(item.getShopId()) + ';' + str(
                item.getPret()) + ';' + str(item.getStoc()) + '\n'
            f.write(item_string)

    def store(self, sale_item):
        """
        Adauga un sale item
        :param sale_item: sale item de adaugat
        :type sale_item: SaleItem
        :return:-; se adauga SaleItem in lista si in fisier
        :raises: ProductAlreadyAssignedException daca exista deja item pentru produs, magazinul dat
        """
        SaleItemRepoMemory.store(self, sale_item)
        self.__save_to_file()

    def find(self, sale_item):
        # TO DO: add specification
        return SaleItemRepoMemory.find(self, sale_item)

    def get_all(self):
        # TO DO: add specification

        return SaleItemRepoMemory.get_all(self)

    def delete_all(self):
        """
        Sterge toti sale items din lista (si fisier)
        """
        SaleItemRepoMemory.delete_all(self)
        self.__save_to_file()

    def get_all_for_product(self, product_id):
        """
        Gaseste toate magazinele in care este vandut produsul cu id product_id
        :param product_id: id-ul produsului
        :type product_id: str
        :return: lista de ProductPrice DTO care contine id produs, id magazin, pret
        :rtype: list of ProductPrice objects
        """
        sale_items = self.get_all()
        dto_list = []
        for sale_item in sale_items:
            if sale_item.getProductId() == product_id:
                dto = ProductPrice(product_id, sale_item.getShopId(), sale_item.getPret())
                dto_list.append(dto)
        return dto_list

    def get_sales(self):
        """
        Calculeaza numarul de produse puse in vanzare pentru fiecare magazin
        :return: lista de ShopProducts DTO (id magazin, numar magazine)
        :rtype: list of ShopProducts objects
        """
        pass
