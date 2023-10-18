import unittest

from domain.entities import SaleItem


class TestCaseSaleItem(unittest.TestCase):
    def setUp(self) -> None:
        pass

    def test_create_sale_item(self):
        sale_item = SaleItem('P1', 'S1', 34.4, 20)
        self.assertEqual(sale_item.getProductId(), 'P1')
        self.assertEqual(sale_item.getShopId(), 'S1')
        self.assertEqual(sale_item.getPret(), 34.4)
        self.assertEqual(sale_item.getStoc(), 20)

    def test_equal_sale_item(self):
        product1_id = 'P1'
        shop1_id = 'S1'
        product2_id = 'P2'

        sale_item1 = SaleItem(product1_id, shop1_id, 34.4, 20)
        sale_item2 = SaleItem(product1_id, shop1_id, 35.5, 210)

        self.assertEqual(sale_item1, sale_item2)

        sale_item3 = SaleItem(product2_id, shop1_id, 10.3, 34)
        self.assertNotEqual(sale_item3,sale_item2)
