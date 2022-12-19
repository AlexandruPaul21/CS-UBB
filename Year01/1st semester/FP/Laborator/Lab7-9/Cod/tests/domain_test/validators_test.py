import unittest

from domain.entities import movie,client
from domain.validators import clientValidator,movieValidator

class MValidators(unittest.TestCase):
    def setUp(self) -> None:
        self.__valid=movieValidator()

    def test_movie(self):
        film=movie(1,"Doamna","Un film fain","romantic")
        self.__valid.validate(film)

        film1=movie(1,"","Un film fain","romantic")
        self.assertRaises(ValueError,self.__valid.validate,film1)

        film2=movie(1,"Doamna","","romantic")
        self.assertRaises(ValueError,self.__valid.validate,film2)

        film3=movie(1,"Doamna","Un film fain","")
        self.assertRaises(ValueError,self.__valid.validate,film3)

class CValidators(unittest.TestCase):
    def setUp(self) -> None:
        self.__valid=clientValidator()

    def test_client(self):
        customer=client(1,"Georgescu Valentin","5020421314002")
        self.__valid.validate(customer)

        customer1=client(1,"","5020421314002")
        self.assertRaises(ValueError,self.__valid.validate,customer1)

        customer3=client(1,"Georgescu Valentin","")
        self.assertRaises(ValueError,self.__valid.validate,customer3)
