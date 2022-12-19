from domain.entities import client
from domain.validators import clientValidator
from repository.clients_repo import ClientsInMemoryRepo
from utils.utils import string_generator,number_string_generator
import random
import string

class ClientService():
    def __init__(self,repo,validator):
        """
        Atribuie service-ului o regula de validare si un repo
        Controller GRASP
        """
        self.__repo=repo
        self.__validator=validator

    def insert_client(self,id,name,cnp):
        """
        Insereraza un client nou in repo
        :param id: id-ul unic al clientului
        :param name: numele clientului
        :param cnp: cnp-ul clientului
        :return: filmul nou adaugat
        :raises: ValueErorr pentru date invalide
        """
        customer=client(id,name,cnp)
        self.__validator.validate(customer)
        self.__repo.add_client(customer)
        return customer

    def erase_client(self,id):
        """
        Sterge clientul cu id-ul id
        :param id: id-ul clientului de sters
        """
        self.__repo.delete_client(id)

    def modify_customer(self,id,name,cnp):
        """
        Modifica clientul
        :param id:id-ul clientului de modificat
        :param name:numele clientului de modificat
        :param cnp:cnp-ul clientului de modificat
        :return: clientul modificat
        :raises: ValueErorr pentru date invalide
        """
        customer=client(id,name,cnp)
        self.__validator.validate(customer)
        self.__repo.modify_client(id,name,cnp)
        return customer

    def search_by_id(self,id):
        """
        Cauta clientul in repo dupa id
        :param id: id-ul de cautat
        :return: clientul cautat
        :raises: ValueError daca id-ul cautat nu este valid
        """
        customer=self.__repo.search_client_by_id(id)
        return customer

    def search_by_param(self,name,CNP):
        """
        Cauta clientul dupa parametrii
        :param name: numele clientului
        :param CNP: CNP-ul clientului
        :return: clientul cautat
        :raises: ValueError daca clientul cautat nu este gasit
        """
        customer=self.__repo.search_client_by_param(name,CNP)
        return customer

    def add_random_clients(self,nr):
        """
        Adauga clienti random
        :param nr: nr de clienti adaugati
        """
        for index in range(nr):
            customer=client(1,string_generator(6),number_string_generator(13))
            self.__validator.validate(customer)
            self.__repo.add_client(customer)

    def search_id(self,id):
        """
        Verifica daca id-ul unui client este valid
        :param id: id-ul clientului
        :raises: ValueError daca clientul nu este gasit
        """
        self.__repo.searchID(id)

    def get_all(self):
        """
        Returneaza lista de clienti
        """
        return self.__repo.get_all_clients()

def test_insert_client():
    test_repo=ClientsInMemoryRepo()
    test_valid=clientValidator()

    test_srv=ClientService(test_repo,test_valid)

    customer=test_srv.insert_client(1,"Alex Sirbu","5020421314002")
    assert customer.getId()==1
    assert customer.getName()=="Alex Sirbu"
    assert customer.getCNP()=="5020421314002"

    try:
        customer=test_srv.insert_client(1,"Alex Sirbu","5020421314003")
        assert False
    except:
        assert True

def test_erase_client():
    test_repo=ClientsInMemoryRepo()
    test_valid=clientValidator()

    test_srv=ClientService(test_repo,test_valid)

    customer1=test_srv.insert_client(1,"Alex Sirbu","5020421314002")
    customer2=test_srv.insert_client(2,"Alex Darabant","5020726314007")

    test_srv.erase_client(2)
    assert len(test_srv.get_all())==1

def test_modify_customer():
    test_repo=ClientsInMemoryRepo()
    test_valid=clientValidator()

    test_srv=ClientService(test_repo,test_valid)

    customer1=test_srv.insert_client(1,"Alex Sirb","5020421314002")
    customer2=test_srv.insert_client(2,"Alex Darabant","5020726314007")

    mod_customer=test_srv.modify_customer(1,"Alex Sirbu","5020421314002")
    assert mod_customer.getId()==1
    assert mod_customer.getName()=="Alex Sirbu"
    assert mod_customer.getCNP()=="5020421314002"

    try:
        mod_customer=test_srv.insert_client(2,"","5020726314007")
        assert False
    except:
        assert True

def test_search_by_id():
    test_repo=ClientsInMemoryRepo()
    test_valid=clientValidator()

    test_srv=ClientService(test_repo,test_valid)

    customer1=test_srv.insert_client(1,"Alex Sirb","5020421314002")
    customer2=test_srv.insert_client(2,"Alex Darabant","5020726314007")

    assert test_srv.search_by_id(1)==customer1
    assert test_srv.search_by_id(2)==customer2

    try:
        test_srv.search_by_id(3)
        assert False
    except ValueError:
        assert True

def test_search_by_param():
    test_repo=ClientsInMemoryRepo()
    test_valid=clientValidator()

    test_srv=ClientService(test_repo,test_valid)

    customer1=test_srv.insert_client(1,"Alex Sirb","5020421314002")
    customer2=test_srv.insert_client(2,"Alex Darabant","5020726314007")

    assert test_srv.search_by_param("Alex Sirb","")==customer1
    assert test_srv.search_by_param("","5020726314007")==customer2

    try:
        test_srv.search_by_param("","")
        assert False
    except ValueError:
        assert True

    try:
        test_srv.search_by_param("Lexutz","")
        assert False
    except ValueError:
        assert True

def test_get_all():
    test_repo=ClientsInMemoryRepo()
    test_valid=clientValidator()

    test_srv=ClientService(test_repo,test_valid)

    customer1=test_srv.insert_client(1,"Alex Sirb","5020421314002")
    customer2=test_srv.insert_client(2,"Alex Darabant","5020726314007")

    assert len(test_srv.get_all())==2

def test_add_random():
    test_repo=ClientsInMemoryRepo()
    test_valid=clientValidator()

    test_srv=ClientService(test_repo,test_valid)

    test_srv.add_random_clients(5)

    assert len(test_srv.get_all())==5