import unittest

from domain.entities import Product
from exceptions.exceptions import DuplicateIDException, ProductNotFoundException
from repository.product_repo import ProductInMemoryRepo, ProductRepoFile, ProductRepoFileInheritance


class TestCaseProductRepoInMemory(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = ProductInMemoryRepo()

    def test_store(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('2', 'jeleuri', 'Spania')
        self.__repo.store(p1)
        self.assertEqual(self.__repo.size(), 1)
        self.__repo.store(p2)
        self.assertEqual(self.__repo.size(), 2)
        p1 = Product('1', 'jeleuri', 'Germania')

        self.assertRaises(DuplicateIDException, self.__repo.store, p1)

    def test_delete_by_id(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('2', 'dropsuri', 'Romania')
        self.__repo.store(p1)
        self.__repo.store(p2)

        deleted_p = self.__repo.delete('1')
        self.assertEqual(deleted_p.getName(), 'jeleuri')
        self.assertEqual(deleted_p.getCountry(), 'Germania')
        self.assertRaises(ProductNotFoundException, self.__repo.delete, 'wrongid')

    def test_update(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('1', 'acadele', 'Turcia')
        self.__repo.store(p1)

        updated_product = self.__repo.update('1', p2)
        self.assertEqual(updated_product.getName(), 'acadele')
        self.assertEqual(updated_product.getCountry(), 'Turcia')

        self.assertRaises(ProductNotFoundException, self.__repo.update, '77', p1)

    def test_get_all(self):
        p1 = Product('1', 'ciocolata martipan', 'Austria')
        self.__repo.store(p1)

        crt_products = self.__repo.get_all()
        self.assertIsInstance(crt_products, list)
        self.assertEqual(len(crt_products), 1)
        self.assertEqual(crt_products[0], p1)

    def test_repo_size(self):
        self.__repo.store(Product('1', 'Snickers, Bounty & Mars Mix', 'SUA'))
        self.assertEqual(self.__repo.size(), 1)

    def test_find(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('2', 'acadele', 'Turcia')
        p3 = Product('3', 'ciocolata martipan', 'Austria')
        self.__repo.store(p1)

        p = self.__repo.find('1')
        self.assertEqual(p.getName(), 'jeleuri')
        self.assertEqual(p.getCountry(), 'Germania')

        p2 = self.__repo.find('J1203')
        self.assertIs(p2, None)


class TestCaseProductRepoFile(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = ProductRepoFileInheritance('test_products_repo.txt')

    def test_store(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('2', 'jeleuri', 'Spania')
        self.__repo.store(p1)
        self.assertEqual(self.__repo.size(), 1)
        self.__repo.store(p2)
        self.assertEqual(self.__repo.size(), 2)
        p1 = Product('1', 'jeleuri', 'Germania')

        self.assertRaises(DuplicateIDException, self.__repo.store, p1)

    def test_delete_by_id(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('2', 'dropsuri', 'Romania')
        self.__repo.store(p1)
        self.__repo.store(p2)

        deleted_p = self.__repo.delete('1')
        self.assertEqual(deleted_p.getName(), 'jeleuri')
        self.assertEqual(deleted_p.getCountry(), 'Germania')
        self.assertRaises(ProductNotFoundException, self.__repo.delete, 'wrongid')

    def test_update(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('1', 'acadele', 'Turcia')
        self.__repo.store(p1)

        updated_product = self.__repo.update('1', p2)
        self.assertEqual(updated_product.getName(), 'acadele')
        self.assertEqual(updated_product.getCountry(), 'Turcia')

        self.assertRaises(ProductNotFoundException, self.__repo.update, '77', p1)

    def test_get_all(self):
        p1 = Product('1', 'ciocolata martipan', 'Austria')
        self.__repo.store(p1)

        crt_products = self.__repo.get_all()
        self.assertIsInstance(crt_products, list)
        self.assertEqual(len(crt_products), 1)
        self.assertEqual(crt_products[0], p1)

    def test_repo_size(self):
        self.__repo.store(Product('1', 'Snickers, Bounty & Mars Mix', 'SUA'))
        self.assertEqual(self.__repo.size(), 1)

    def test_find(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('2', 'acadele', 'Turcia')
        p3 = Product('3', 'ciocolata martipan', 'Austria')
        self.__repo.store(p1)

        p = self.__repo.find('1')
        self.assertEqual(p.getName(), 'jeleuri')
        self.assertEqual(p.getCountry(), 'Germania')

        p2 = self.__repo.find('J1203')
        self.assertIs(p2, None)

    def tearDown(self) -> None:
        self.__repo.delete_all()
