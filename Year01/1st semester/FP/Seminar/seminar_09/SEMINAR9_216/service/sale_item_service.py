from domain.entities import SaleItem
from exceptions.exceptions import ProductNotFoundException, ShopNotFoundException


class SaleItemService:
    def __init__(self, sale_repo, sale_val, product_repo, shop_repo):
        self.__sale_repo = sale_repo
        self.__sale_val = sale_val
        self.__product_repo = product_repo
        self.__shop_repo = shop_repo

    def add_sale_item(self, product_id, shop_id, pret, stoc):
        """
        Adauga un Sale Item
        :param product_id:id-ul produsului
        :type product_id:str
        :param shop_id:id-ul magazinului
        :type shop_id:str
        :param pret:pretul produsului in magazinul dat
        :type pret:float
        :param stoc:numarul de unitati in stoc in magazinul dat
        :type stoc:int
        :return: SaleItem adaugat
        :rtype: SaleItem
        :raises: ProductNotFoundException daca nu exista produs cu id dat
                 ShopNotFoundException daca nu exista magazin cu id dat
                 ValidationException daca item-ului nu e valid
                 ProductAlreadyAssigned daca item-ul deja exista pentru magazin si produs dat
        """

        product = self.__product_repo.find(product_id)
        if product is None:
            raise ProductNotFoundException()

        shop = self.__shop_repo.find(shop_id)
        if shop is None:
            raise ShopNotFoundException()

        sale_item = SaleItem(product_id, shop_id, pret, stoc)
        self.__sale_val.validate(sale_item)
        self.__sale_repo.store(sale_item)
        return sale_item

    def get_all(self):
        return self.__sale_repo.get_all()

    def get_top_locations_for_product(self, product_id, n=3):
        """
        Cauta primele 3 magazine cu cel mai bun pret pentru produsul dat
        :param product_id: produsul dat
        :type product_id: str
        :return: lista de ProductPrice DTOs
        :rtype: list of ProductPrice objects
        """
        product = self.__product_repo.find(product_id)
        if product is None:
            raise ProductNotFoundException()

        product_prices_list = self.__sale_repo.get_all_for_product(product_id)
        product_prices_list = sorted(product_prices_list, key=lambda x: x.getPret())
        product_prices_list = product_prices_list[:n]

        for prod_price in product_prices_list:
            prod_price.setNumeProdus(product.getName())
            shop = self.__shop_repo.find(prod_price.getShopId())
            prod_price.setNumeMagazin(shop.getName())
            prod_price.setLocatieMagazin(shop.getLocation())

        return product_prices_list

    def get_number_of_available_items(self):
        """
        Calculeaza numarul de produse puse in vanzare pentru fiecare magazin
        :return: lista de ShopProducts DTO
        :rtype: list of ShopProducts objects
        """
        #implementation similar to the first report
        pass

