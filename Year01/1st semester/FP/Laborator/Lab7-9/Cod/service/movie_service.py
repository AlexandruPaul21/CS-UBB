from domain.entities import movie
from domain.validators import movieValidator
from repository.movies_repo import MovieInMemoryRepo
from utils.utils import string_generator

class MovieService():
    """
    Controlerul GRASP al aplicatiei-realizeaza legatura dintre ui si domain
    """
    def __init__(self,repo,validator):
        """
        Initializeaza elementele clasei de service
        :param repo: reposistory-ul
        :param validator: validatorul
        """
        self.__repo=repo
        self.__validator=validator

    def insert_movie(self,id,title,desc,gen):
        """
        Functia incearca introducerea filmului in memorie
        :param title: titlul filmului
        :param desc: descrierea filmului
        :param gen: genul filmului
        :return: filmul adaugat
        :raises: ValueError daca produsul e invalid
        """
        film=movie(id,title,desc,gen)
        self.__validator.validate(film)
        self.__repo.add_movie(film)
        return film

    def erase_movie(self,id):
        """
        Functia incearca stergerea filmului
        :param id: id-ul la care se sterge filmul
        """
        self.__repo.delete_movie(id)

    def modify_film(self,id,title,desc,gen):
        """
        Modifica filmul mentionat
        :param id: id-ul filmului de inlocuit
        :param title: titlul filmului
        :param desc: descrierea filmului
        :param gen: genul filmului
        :return: filmul modificat
        :raises: ValueErorr pentru date invalide
        """
        film=movie(id,title,desc,gen)
        self.__validator.validate(film)
        self.__repo.modify_movie(id,title,desc,gen)
        return film

    def search_by_id(self,id):
        """
        Cauta filmul dupa id
        :param id: id-ul filmului
        :return: filmul cautat
        :raises: ValueError daca filmul nu este gasit
        """
        film=self.__repo.search_movie_by_id(id)
        return film

    def search_by_param(self,title,desc,gen):
        """
        Cauta filmul dupa parametrii
        :param title: titlul filmului
        :param desc: descrirerea filmului
        :param gen: genul filmului
        :return: filmul cautat
        :raises: ValueError daca filmul nu este gasit
        """
        film=self.__repo.search_movie_by_param(title,desc,gen)
        return film

    def add_random_movies(self,nr):
        """
        Functia adauga filme random
        :param nr: nr de filme adaugate
        """
        for index in range(nr):
            film=movie(1,string_generator(6),string_generator(15),string_generator(5))
            self.__validator.validate(film)
            self.__repo.add_movie(film)

    def search_id(self,id):
        """
        Verifica daca exista film cu id-ul cautat
        :param id: id-ul filmului
        :raises: ValueError daca filmul nu este gasit
        """
        self.__repo.searchID(id)

    def get_all(self):
        """
        Retureneaza toate filmele din repo
        """
        return self.__repo.get_all_movies()

