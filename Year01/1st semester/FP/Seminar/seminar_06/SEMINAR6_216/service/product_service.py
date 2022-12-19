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

    def add_product(self, denumire, stoc, pret):
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
        :raises: ValueError daca produsul e invalid

        """
        product = Product(denumire, stoc, pret)
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


def test_add_product():
    test_repo = InMemoryRepository()
    test_val = ProductValidator()

    test_srv = ProductService(test_repo, test_val)

    product = test_srv.add_product('jeleuri', 33, 1.25)
    assert (product.getName() == 'jeleuri')
    assert (product.getStoc() == 33)

    assert (len(test_srv.get_all_products()) == 1)

    try:
        test_srv.add_product('', 3,2.32 )
        assert False
    except ValueError:
        assert True

def test_get_all_products():
    pass
