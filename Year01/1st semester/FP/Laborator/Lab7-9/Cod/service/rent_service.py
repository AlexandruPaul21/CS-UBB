from repository.rent_repo import RentInMemoryRepo
from repository.clients_repo import ClientsInMemoryRepo
from repository.movies_repo import MovieInMemoryRepo

class RentService():
    def __init__(self,repo):
        """
        Initializeaza clasa
        :param repo: repositroty ul din care citesc
        """
        self.__repo=repo

    def add_rent_op(self,id1,id2):
        """
        Se adauga o operatie de inchiriere
        :param id1: id-ul clientului
        :param id2: id-ul filmului
        """
        self.__repo.add_rent(id1,id2)

    def remove_rent_op(self,id1,id2):
        """
        Se realizeaza operatia de anulare a inchirierii
        :param id1: id-ul clientului
        :param id2: id-ul filmului
        """
        self.__repo.delete_rent(id1,id2)

    def get_list_with_ids(self):
        """
        Obtine din repo lista de id-uri
        :return: lista de id-uri
        """
        return self.__repo.get_list()

    def get_list_with_id_m(self):
        """
        Obtine lista de filme si cati clienti le-au inchiriat
        :return: lista de filme
        """
        return self.__repo.get_all_for_max()

    def get_all_for_id(self,id):
        """
        Functia identifica toate filmele inchiriate de un client
        :param id: id-ul clientului
        :return: lista de id-uri ale filmelor pe care clientul le are inchiriate
        """
        return self.__repo.get_all_id(id)

    def get_all_for_id2(self,id):
        """
        Functia identifica toti clientii ce au inchiriat un film
        :param id: id-ul filmului
        :return: lista de id-uri ale filmelor pe care clientul le are inchiriate
        """
        return self.__repo.get_all_id2(id)