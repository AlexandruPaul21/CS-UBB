import unittest

from domain.entities import client
from repository.clients_repo import ClientsInMemoryRepo,ClientsFileRepo

class TestClientRepo(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo=ClientsInMemoryRepo()
        self.__repo1=ClientsInMemoryRepo()
        self.__repo2=ClientsInMemoryRepo()
        self.__repo3=ClientsInMemoryRepo()
        self.__new_repo=ClientsInMemoryRepo()

    def test_eq(self):
        customer=client(1,"Georgescu Valentin","5020421314002")
        customer1=client(1,"Georgescu Valentin","5120421314002")

        self.__repo1.add_client(customer)
        self.__repo2.add_client(customer)
        self.assertEqual(self.__repo1,self.__repo2)
        self.__repo3.add_client(customer1)
        self.assertNotEqual(self.__repo1,self.__repo3)
        self.assertRaises(ValueError,self.__repo3.searchID,5)

    def test_add_client(self):
        customer=client(1,"Georgescu Valentin","5020421314002")

        self.__repo.add_client(customer)
        self.assertEqual(len(self.__repo.get_all_clients()),1)

    def test_delete_client(self):
        customer=client(1,"Georgescu Valentin","5020421314002")

        self.__repo.add_client(customer)
        self.__repo.delete_client(1)
        self.assertEqual(len(self.__repo.get_all_clients()),0)

    def test_modify_client(self):
        customer=client(1,"Georgescu Valentin","5020421314002")

        self.__repo.add_client(customer)
        self.__repo.modify_client(1,"Nicolae Guta","5020421314002")

        customer2=client(1,"Nicolae Guta","5020421314002")

        self.__new_repo.add_client(customer2)
        self.assertEqual(self.__repo,self.__new_repo)

    def test_get_all_clients(self):
        customer=client(1,"Georgescu Valentin","5020421314002")

        self.__repo.add_client(customer)
        self.assertEqual(self.__repo.get_all_clients(),[customer])

    def test_search_client_by_id(self):
        customer1=client(1,"Georgescu Valentin","5020421314002")
        customer2=client(2,"Nicolae Guta","5020421314002")

        self.__repo.add_client(customer1)
        self.__repo.add_client(customer2)

        self.assertEqual(self.__repo.search_client_by_id(1),customer1)
        self.assertEqual(self.__repo.search_client_by_id(2),customer2)
        self.assertRaises(ValueError,self.__repo.search_client_by_id,3)

    def test_search_client_by_param(self):
        customer1=client(1,"Georgescu Valentin","5020421314002")
        customer2=client(2,"Nicolae Guta","5020421314002")

        self.__repo.add_client(customer1)
        self.__repo.add_client(customer2)

        self.assertEqual(self.__repo.search_client_by_param("Nicolae Guta",""),customer2)
        self.assertEqual(self.__repo.search_client_by_param("","5020421314002"),customer1)
        self.assertRaises(ValueError,self.__repo.search_client_by_param,"Nicola","")

class RepoFileTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo=ClientsFileRepo("test_clients.txt")

    def test_create_file_Repo(self):
        init_len=len(self.__repo.get_all_clients())
        customer=client(5,"Alex Darabant","5023465316653156")
        self.__repo.add_client(customer)

        self.assertEqual(len(self.__repo.get_all_clients()),init_len+1)

    def test_modify_fileRepo(self):
        init_len=len(self.__repo.get_all_clients())
        customer=client(1,"Georgescu Valentin","5020421314002")

        self.__repo.add_client(customer)
        self.__repo.modify_client(1,"Nicolae Guta","5020421314002")

        self.assertEqual(len(self.__repo.get_all_clients()),init_len+1)