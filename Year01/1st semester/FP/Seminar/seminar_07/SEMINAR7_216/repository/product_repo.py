from domain.entities import Product


class InMemoryRepository:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de produse (i.e. sa ofere un depozit persistent pentru obiecte
        de tip Product)

        CReateUpdateDelete

        """

    def __init__(self):
        # lista: [produs1, produs2, produs3]

        self.__products = []

    def find(self, id):
        """
        Cauta un produs cu id-ul dat in lista
        :param id: id-ul dat
        :type id: str
        :return: produsul cu id-ul dat, None daca nu exista produs cu id-ul cautat
        :rtype: Product
        """
        for product in self.__products:
            if product.getId() == id:
                return product
        return None

    def store(self, product):
        """
        Adauga produs in lista
        :param product: produsul de adaugat
        :type product: Product
        :return: -; lista de produse se modifica prin adaugarea produsului dat
        :raises: ValueError daca exista produs cu acelasi id in lista
        """
        p = self.find(product.getId())
        if p is not None:
            raise ValueError('Produsul cu acest id exista deja.')

        self.__products.append(product)

    def get_all_products(self):
        """
        Returneaza o lista cu toate produsele disponibile
        :rtype: list of Product objects
        """
        return self.__products

    def size(self):
        """
        Returneaza numarul de produse din lista
        :return: numarul de produse din lista
        :rtype:int
        """
        return len(self.__products)

    def __find_index(self, id):
        """
        Gaseste indexul (pozitia) produsului cu identificator id in lista
        :param id: id dat
        :type id: str
        :return: index-ul produsului cu id id in lista, -1 daca produsul nu exista
        :rtype: int (>=0, <repo.size())
        """
        index = -1
        for i in range(self.size()):
            if self.__products[i].getId() == id:
                index = i
        return index

    def delete_product(self, id):
        """
        Sterge produsul cu id dat
        :param id: id dat
        :type id: str
        :return: produsul sters
        :rtype: Product
        """
        product = self.find(id)
        if product is None:
            raise ValueError('Nu exista produs cu ID-ul dat.')

        self.__products.remove(product)
        return product

        # index = self.__find_index(id)
        # if index == -1:
        #     raise ValueError('Nu exista produs cu acest id.')
        # return self.__products.pop(index)
        # remove(value)
        # pop(index) - pop() - elimina ultimul element
        # del lista[index]

    def update_product(self, id, product):
        """
        Modifica un produs
        :param id: id-ul produsului de modificat
        :type id: str
        :return: produsul modificat
        :rtype: Product
        :raises: ValueError daca nu exista produs cu identificator id in lista
        """
        p = self.find(id)
        if p is None:
            raise ValueError('Nu exista produs cu acest id.')
        p.setName(product.getName())
        p.setPret(product.getPret())
        p.setStoc(product.getStoc())
        return p

        # index = self.__find_index(id)
        # if index==-1:
        #     raise ValueError('Nu exista produs cu ID-ul dat.')
        #
        # #old_p = self.__products[index]
        # self.__products[index]=product
        # return product

    def delete_by_criteria(self, filter_function):
        """
        Sterge produse din multime dupa un criteriu dat
        :param filter_function: functia (criteriul dupa care se sterg produsele)
        :type filter_function: function
        :return: numarul de produse sterse
        :rtype: int
        """
        initial_no_products = self.size()
        self.__products = [product for product in self.__products if not filter_function(product)]
        return initial_no_products - self.size()

    def delete_all(self):
        """
        Sterge toate produsele din lista
        """
        self.__products.clear()
        # self.__products = []

    def get_products_by_criteria(self, filter_function):
        """
        Selecteaza elementele din lista care indeplinesc un criteriu
        :param filter_function: functia dupa care se filtreaza
        :type filter_function: function
        :return: lista de produse care indeplinesc criteriul
        :rtype: list of products
        """
        return [product for product in self.__products if filter_function(product)]


class InMemoryRepoDict():
    def __init__(self):

        # {id_produs1: produs1, id_produs2: produs2, id_produs3: produs3}

        self.__products = {}

    def find(self, id):
        """
        Cauta un produs cu id-ul dat in lista
        :param id: id-ul dat
        :type id: str
        :return: produsul cu id-ul dat, None daca nu exista produs cu id-ul cautat
        :rtype: Product
        :raises: KeyError daca nu exista produs cu id-ul dat
        """
        return self.__products[id]

    def store(self, product):
        """
        Adauga produs in lista
        :param product: produsul de adaugat
        :type product: Product
        :return: -; lista de produse se modifica prin adaugarea produsului dat
        :rtype:
        :raises: ValueError daca exista deja produs cu id dat
        """

        if product.getId() in self.__products:
            raise ValueError('Produsul cu acest id exista deja.')

        self.__products[product.getId()] = product

    def get_all_products(self):
        """
        Returneaza o lista cu toate produsele disponibile
        :rtype: list of Product objects
        """
        return self.__products.values()

    def size(self):
        """
        Returneaza numarul de produse din lista
        :return: numarul de produse din lista
        :rtype:int
        """
        return len(self.__products)

    def delete_product(self, id):
        """
        Sterge produsul cu id dat
        :param id: id dat
        :type id: str
        :return: produsul sters
        :rtype: Product
        """
        if id not in self.__products:
            raise ValueError('Nu exista produs cu ID-ul dat.')

        product = self.__products[id]
        del self.__products[id]
        return product

    def update_product(self, id, product):
        """
        Modifica un produs
        :param id: id-ul produsului de modificat
        :type id: str
        :return: produsul modificat
        :rtype: Product
        :raises: ValueError daca nu exista produs cu identificator id in lista
        """

        if id not in self.__products:
            raise ValueError('Nu exista produs cu acest id.')

        # old_p = self.__products[id]
        self.__products[id] = product
        return product


def setup_test_repo():
    p1 = Product('J1001', 'jeleuri', 21, 5.4)
    p2 = Product('J1002', 'jeleuri haribo', 287, 6.21)
    p3 = Product('C5000', 'ciocolata Milka', 100, 4.32)
    p4 = Product('C5001', 'ciocolata Poiana', 87, 3.95)
    p5 = Product('C5002', 'praline belgiene', 2, 10.95)
    p6 = Product('N1234', 'napolitane', 0, 3.95)
    p7 = Product('A1002', 'acadele', 213, 1.70)
    p8 = Product('SPEC12', 'bomboane Halloween', 0, 4.99)
    p9 = Product('SPEC13', 'bomboane Craciun', 501, 7.99)
    p10 = Product('SPEC14', 'figurina ciocolata om zapada', 110, 3.95)

    test_repo = InMemoryRepository()
    test_repo.store(p1)
    test_repo.store(p2)
    test_repo.store(p3)
    test_repo.store(p4)
    test_repo.store(p5)
    test_repo.store(p6)
    test_repo.store(p7)
    test_repo.store(p8)
    test_repo.store(p9)
    test_repo.store(p10)
    return test_repo


def test_store():
    test_repo = InMemoryRepository()
    p1 = Product('1', 'jeleuri', 10, 12.5)
    p2 = Product('2', 'jeleuri', 10, 12.5)
    test_repo.store(p1)
    assert (test_repo.size() == 1)
    test_repo.store(p2)
    assert (test_repo.size() == 2)

    try:
        p1 = Product('1', 'jeleuri', 10, 12.5)
        test_repo.store(p1)
        assert False
    except ValueError:
        assert True


def test_delete_by_id():
    test_repo = InMemoryRepository()
    p1 = Product('1', 'jeleuri', 10, 12.5)
    p2 = Product('2', 'acadele', 12, 8.5)
    test_repo.store(p1)
    test_repo.store(p2)

    deleted_p = test_repo.delete_product('1')
    assert (deleted_p.getName() == 'jeleuri')
    assert (deleted_p.getPret() == 12.5)
    assert (deleted_p.getStoc() == 10)

    try:
        deleted_p = test_repo.delete_product('wrongid')
        assert False
    except ValueError:
        assert True


def test_update_product():
    test_repo = InMemoryRepository()
    p1 = Product('1', 'jeleuri', 10, 12.5)

    p2 = Product('1', 'acadele', 12, 8.5)

    test_repo.store(p1)

    updated_product = test_repo.update_product('1', p2)
    assert (updated_product.getName() == 'acadele')
    assert (updated_product.getStoc() == 12)
    assert (updated_product.getPret() == 8.5)

    try:
        updated_product = test_repo.update_product('77', p1)
        assert False
    except ValueError:
        assert True


def test_get_all_products():
    test_repo = InMemoryRepository()
    p1 = Product('1', 'ciocolata martipan', 25, 7.5)
    test_repo.store(p1)

    crt_products = test_repo.get_all_products()
    assert (type(crt_products) == list)
    assert (len(crt_products) == 1)
    assert (crt_products[0].getName() == 'ciocolata martipan')

    test_repo2 = setup_test_repo()
    crt_products = test_repo2.get_all_products()
    assert (len(crt_products) == 10)


def test_repo_size():
    test_repo = InMemoryRepository()
    test_repo.store(Product('1', 'Snickers, Bounty & Mars Mix', 20, 25.99))
    assert (test_repo.size() == 1)

    test_repo1 = setup_test_repo()
    assert (test_repo1.size() == 10)
    test_repo1.delete_product('J1001')
    test_repo1.delete_product('SPEC12')
    assert (test_repo1.size() == 8)


def test_find():
    test_repo = setup_test_repo()
    p = test_repo.find('J1001')
    assert (p.getName() == 'jeleuri')
    assert (p.getStoc() == 21)
    assert (p.getPret() == 5.4)

    p2 = test_repo.find('J1203')
    assert (p2 is None)


def test_delete_by_criteria():
    test_repo = setup_test_repo()

    # assume x is a Product

    # sterge produsele care contin 'jeleuri' in nume
    filter_by_name1 = lambda x: 'jeleuri' in x.getName()
    test_repo.delete_by_criteria(filter_by_name1)
    assert (test_repo.size() == 8)

    # sterge produsele al caror nume are mai mult de 2 cuvinte
    filter_by_name2 = lambda x: len(x.getName().split()) > 2
    test_repo.delete_by_criteria(filter_by_name2)
    assert (test_repo.size() == 7)

    # sterge produsele al caror nume incepe cu 'c'
    filter_by_name3 = lambda x: x.getName().startswith('c')
    test_repo.delete_by_criteria(filter_by_name3)
    assert (test_repo.size() == 5)

    # sterge dupa unitati stoc

    test_repo2 = setup_test_repo()
    # sterge produsele care nu sunt in stoc
    filter_by_stock1 = lambda x: x.getStoc() == 0
    test_repo2.delete_by_criteria(filter_by_stock1)
    assert (test_repo2.size() == 8)

    # sterge produsele pentru care exista intre 10 si 30 unitati in stoc
    filter_by_stock2 = lambda x: x.getStoc() >= 10 and x.getStoc() <= 30
    test_repo2.delete_by_criteria(filter_by_stock2)
    assert (test_repo2.size() == 7)

    # sterge produsele pentru care exista mai putin de 10 produse
    filter_by_stock3 = lambda x: x.getStoc() < 10
    test_repo2.delete_by_criteria(filter_by_stock3)
    assert (test_repo2.size() == 6)

    # sterge dupa pret

    test_repo3 = setup_test_repo()

    # sterge produsele care au pretul mai mare de 4
    filter_by_price1 = lambda x: x.getPret() > 4
    test_repo3.delete_by_criteria(filter_by_price1)
    assert (test_repo3.size() == 4)

    # sterge produsele pentru care pretul e ceva.99

    filter_by_price2 = lambda x: int(x.getPret() - 0.99) == x.getPret() - 0.99
    test_repo3.delete_by_criteria(filter_by_price2)
    assert (test_repo3.size() == 4)

    new_p = Product('CHR1004', 'figurina ciocolata mos craciun', 11, 30.99)
    test_repo3.store(new_p)
    assert (test_repo3.size() == 5)

    how_many_deleted = test_repo3.delete_by_criteria(filter_by_price2)
    assert (how_many_deleted == 1)
    assert (test_repo3.size() == 4)


def test_filter_by_criteria():
    test_repo = setup_test_repo()

    # assume x is a Product

    # selecteaza produsele care contin 'jeleuri' in nume
    filter_by_name1 = lambda x: 'cioco' in x.getName()
    filtered_list = test_repo.get_products_by_criteria(filter_by_name1)
    assert (test_repo.size() == 10)
    assert (len(filtered_list) == 3)

    # selecteaza produsele al caror nume este format din mai mult de 15 caractere
    filter_by_name2 = lambda x: len(x.getName()) > 15
    filtered_list = test_repo.get_products_by_criteria(filter_by_name2)
    assert (test_repo.size() == 10)
    assert (len(filtered_list) == 5)

    # selecteaza produsele pentru care numarul de unitati in stoc este multiplu de 10
    filter_by_stock1 = lambda x: x.getStoc() % 10 == 0
    filtered_list = test_repo.get_products_by_criteria(filter_by_stock1)
    assert (test_repo.size() == 10)
    # 0%10==0
    assert (len(filtered_list) == 4)

    # selecteaza produsele pentru care pretul este mai mic de 5 sau mai mare de 20 lei
    filter_by_price1 = lambda x: x.getPret() < 5 or x.getPret() > 20
    filtered_list = test_repo.get_products_by_criteria(filter_by_price1)
    assert (test_repo.size() == 10)
    assert (len(filtered_list) == 6)

