from domain.entities import Product
from domain.validators import ProductValidator
from repository.product_repo import InMemoryRepository


class ProductService:
    """
        GRASP Controller
        Responsabil de efectuarea operatiilor cerute de utilizator
        Coordoneaza operatiile necesare pentru a realiza actiunea declansata de utilizator
        (i.e. declansare actiune: utilizator -> ui-> obiect tip service in ui -> service -> service coordoneaza operatiile
        folosind alte obiecte (e.g. repo, validator) pentru a realiza efectiv operatia)
    """

    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiectul de tip repo care ne ajuta sa gestionam lista de produse
        :type repo:InMemoryRepository
        :param validator: validatorul pentru verificarea produselor
        :type validator: ProductValidator
        """
        self.__repo = repo
        self.__validator = validator

    def add_product(self, id, denumire, stoc, pret):
        """
        Adauga produs
        :param denumire: denumirea produsului
        :type denumire:str
        :param stoc: numarul de unitati in stoc pentru produs
        :type stoc:int
        :param pret: pretul pe unitate al produsului
        :type pret: float
        :return: produsul adaugat in lista
        :rtype: Product
        :raises: ValueError daca produsul e invalid sau mai exista un produs cu id-ul dat

        """
        product = Product(id, denumire, stoc, pret)
        self.__validator.validate(product)
        self.__repo.store(product)
        return product

    def get_all_products(self):
        """
        Returneaza o lista cu toate produsele disponibile
        :return: lista de produse disponibile
        :rtype: list of Product objects
        """
        return self.__repo.get_all_products()

    def delete_by_id(self, id):
        """
        Sterge produs cu id dat
        :param id: id-ul dat
        :type id: str
        :return: produsul sters
        :rtype: Product
        :raises: ValueError daca nu exista produs cu id-ul dat
        """
        return self.__repo.delete_product(id)

    def update_product(self, id, denumire, stoc, pret):
        """
        Modifica produsul cu id id cu datele date
        :param id: id-ul dat
        :type id: str
        :param denumire: noua denumire a produsului
        :type denumire: str
        :param stoc: noul numar de unitati in stoc
        :type stoc: int
        :param pret: noul pret al produsului
        :type pret: float
        :return: produsul modificat
        :rtype: Product
        :raises: ValueError daca nu exista produs cu id dat
        """
        product = Product(id, denumire, stoc, pret)
        self.__validator.validate(product)
        return self.__repo.update_product(id, product)

    def delete_stock_zero(self):
        """
        Sterge toate produsele care au stoc 0
        :return: numarul de produse sterse
        :rtype: int
        """
        how_many_deleted = self.__repo.delete_by_criteria(lambda x: x.getStoc() == 0)
        return how_many_deleted

    def filter_by_name(self, str_to_search):
        """
        Selecteaza produsele care contin str_to_search in nume
        :param str_to_search: string de cautat in numele produselor
        :type str_to_search: str
        :return: lista de produse care au str_to_search in nume
        :rtype: list of Product objects
        """
        all_products = self.get_all_products()
        filtered_list = [product for product in all_products if str_to_search in product.getName()]
        return filtered_list


def test_add_product():
    test_repo = InMemoryRepository()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)

    product = test_srv.add_product('1', 'jeleuri', 33, 1.25)
    assert (product.getName() == 'jeleuri')
    assert (product.getStoc() == 33)

    assert (len(test_srv.get_all_products()) == 1)

    try:
        test_srv.add_product('2', '', 3, 2.32)
        assert False
    except ValueError:
        assert True


def test_get_all_products():
    test_repo = InMemoryRepository()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    added_product = test_srv.add_product('JELLIES1', 'jeleuri', 9, 10)
    assert (type(test_srv.get_all_products()) == list)
    assert (len(test_srv.get_all_products()) == 1)
    crt_products = test_srv.get_all_products()
    assert (crt_products[0].getName() == 'jeleuri')

    added_product2 = test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 34, 5.5)
    assert (len(test_srv.get_all_products()) == 2)
    assert (crt_products[1].getStoc() == 34)
    assert (crt_products[1].getPret() == 5.5)

    # this shouldn't add anything (duplicate id)
    try:
        test_srv.add_product('JELLIES3', 'marshmallows', 10, 7.89)
    except:
        pass
    assert (len(test_srv.get_all_products()) == 2)

    test_srv.delete_by_id('JELLIES1')
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 1)
    assert (crt_products[0].getName() == 'jeleuri acrisoare')

    test_srv.update_product('JELLIES3', 'Sour Patch', 34, 5.5)
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 1)
    assert (crt_products[0].getName() == 'Sour Patch')


def test_delete_product():
    test_repo = InMemoryRepository()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    test_srv.add_product('JELLIES1', 'jeleuri', 9, 10)
    test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 34, 5.5)
    test_srv.add_product('MISC0011', 'marshmallows', 10, 7.89)

    deleted_product1 = test_srv.delete_by_id('JELLIES1')
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 2)
    assert (deleted_product1.getName() == 'jeleuri')
    assert (deleted_product1.getStoc() == 9)
    assert (deleted_product1.getPret() == 10)

    # daca avem find in service, putem testa si ca nu mai exista
    # niciun produs cu id 'JELLIES1' in lista de produse

    deleted_product2 = test_srv.delete_by_id('JELLIES3')
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 1)
    assert (deleted_product2.getName() == 'jeleuri acrisoare')
    assert (deleted_product2.getStoc() == 34)
    assert (deleted_product2.getPret() == 5.5)

    try:
        test_srv.delete_by_id('WRONG ID')
        assert False
    except ValueError:
        assert True
    assert (len(crt_products) == 1)


def test_update_product():
    test_repo = InMemoryRepository()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    test_srv.add_product('JELLIES1', 'jeleuri', 9, 10)
    test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 34, 5.5)
    test_srv.add_product('MISC0011', 'marshmallows', 10, 7.89)

    updated_product = test_srv.update_product('JELLIES3', 'Sour Patch', 34, 5.5)

    assert (updated_product.getName() == 'Sour Patch')
    assert (updated_product.getStoc() == 34)
    assert (updated_product.getPret() == 5.5)

    try:
        test_srv.update_product('INVALID ID', 'jeleuri Haribo', 9, 10)
        assert False
    except ValueError:
        assert True


def test_delete_stock_zero():
    test_repo = InMemoryRepository()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    test_srv.add_product('JELLIES1', 'jeleuri', 0, 10)
    test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 0, 5.5)
    test_srv.add_product('MISC0011', 'marshmallows', 10, 7.89)

    test_srv.delete_stock_zero()
    assert (len(test_srv.get_all_products()) == 1)

    # better if find by id is used
    assert (test_srv.get_all_products()[0].getName() == 'marshmallows')

    test_srv.delete_stock_zero()
    assert (len(test_srv.get_all_products()) == 1)


def test_filter_by_name():
    test_repo = InMemoryRepository()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    test_srv.add_product('JELLIES1', 'jeleuri', 0, 10)
    test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 0, 5.5)
    test_srv.add_product('MISC0011', 'marshmallows', 10, 7.89)

    filtered_list = test_srv.filter_by_name('jeleuri')
    assert (len(filtered_list) == 2)

    filtered_list = test_srv.filter_by_name('acadele')
    assert (len(filtered_list) == 0)

    filtered_list = test_srv.filter_by_name('ma')
    assert (len(filtered_list) == 1)
