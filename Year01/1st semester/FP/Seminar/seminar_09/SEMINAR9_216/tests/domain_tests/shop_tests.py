import unittest

from domain.entities import Shop


class TestCaseShop(unittest.TestCase):
    def setUp(self) -> None:
        pass

    def test_create_shop(self):
        shop = Shop('1', 'Bon Bon Shop', 'Piata Muzeului')
        self.assertEqual(shop.getLocation(), 'Piata Muzeului')
        self.assertEqual(shop.getName(), 'Bon Bon Shop')

        shop.setLocatie('Piata Muzeului, Cluj-Napoca')
        self.assertEqual(shop.getLocation(), 'Piata Muzeului, Cluj-Napoca')

        shop.setNume('BonBonShop')
        self.assertEqual(shop.getName(), 'BonBonShop')

    def test_equal_shop(self):
        shop1 = Shop('1', 'Bon Bon Shop', 'Piata Muzeului')
        shop2 = Shop('1', 'Candy Store', 'Piata Unirii')

        self.assertEqual(shop1, shop2)

        shop3 = Shop('3', 'Christmas Candy', 'North Pole')
        self.assertNotEqual(shop3, shop2)
