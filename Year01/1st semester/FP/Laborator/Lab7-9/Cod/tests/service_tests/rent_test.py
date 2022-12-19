import unittest

from repository.rent_repo import RentInMemoryRepo
from service.rent_service import RentService

class RentServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__rent_repo=RentInMemoryRepo()
        self.__rent_srv=RentService(self.__rent_repo)

    def test_add_rent(self):
        self.__rent_srv.add_rent_op(1,1)

    def test_remove_rent(self):
        self.__rent_srv.add_rent_op(1,1)
        self.__rent_srv.remove_rent_op(1,1)

    def test_get_list(self):
        lst=self.__rent_srv.get_list_with_ids()

    def test_get_all_for_id(self):
        self.__rent_srv.add_rent_op(1,1)
        self.__rent_srv.get_all_for_id(1)