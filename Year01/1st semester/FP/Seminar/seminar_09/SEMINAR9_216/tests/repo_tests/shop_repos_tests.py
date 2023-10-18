import unittest

from domain.entities import Shop
from exceptions.exceptions import DuplicateIDException, ShopNotFoundException
from repository.shop_repo import ShopInMemoryRepo, ShopRepoFile, ShopRepoFileInheritance


class TestCaseShopRepoInMemory(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = ShopInMemoryRepo()

    def test_store(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')

        self.__repo.store(shop1)
        self.assertEqual(self.__repo.size(), 1)
        self.__repo.store(shop2)
        self.assertEqual(self.__repo.size(), 2)
        shop1 = Shop('1', 'Candies ALL THE CANDIES', 'Magical land')

        self.assertRaises(DuplicateIDException, self.__repo.store, shop1)

    def test_delete_by_id(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')
        self.__repo.store(shop1)
        self.__repo.store(shop2)

        deleted_p = self.__repo.delete('1')
        self.assertEqual(deleted_p.getName(), 'Bon Bon Candy')
        self.assertEqual(deleted_p.getLocation(), 'Piata Muzeului')
        self.assertRaises(ShopNotFoundException, self.__repo.delete, 'wrongid')

    def test_update(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')
        self.__repo.store(shop1)

        updated_shop = self.__repo.update('1', shop2)
        self.assertEqual(updated_shop.getName(), 'Sweets Shop')
        self.assertEqual(updated_shop.getLocation(), 'Piata Unirii')

        self.assertRaises(ShopNotFoundException, self.__repo.update, '77', shop1)

    def test_get_all(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        self.__repo.store(shop1)

        crt_shops = self.__repo.get_all()
        self.assertIsInstance(crt_shops, list)
        self.assertEqual(len(crt_shops), 1)
        self.assertEqual(crt_shops[0], shop1)

    def test_repo_size(self):

        self.__repo.store(Shop('1', 'Bon Bon Candy', 'Piata Muzeului'))
        self.assertEqual(self.__repo.size(), 1)

    def test_find(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')
        self.__repo.store(shop1)

        p = self.__repo.find('1')
        self.assertEqual(p.getName(), 'Bon Bon Candy')
        self.assertEqual(p.getLocation(), 'Piata Muzeului')

        shop2 = self.__repo.find('J1203')
        self.assertIs(shop2, None)


class TestCaseShopRepoFile(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = ShopRepoFileInheritance('test_shops_repo.txt')
        self.__repo.delete_all()

    def test_store(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')

        self.__repo.store(shop1)
        self.assertEqual(self.__repo.size(), 1)
        self.__repo.store(shop2)
        self.assertEqual(self.__repo.size(), 2)
        shop1 = Shop('1', 'Candies ALL THE CANDIES', 'Magical land')

        self.assertRaises(DuplicateIDException, self.__repo.store, shop1)

    def test_delete_by_id(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')
        self.__repo.store(shop1)
        self.__repo.store(shop2)

        deleted_p = self.__repo.delete('1')
        self.assertEqual(deleted_p.getName(), 'Bon Bon Candy')
        self.assertEqual(deleted_p.getLocation(), 'Piata Muzeului')
        self.assertRaises(ShopNotFoundException, self.__repo.delete, 'wrongid')

    def test_update(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')
        self.__repo.store(shop1)

        updated_shop = self.__repo.update('1', shop2)
        self.assertEqual(updated_shop.getName(), 'Sweets Shop')
        self.assertEqual(updated_shop.getLocation(), 'Piata Unirii')

        self.assertRaises(ShopNotFoundException, self.__repo.update, '77', shop1)

    def test_get_all(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        self.__repo.store(shop1)

        crt_shops = self.__repo.get_all()
        self.assertIsInstance(crt_shops, list)
        self.assertEqual(len(crt_shops), 1)
        self.assertEqual(crt_shops[0], shop1)

    def test_repo_size(self):

        self.__repo.store(Shop('1', 'Bon Bon Candy', 'Piata Muzeului'))
        self.assertEqual(self.__repo.size(), 1)

    def test_find(self):
        shop1 = Shop('1', 'Bon Bon Candy', 'Piata Muzeului')
        shop2 = Shop('2', 'Sweets Shop', 'Piata Unirii')
        self.__repo.store(shop1)

        p = self.__repo.find('1')
        self.assertEqual(p.getName(), 'Bon Bon Candy')
        self.assertEqual(p.getLocation(), 'Piata Muzeului')

        shop2 = self.__repo.find('J1203')
        self.assertIs(shop2, None)

    def tearDown(self) -> None:
        self.__repo.delete_all()
