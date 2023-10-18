from domain.dtos import ProductPrice, ShopProducts
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
        product = self.__product_repo.find(product_id)
        if product is None:
            raise ProductNotFoundException()

        all_sale_items = self.__sale_repo.get_all()
        product_prices_list = []
        for sale_item in all_sale_items:
            if sale_item.getProduct().getId() == product_id:
                prod_price_dto = ProductPrice(sale_item.getProduct().getName(), sale_item.getShop().getName(),
                                              sale_item.getShop().getLocation(), sale_item.getPret())
                product_prices_list.append(prod_price_dto)

        product_prices_list = sorted(product_prices_list, key=lambda x: x.getPret(), reverse=False)
        # handle the case when we don't have enough items in the list - either print the ones we have
        # or throw exception
        product_prices_list = product_prices_list[:n]
        return product_prices_list

    def get_number_of_available_items(self):
        """
        Calculeaza numarul de produse puse in vanzare pentru fiecare magazin
        :return: lista de ShopProducts DTO
        :rtype: list of ShopProducts objects
        """
        all_sale_items = self.__sale_repo.get_all()
        shop_products_dtos = {}
        for sale_item in all_sale_items:
            if sale_item.getShop().getId() not in shop_products_dtos:
                shop_products_dtos[sale_item.getShop().getId()] = ShopProducts(sale_item.getShop().getName(),
                                                                               sale_item.getShop().getLocation())
            else:
                shop_products_dtos[sale_item.getShop().getId()].increaseNoProducts()

        return list(shop_products_dtos.values())


def test_add_sale_item():
    sale_repo = SaleItemRepoMemory()
    sale_val = SaleItemValidator()
    product_repo = ProductRepoFile('test_products.txt')
    shop_repo = ShopRepoFile('test_shops.txt')

    test_srv = SaleItemService(sale_repo, sale_val, product_repo, shop_repo)

    test_srv.add_sale_item('1', '1', 24, 200)
    assert (len(test_srv.get_all()) == 1)

    try:
        test_srv.add_sale_item('1', '1', 5.5, 40)
        assert False
    except ProductAlreadyAssignedException:
        assert True

    try:
        test_srv.add_sale_item('100', '1', 5.5, 40)
        assert False
    except ProductNotFoundException:
        assert True


def test_get_top_locations():
    sale_repo = SaleItemRepoMemory()
    sale_val = SaleItemValidator()
    product_repo = ProductRepoFile('test_products.txt')
    shop_repo = ShopRepoFile('test_shops.txt')

    test_srv = SaleItemService(sale_repo, sale_val, product_repo, shop_repo)

    test_srv.add_sale_item('1', '1', 1.86, 200)
    test_srv.add_sale_item('1', '2', 3.95, 200)
    test_srv.add_sale_item('1', '3', 4.22, 200)
    test_srv.add_sale_item('1', '4', 2.4, 200)

    dto_list = test_srv.get_top_locations_for_product('1')
    assert (len(dto_list) == 3)
    # dto_list is sorted based on price
    for el in dto_list:
        assert (el.getNumeProdus() == 'Haribo Gummy Bears')
    assert (dto_list[0].getPret() == 1.86)
    assert (dto_list[0].getNumeMagazin() == 'Bon Bon Candy')

    assert (dto_list[1].getPret() == 2.4)
    assert (dto_list[1].getNumeMagazin() == 'Sweets Store')

    assert (dto_list[2].getPret() == 3.95)
    assert (dto_list[2].getNumeMagazin() == 'CandyToys')

    try:
        dto_list = test_srv.get_top_locations_for_product('2436')
        assert False
    except ProductNotFoundException:
        assert True


def test_get_number_of_available_items():
    sale_repo = SaleItemRepoMemory()
    sale_val = SaleItemValidator()
    product_repo = ProductRepoFile('test_products.txt')
    shop_repo = ShopRepoFile('test_shops.txt')

    test_srv = SaleItemService(sale_repo, sale_val, product_repo, shop_repo)

    test_srv.add_sale_item('1', '1', 1.86, 200)
    test_srv.add_sale_item('2', '1', 3.95, 200)
    test_srv.add_sale_item('3', '1', 4.22, 200)
    test_srv.add_sale_item('4', '1', 2.4, 200)

    test_srv.add_sale_item('1', '2', 1.86, 200)
    test_srv.add_sale_item('2', '2', 3.95, 200)

    test_srv.add_sale_item('1', '3', 1.86, 200)


    dto_list = test_srv.get_number_of_available_items()
    # we can't guarantee the order as it is
    # so we sort it

    dto_list = sorted(dto_list, key=lambda x: x.getNumarProduse(), reverse=True)
    assert (len(dto_list) == 3)
    assert (dto_list[0].getNumeMagazin() == 'Bon Bon Candy')
    assert (dto_list[0].getNumarProduse() == 4)

    assert (dto_list[1].getNumeMagazin() == 'CandyToys')
    assert (dto_list[1].getNumarProduse() == 2)

    assert (dto_list[2].getNumeMagazin() == 'Candyland')
    assert (dto_list[2].getNumarProduse() == 1)
