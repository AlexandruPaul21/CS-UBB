import unittest

from domain.validators import SaleItemValidator
from exceptions.exceptions import ProductAlreadyAssignedException, ProductNotFoundException
from repository.product_repo import ProductRepoFile
from repository.repo_sale_item import SaleItemRepoFile
from repository.shop_repo import ShopRepoFile
from service.sale_item_service import SaleItemService


class TestCaseSaleItemService(unittest.TestCase):
    def setUp(self) -> None:
        sale_repo = SaleItemRepoFile('test_sale_items_srv.txt')
        sale_repo.delete_all()
        sale_val = SaleItemValidator()

        product_repo = ProductRepoFile('test_products_srv.txt')
        shop_repo = ShopRepoFile('test_shops_srv.txt')

        self.__sale_item_service = SaleItemService(sale_repo, sale_val, product_repo, shop_repo)

    def test_add_sale_item(self):
        self.__sale_item_service.add_sale_item('1', '1', 24, 200)
        self.assertEqual(len(self.__sale_item_service.get_all()), 1)
        self.assertRaises(ProductAlreadyAssignedException, self.__sale_item_service.add_sale_item, '1', '1', 5.5, 40)
        self.assertRaises(ProductNotFoundException, self.__sale_item_service.add_sale_item, '100', '1', 5.5, 40, )

        # also test for ValidationException, ShopNotFoundException

    def test_get_number_of_available_items(self):
        pass
        # method not implemented in this version

        # self.__sale_item_service.add_sale_item('1', '1', 1.86, 200)
        # self.__sale_item_service.add_sale_item('2', '1', 3.95, 200)
        # self.__sale_item_service.add_sale_item('3', '1', 4.22, 200)
        # self.__sale_item_service.add_sale_item('4', '1', 2.4, 200)
        #
        # self.__sale_item_service.add_sale_item('1', '2', 1.86, 200)
        # self.__sale_item_service.add_sale_item('2', '2', 3.95, 200)
        #
        # self.__sale_item_service.add_sale_item('1', '3', 1.86, 200)
        #
        # dto_list = self.__sale_item_service.get_number_of_available_items()
        # # we can't guarantee the order as it is
        # # so we sort it
        # print(dto_list)
        # dto_list = sorted(dto_list, key=lambda x: x.getNumarProduse(), reverse=True)
        # self.assertEqual(len(dto_list) , 3)
        # self.assertEqual(dto_list[0].getNumeMagazin() , 'Bon Bon Candy')
        # self.assertEqual(dto_list[0].getNumarProduse() , 4)
        #
        # self.assertEqual(dto_list[1].getNumeMagazin() , 'CandyToys')
        # self.assertEqual(dto_list[1].getNumarProduse() , 2)
        #
        # self.assertEqual(dto_list[2].getNumeMagazin() , 'Candyland')
        # self.assertEqual(dto_list[2].getNumarProduse() , 1)


    def test_get_top_locations(self):
        self.__sale_item_service.add_sale_item('1', '1', 1.86, 200)
        self.__sale_item_service.add_sale_item('1', '2', 3.95, 200)
        self.__sale_item_service.add_sale_item('1', '3', 4.22, 200)
        self.__sale_item_service.add_sale_item('1', '4', 2.4, 200)

        dto_list = self.__sale_item_service.get_top_locations_for_product('1')
        self.assertEqual(len(dto_list), 3)

        # dto_list is sorted based on price
        for el in dto_list:
            self.assertEqual(el.getNumeProdus(), 'Haribo Gummy Bears')
        self.assertEqual(dto_list[0].getPret(), 1.86)
        self.assertEqual(dto_list[0].getNumeMagazin(), 'Bon Bon Candy')

        self.assertEqual(dto_list[1].getPret(), 2.4)
        self.assertEqual(dto_list[1].getNumeMagazin(), 'Sweets Store')

        self.assertEqual(dto_list[2].getPret(), 3.95)
        self.assertEqual(dto_list[2].getNumeMagazin(), 'CandyToys')
        self.assertRaises(ProductNotFoundException, self.__sale_item_service.get_top_locations_for_product, '2436')

    def tearDown(self) -> None:
        # delete contents of the file to have blank slate in each
        # subsequent test
        with open('test_sale_items_srv.txt', 'w'):
            pass
