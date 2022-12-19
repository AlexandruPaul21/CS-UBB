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


def test_add_product():
    test_repo = ProductInMemoryRepo()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)

    product = test_srv.add_product('1', 'jeleuri', 'Germania')
    assert (product.getName() == 'jeleuri')
    assert (product.getCountry() == 'Germania')

    assert (len(test_srv.get_all_products()) == 1)

    try:
        test_srv.add_product('2', '', 'Austria')
        assert False
    except ValidationException:
        assert True


def test_get_all_products():
    test_repo = ProductInMemoryRepo()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    added_product = test_srv.add_product('JELLIES1', 'jeleuri', 'Germania')
    assert (type(test_srv.get_all_products()) == list)
    assert (len(test_srv.get_all_products()) == 1)
    crt_products = test_srv.get_all_products()
    assert (crt_products[0].getName() == 'jeleuri')

    added_product2 = test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 'SUA')
    assert (len(test_srv.get_all_products()) == 2)

    # this shouldn't add anything (duplicate id)
    try:
        test_srv.add_product('JELLIES3', 'marshmallows', 'Belgia')
    except:
        pass
    assert (len(test_srv.get_all_products()) == 2)

    test_srv.delete_by_id('JELLIES1')
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 1)
    assert (crt_products[0].getName() == 'jeleuri acrisoare')

    test_srv.update_product('JELLIES3', 'Sour Patch', 'SUA')
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 1)
    assert (crt_products[0].getName() == 'Sour Patch')


def test_delete_product():
    test_repo = ProductInMemoryRepo()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    test_srv.add_product('JELLIES1', 'jeleuri', 'Germania')
    test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 'SUA')
    test_srv.add_product('MISC0011', 'marshmallows', 'Belgia')

    deleted_product1 = test_srv.delete_by_id('JELLIES1')
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 2)
    assert (deleted_product1.getName() == 'jeleuri')
    assert (deleted_product1.getCountry() == 'Germania')

    # daca avem find in service, putem testa si ca nu mai exista
    # niciun produs cu id 'JELLIES1' in lista de produse

    deleted_product2 = test_srv.delete_by_id('JELLIES3')
    crt_products = test_srv.get_all_products()
    assert (len(crt_products) == 1)
    assert (deleted_product2.getName() == 'jeleuri acrisoare')
    assert (deleted_product2.getCountry() == 'SUA')

    try:
        test_srv.delete_by_id('WRONG ID')
        assert False
    except ProductNotFoundException:
        assert True
    assert (len(crt_products) == 1)


def test_update_product():
    test_repo = ProductInMemoryRepo()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)
    test_srv.add_product('JELLIES1', 'jeleuri', 'Germania')
    test_srv.add_product('JELLIES3', 'jeleuri acrisoare', 'SUA')
    test_srv.add_product('MISC0011', 'marshmallows', 'Belgia')

    updated_product = test_srv.update_product('JELLIES3', 'Sour Patch', 'SUA')

    assert (updated_product.getName() == 'Sour Patch')
    assert (updated_product.getCountry() == 'SUA')

    try:
        test_srv.update_product('INVALID ID', 'jeleuri Haribo', 'Germania')
        assert False
    except ProductNotFoundException:
        assert True
