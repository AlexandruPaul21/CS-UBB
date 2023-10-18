import unittest
from repository.show_repo import ShowRepo
from service.show_service import ShowService
from domain.entities import Show
from domain.validators import ShowValidator
from exceptions.domain_exceptions import DomainException,NotFoundException


class testService(unittest.TestCase):
    def setUp(self) -> None:
        self.__srv=ShowService(ShowRepo("test.txt"),ShowValidator())

    def testAdd(self):
        self.__srv.add_new_show("Lacul Lacurilor", "Andrei Andreinovici", "Comedie", 500)
        self.assertEqual(len(self.__srv.get_all_service()),5)

        self.assertRaises(DomainException,self.__srv.add_new_show,"","Andrei Adrian","Comedie",3)
        self.assertRaises(DomainException,self.__srv.add_new_show,"Lacul Lebedelor","","Comedie",3)
        self.assertRaises(DomainException,self.__srv.add_new_show,"Lacul Lebedelor","Andrei Adrian","Drama",3)
        self.assertRaises(DomainException,self.__srv.add_new_show,"Lacul Lebedelor","Andrei Adrian","",3)
        self.assertRaises(DomainException,self.__srv.add_new_show,"Lacul Lebedelor","Andrei Adrian","Comedie",-6)

    def testModify(self):
        self.__srv.modify_show_service("Casa din deal","Laura Harasniuc","Balet",1000)
        shows=self.__srv.get_all_service()
        s=shows[2]
        self.assertEqual(s.getTitle(),"Casa din deal")
        self.assertEqual(s.getArtist(),"Laura Harasniuc")
        self.assertEqual(s.getGen(),"Balet")
        self.assertEqual(s.getTime(),1000)
        self.assertRaises(NotFoundException,self.__srv.modify_show_service,"Casa","Laura Harasniuc","Balet",1000)

    def testExport(self):
        self.__srv.export_srv("ana.txt")

    def testRandom(self):
        self.__srv.rand(5)
        self.assertEqual(len(self.__srv.get_all_service()),10)