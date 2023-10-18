import unittest

from domain.validators import ProductValidator
from exceptions.exceptions import ValidationException, ProductNotFoundException
from repository.product_repo import ProductInMemoryRepo
from service.product_service import ProductService


class TestCaseProductService(unittest.TestCase):
    def setUp(self) -> None:
        repo = ProductInMemoryRepo()
        val = ProductValidator()

        self.__product_srv = ProductService(repo, val)

    def test_add_product(self):

        product = self.__product_srv.add_product('1', 'jeleuri', 'Germania')
        self.assertEqual(product.getName(), 'jeleuri')
        self.assertEqual(product.getCountry(), 'Germania')

        self.assertEqual(len(self.__product_srv.get_all_products()), 1)
        self.assertRaises(ValidationException, self.__product_srv.add_product, '2', '', 'Austria')

    def test_get_all_products(self):

        added_product = self.__product_srv.add_product('JELLIES1', 'jeleuri', 'Germania')
        self.assertIsInstance(self.__product_srv.get_all_products(), list)
        self.assertEqual(len(self.__product_srv.get_all_products()), 1)

        crt_products = self.__product_srv.get_all_products()
        self.assertEqual(crt_products[0].getName(), 'jeleuri')

        added_product2 = self.__product_srv.add_product('JELLIES3', 'jeleuri acrisoare', 'SUA')
        self.assertEqual(len(self.__product_srv.get_all_products()), 2)

        # this shouldn't add anything (duplicate id)
        try:
            self.__product_srv.add_product('JELLIES3', 'marshmallows', 'Belgia')
        except:
            pass
        self.assertEqual(len(self.__product_srv.get_all_products()), 2)

        self.__product_srv.delete_by_id('JELLIES1')
        crt_products = self.__product_srv.get_all_products()
        self.assertEqual(len(self.__product_srv.get_all_products()), 1)
        self.assertEqual(crt_products[0].getName(), 'jeleuri acrisoare')

        self.__product_srv.update_product('JELLIES3', 'Sour Patch', 'SUA')
        crt_products = self.__product_srv.get_all_products()
        self.assertEqual(len(self.__product_srv.get_all_products()), 1)
        self.assertEqual(crt_products[0].getName(), 'Sour Patch')

    def test_delete_product(self):

        self.__product_srv.add_product('JELLIES1', 'jeleuri', 'Germania')
        self.__product_srv.add_product('JELLIES3', 'jeleuri acrisoare', 'SUA')
        self.__product_srv.add_product('MISC0011', 'marshmallows', 'Belgia')

        deleted_product1 = self.__product_srv.delete_by_id('JELLIES1')

        self.assertEqual(len(self.__product_srv.get_all_products()), 2)

        self.assertEqual(deleted_product1.getName(), 'jeleuri')
        self.assertEqual(deleted_product1.getCountry(), 'Germania')

        deleted_product2 = self.__product_srv.delete_by_id('JELLIES3')
        crt_products = self.__product_srv.get_all_products()
        self.assertEqual(len(crt_products), 1)
        self.assertEqual(deleted_product2.getName(), 'jeleuri acrisoare')
        self.assertEqual(deleted_product2.getCountry(), 'SUA')

        self.assertRaises(ProductNotFoundException, self.__product_srv.delete_by_id, 'WRONG ID')

    def test_update_product(self):

        self.__product_srv.add_product('JELLIES1', 'jeleuri', 'Germania')
        self.__product_srv.add_product('JELLIES3', 'jeleuri acrisoare', 'SUA')
        self.__product_srv.add_product('MISC0011', 'marshmallows', 'Belgia')

        updated_product = self.__product_srv.update_product('JELLIES3', 'Sour Patch', 'SUA')

        self.assertEqual(updated_product.getName(), 'Sour Patch')
        self.assertEqual(updated_product.getCountry(), 'SUA')
        self.assertRaises(ProductNotFoundException, self.__product_srv.update_product, 'INVALID ID', 'jeleuri Haribo',
                          'Germania')
