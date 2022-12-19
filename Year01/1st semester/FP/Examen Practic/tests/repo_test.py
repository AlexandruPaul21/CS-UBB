import unittest
from repository.show_repo import ShowRepo
from domain.entities import Show
from exceptions.domain_exceptions import NotFoundException

class testRepo(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo=ShowRepo("test.txt")

    def testLoad(self):
        self.assertEqual(len(self.__repo.get_all()),5)

    def testAdd(self):
        self.__repo.add_show(Show("Lacul Lacurilor","Andrei Andreinovici","Comedie",500))
        self.assertEqual(len(self.__repo.get_all()),5)

    def testModify(self):
        self.__repo.modify_show(Show("Casa din deal","Laura Harasniuc","Balet",1000))
        shows=self.__repo.get_all()
        s=shows[2]
        self.assertEqual(s.getTitle(),"Casa din deal")
        self.assertEqual(s.getArtist(),"Laura Harasniuc")
        self.assertEqual(s.getGen(),"Balet")
        self.assertEqual(s.getTime(),1000)
        self.assertRaises(NotFoundException,self.__repo.modify_show,Show("Casa din","Laura Harasniuc","Balet",1000))

    def testExport(self):
        self.__repo.export("ana.txt")