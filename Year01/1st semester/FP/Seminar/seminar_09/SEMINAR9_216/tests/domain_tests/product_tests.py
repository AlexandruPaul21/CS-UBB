import unittest

from domain.entities import Product
from domain.validators import ProductValidator
from exceptions.exceptions import ValidationException


class TestCaseProduct(unittest.TestCase):
    def setUp(self) -> None:
        self.__validator = ProductValidator()


    def test_create_product(self):
        p = Product('1', 'jeleuri', 'Spania')
        self.assertEqual(p.getName(), 'jeleuri')
        self.assertEqual(p.getCountry(), 'Spania')

        p.setName('jeleuri Haribo')
        self.assertEqual(p.getName(), 'jeleuri Haribo')
        p.setCountry('Germania')
        self.assertEqual(p.getCountry(), 'Germania')

    def test_equal_products(self):
        p1 = Product('1', 'jeleuri', 'Germania')
        p2 = Product('1', 'jeleuri', 'Germania')
        self.assertEqual(p1, p2)

        p3 = Product('2', 'jeleuri', 'Spania')
        self.assertNotEqual(p1, p3)

    def test_validate_product(self):
        p = Product('1', 'jeleuri', 'Germania')
        self.__validator.validate(p)
        p1 = Product('2', '', 'Germania')
        self.assertRaises(ValidationException, self.__validator.validate,p1)

        p2 = Product('3', 'acadele', 'Portugalia')
        self.assertRaises(ValidationException, self.__validator.validate,p2)


