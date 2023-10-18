import unittest

from domain.entities import client
from domain.validators import clientValidator
from repository.clients_repo import ClientsInMemoryRepo
from service.client_service import ClientService

class ClientServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        self.__test_repo=ClientsInMemoryRepo()
        self.__test_valid=clientValidator()
        self.__test_srv=ClientService(self.__test_repo,self.__test_valid)

    def test_insert_client(self):
        customer=self.__test_srv.insert_client(1,"Alex Sirbu","5020421314002")
        self.assertEqual(customer.getId(),1)
        self.assertEqual(customer.getName(),"Alex Sirbu")
        self.assertEqual(customer.getCNP(),"5020421314002")

        self.assertRaises(ValueError,self.__test_srv.insert_client,1,"Alex Sirbu","50020421314003")

    def test_erase_client(self):
        customer1=self.__test_srv.insert_client(1,"Alex Sirbu","5020421314002")
        customer2=self.__test_srv.insert_client(2,"Alex Darabant","5020726314007")

        self.__test_srv.erase_client(2)
        self.assertEqual(len(self.__test_srv.get_all()),1)

    def test_modify_customer(self):
        customer1=self.__test_srv.insert_client(1,"Alex Sirb","5020421314002")
        customer2=self.__test_srv.insert_client(2,"Alex Darabant","5020726314007")

        mod_customer=self.__test_srv.modify_customer(1,"Alex Sirbu","5020421314002")
        self.assertEqual(mod_customer.getId(),1)
        self.assertEqual(mod_customer.getName(),"Alex Sirbu")
        self.assertEqual(mod_customer.getCNP(),"5020421314002")

        self.assertRaises(ValueError,self.__test_srv.insert_client,2,"","5020726314007")

    def test_search_by_id(self):
        customer1=self.__test_srv.insert_client(1,"Alex Sirb","5020421314002")
        customer2=self.__test_srv.insert_client(2,"Alex Darabant","5020726314007")

        self.assertEqual(self.__test_srv.search_by_id(1),customer1)
        self.assertEqual(self.__test_srv.search_by_id(2),customer2)

        self.assertRaises(ValueError,self.__test_srv.search_by_id,3)

    def test_search_by_param(self):
        customer1=self.__test_srv.insert_client(1,"Alex Sirb","5020421314002")
        customer2=self.__test_srv.insert_client(2,"Alex Darabant","5020726314007")

        self.assertEqual(self.__test_srv.search_by_param("Alex Sirb",""),customer1)
        self.assertEqual(self.__test_srv.search_by_param("","5020726314007"),customer2)

        self.assertRaises(ValueError,self.__test_srv.search_by_param,"","")
        self.assertRaises(ValueError,self.__test_srv.search_by_param,"Lexutz","")

    def test_get_all(self):
        customer1=self.__test_srv.insert_client(1,"Alex Sirb","5020421314002")
        customer2=self.__test_srv.insert_client(2,"Alex Darabant","5020726314007")

        self.assertEqual(len(self.__test_srv.get_all()),2)

    def test_add_random(self):
        self.__test_srv.add_random_clients(5)

        self.assertEqual(len(self.__test_srv.get_all()),5)