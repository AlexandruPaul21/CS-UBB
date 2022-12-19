import unittest
from domain.entities import Show
from domain.validators import ShowValidator
from exceptions.domain_exceptions import DomainException


class testsDomain(unittest.TestCase):
    def setUp(self) -> None:
        self.__valid=ShowValidator()
    def testCreate(self):
        s=Show("Lacul Lebedelor","Andrei Adrian","Comedie",3)
        self.assertEqual(s.getTitle(),"Lacul Lebedelor")
        self.assertEqual(s.getArtist(),"Andrei Adrian")
        self.assertEqual(s.getGen(),"Comedie")
        self.assertEqual(s.getTime(),3)

        s.setTime(4)
        s.setGen("Balet")
        s.setTitle("Lacul")
        s.setArtist("Andrei")
        self.assertEqual(s.getTitle(), "Lacul")
        self.assertEqual(s.getArtist(), "Andrei")
        self.assertEqual(s.getGen(), "Balet")
        self.assertEqual(s.getTime(), 4)

    def testValidate(self):
        s=Show("Lacul Lebedelor","Andrei Adrian","Comedie",3)
        self.__valid.validate(s)

        s = Show("", "Andrei Adrian", "Comedie", 3)
        self.assertRaises(DomainException,self.__valid.validate,s)
        s = Show("Lacul Lebedelor", "", "Comedie", 3)
        self.assertRaises(DomainException, self.__valid.validate, s)
        s = Show("Lacul Lebedelor", "Andrei Adrian", "Drama", 3)
        self.assertRaises(DomainException, self.__valid.validate, s)
        s = Show("Lacul Lebedelor", "Andrei Adrian", "", 3)
        self.assertRaises(DomainException, self.__valid.validate, s)
        s = Show("Lacul Lebedelor", "Andrei Adrian", "Comedie", -3)
        self.assertRaises(DomainException, self.__valid.validate, s)
