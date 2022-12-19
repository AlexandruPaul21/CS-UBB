import unittest

from domain.entities import SaleItem
from exceptions.exceptions import ProductAlreadyAssignedException
from repository.repo_sale_item import SaleItemRepoMemory, SaleItemRepoFile


class TestCaseSaleItemRepoInMemory(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = SaleItemRepoMemory()

    def test_store_sale_item(self):
        sale_item1 = SaleItem('1', '1', 2.5, 20)
        self.__repo.store(sale_item1)

        self.assertEqual(len(self.__repo.get_all()), 1)
        self.assertRaises(ProductAlreadyAssignedException, self.__repo.store, sale_item1)

    def test_find_item(self):
        sale_item1 = SaleItem('1', '1', 2.5, 20)
        self.__repo.store(sale_item1)
        sale_item2 = SaleItem('1', '2', 2.5, 20)

        self.assertEqual(self.__repo.find(sale_item1), sale_item1)
        self.assertIs(None, self.__repo.find(sale_item2))


class TestCaseSaleItemRepoFile(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = SaleItemRepoFile('test_sale_items_repo.txt')

    def test_store_sale_item(self):
        sale_item1 = SaleItem('1', '1', 2.5, 20)
        self.__repo.store(sale_item1)

        self.assertEqual(len(self.__repo.get_all()), 1)
        self.assertRaises(ProductAlreadyAssignedException, self.__repo.store, sale_item1)

    def test_find_item(self):
        sale_item1 = SaleItem('1', '1', 2.5, 20)
        self.__repo.store(sale_item1)
        sale_item2 = SaleItem('1', '2', 2.5, 20)

        self.assertEqual(self.__repo.find(sale_item1), sale_item1)
        self.assertIs(None, self.__repo.find(sale_item2))

    def tearDown(self) -> None:
        self.__repo.delete_all()
