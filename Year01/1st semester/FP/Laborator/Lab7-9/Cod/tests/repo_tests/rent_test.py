import unittest

from repository.rent_repo import RentInMemoryRepo

class TestRentRepo(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo_rent=RentInMemoryRepo()

    def test_add_rent(self):
        self.__repo_rent.add_rent(1,1)
        self.assertEqual(self.__repo_rent.get_all(),[(1,1)])

    def test_delete_rent(self):
        self.__repo_rent.add_rent(1,1)
        self.assertEqual(self.__repo_rent.get_all(),[(1,1)])

        self.__repo_rent.delete_rent(1,1)
        self.assertEqual(self.__repo_rent.get_all(),[])

    def test_get_list(self):
        self.__repo_rent.add_rent(1,2)
        self.__repo_rent.add_rent(1,3)
        self.__repo_rent.add_rent(2,4)
        self.__repo_rent.add_rent(5,3)

        lst=self.__repo_rent.get_list()

        self.assertEqual(lst,[0,2,1,0,0,1]+[0]*94)

    def test_get_all_id(self):
        self.__repo_rent.add_rent(1,1)
        self.__repo_rent.add_rent(1,2)
        self.assertEqual(self.__repo_rent.get_all_id(1),[1,2])

    def test_last(self):
        self.__repo_rent.add_rent(1,2)
        self.__repo_rent.add_rent(1,3)
        self.__repo_rent.add_rent(2,4)
        self.__repo_rent.add_rent(5,3)

        lst=self.__repo_rent.get_all_for_max()

        self.assertEqual(lst,[1,5])