from domain.entities import SaleItem
from domain.validators import SaleItemValidator
from exceptions.exceptions import ProductNotFoundException, ShopNotFoundException, ProductAlreadyAssignedException
from repository.product_repo import ProductRepoFile
from repository.repo_sale_item import SaleItemRepoMemory
from repository.shop_repo import ShopRepoFile


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

        sale_item = SaleItem(product, shop, pret, stoc)
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
        pass


def test_add_sale_item():
    sale_repo = SaleItemRepoMemory()
    sale_val = SaleItemValidator()
    product_repo = ProductRepoFile('test_products.txt')
    shop_repo = ShopRepoFile('test_shops.txt')

    test_srv = SaleItemService(sale_repo, sale_val, product_repo, shop_repo)

    test_srv.add_sale_item('1', '1', 24, 200)
    assert (len(test_srv.get_all()) == 1)

    try:
        test_srv.add_sale_item('1','1', 5.5, 40)
        assert False
    except ProductAlreadyAssignedException:
        assert True

    try:
        test_srv.add_sale_item('100','1', 5.5, 40)
        assert False
    except ProductNotFoundException:
        assert True

    #also test for ValidationException, ShopNotFoundException
