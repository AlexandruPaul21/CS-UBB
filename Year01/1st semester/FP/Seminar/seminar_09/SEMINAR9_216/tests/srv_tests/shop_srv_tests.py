import unittest

from domain.validators import ShopValidator
from exceptions.exceptions import ValidationException, ShopNotFoundException
from repository.shop_repo import ShopInMemoryRepo
from service.shop_service import ShopService


class TestCaseShopService(unittest.TestCase):
    def setUp(self) -> None:
        repo = ShopInMemoryRepo()
        val = ShopValidator()

        self.__shop_srv = ShopService(repo, val)

    def test_add_shop(self):

        shop = self.__shop_srv.add_shop('1', 'Bon Bon Shop', 'Piata Muzeului')
        self.assertEqual(shop.getName(), 'Bon Bon Shop')
        self.assertEqual(shop.getLocation(), 'Piata Muzeului')

        self.assertEqual(len(self.__shop_srv.get_all_shops()), 1)
        self.assertRaises(ValidationException, self.__shop_srv.add_shop, '2', '', 'London')

    def test_get_all_shops(self):

        shop = self.__shop_srv.add_shop('1', 'Bon Bon Shop', 'Piata Muzeului')
        self.assertIsInstance(self.__shop_srv.get_all_shops(), list)
        self.assertEqual(len(self.__shop_srv.get_all_shops()), 1)

        crt_shops = self.__shop_srv.get_all_shops()
        self.assertEqual(crt_shops[0].getName(), 'Bon Bon Shop')

        added_shop2 = self.__shop_srv.add_shop('2', 'Candy Store', 'Strada Frunzisului')
        self.assertEqual(len(self.__shop_srv.get_all_shops()), 2)

        try:
            self.__shop_srv.add_shop('2', 'Candy Store', 'Strada Frunzisului')
        except:
            pass

        self.assertEqual(len(self.__shop_srv.get_all_shops()), 2)

        self.__shop_srv.delete_by_id('2')
        crt_shops = self.__shop_srv.get_all_shops()
        self.assertEqual(len(self.__shop_srv.get_all_shops()), 1)
        self.assertEqual(crt_shops[0].getName(), 'Bon Bon Shop')

        self.__shop_srv.update_shop('1', 'BonBonShop', 'Piata Muzeului')
        crt_shops = self.__shop_srv.get_all_shops()
        self.assertEqual(len(self.__shop_srv.get_all_shops()), 1)
        self.assertEqual(crt_shops[0].getName(), 'BonBonShop')

    def test_delete_shop(self):

        shop1 = self.__shop_srv.add_shop('1', 'Bon Bon Shop', 'Piata Muzeului')
        shop2 = self.__shop_srv.add_shop('2', 'Magazin de dulciuri', 'Cluj')
        shop3 = self.__shop_srv.add_shop('3', 'Sweets Store', 'New York')

        deleted_shop1 = self.__shop_srv.delete_by_id('3')

        self.assertEqual(len(self.__shop_srv.get_all_shops()), 2)

        self.assertEqual(deleted_shop1.getName(), 'Sweets Store')
        self.assertEqual(deleted_shop1.getLocation(), 'New York')

        deleted_shop2 = self.__shop_srv.delete_by_id('2')
        crt_shops = self.__shop_srv.get_all_shops()
        self.assertEqual(len(crt_shops), 1)
        self.assertEqual(deleted_shop2.getName(), 'Magazin de dulciuri')
        self.assertEqual(deleted_shop2.getLocation(),'Cluj')

        self.assertRaises(ShopNotFoundException, self.__shop_srv.delete_by_id, 'WRONG ID')

    def test_update_shop(self):

        shop1 = self.__shop_srv.add_shop('1', 'Bon Bon Shop', 'Piata Muzeului')
        shop2 = self.__shop_srv.add_shop('2', 'Magazin de dulciuri', 'Cluj')
        shop3 = self.__shop_srv.add_shop('3', 'Sweets Store', 'New York')

        updated_shop = self.__shop_srv.update_shop('3', 'All sweets', 'Santa\'s Village')

        self.assertEqual(updated_shop.getName(), 'All sweets')
        self.assertEqual(updated_shop.getLocation(), 'Santa\'s Village')
        self.assertRaises(ShopNotFoundException, self.__shop_srv.update_shop, 'INVALID ID', 'Sweets',
                          'Paris')
