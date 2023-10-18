from domain.entities import Product
from domain.validators import ProductValidator
from exceptions.exceptions import ValidationException, ProductNotFoundException
from repository.product_repo import ProductInMemoryRepo


class ProductService:
    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiectul de tip repo care ne ajuta sa gestionam lista de produse
        :type repo: ProductRepo
        :param validator: validatorul pentru verificarea produselor
        :type validator: ProductValidator
        """
        self.__repo = repo
        self.__validator = validator

    def add_product(self, id, denumire, tara_origine):
        """
        Adauga produs
        :param denumire: denumirea produsului
        :type denumire:str

        :return: produsul adaugat in lista
        :rtype: Product
        :raises: ValidationException daca produsul e invalid
                 DuplicateIDException daca mai exista un produs cu id-ul dat

        """
        product = Product(id, denumire, tara_origine)
        self.__validator.validate(product)
        self.__repo.store(product)
        return product

    def get_all_products(self):
        """
        Returneaza o lista cu toate produsele disponibile
        :return: lista de produse disponibile
        :rtype: list of Product objects
        """
        return self.__repo.get_all()

    def delete_by_id(self, id):
        """
        Sterge produs cu id dat
        :param id: id-ul dat
        :type id: str
        :return: produsul sters
        :rtype: Product
        :raises: ProductNotFoundException daca nu exista produs cu id-ul dat
        """
        return self.__repo.delete(id)

    def update_product(self, id, denumire, tara_origine):
        """
        Modifica produsul cu id id cu datele date
        :param id: id-ul dat
        :type id: str
        :param denumire: noua denumire a produsului
        :type denumire: str
        :param tara_origine: tara de origine a produsului
        :type tara_origine: str
        :return: produsul modificat
        :rtype: Product
        :raises: ProductNotFoundException daca nu exista produs cu id dat
                 ValidationException daca noul produs e invalid
        """
        product = Product(id, denumire, tara_origine)
        self.__validator.validate(product)
        return self.__repo.update(id, product)


