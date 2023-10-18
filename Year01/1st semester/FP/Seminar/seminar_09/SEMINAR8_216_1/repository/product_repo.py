from domain.entities import Product
from exceptions.exceptions import ProductNotFoundException, DuplicateIDException, CorruptedFileException


class ProductInMemoryRepo:
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
        """
        if id in self.__products:
            return self.__products[id]
        return None

    def store(self, product):
        """
        Adauga produs in lista
        :param product: produsul de adaugat
        :type product: Product
        :return: -; lista de produse se modifica prin adaugarea produsului dat
        :rtype:
        :raises: DuplicateIDException daca exista deja produs cu id dat
        """

        if product.getId() in self.__products:
            raise DuplicateIDException()

        self.__products[product.getId()] = product

    def get_all(self):
        """
        Returneaza o lista cu toate produsele disponibile
        :rtype: list of Product objects
        """
        return list(self.__products.values())

    def size(self):
        """
        Returneaza numarul de produse din lista
        :return: numarul de produse din lista
        :rtype:int
        """
        return len(self.__products)

    def delete(self, id):
        """
        Sterge produsul cu id dat
        :param id: id dat
        :type id: str
        :return: produsul sters
        :rtype: Product
        :raises: ProductNotFoundException daca nu exista produs cu id-ul dat
        """
        if id not in self.__products:
            raise ProductNotFoundException()

        product = self.__products[id]
        del self.__products[id]
        return product

    def update(self, id, product):
        """
        Modifica un produs
        :param id: id-ul produsului de modificat
        :type id: str
        :return: produsul modificat
        :rtype: Product
        :raises: ProductNotFoundException daca nu exista produs cu id-ul dat
        """

        if id not in self.__products:
            raise ProductNotFoundException()

        # old_p = self.__products[id]
        self.__products[id] = product
        return product


def test_store():
    test_repo = ProductInMemoryRepo()
    p1 = Product('1', 'jeleuri', 'Germania')
    p2 = Product('2', 'jeleuri', 'Spania')
    test_repo.store(p1)
    assert (test_repo.size() == 1)
    test_repo.store(p2)
    assert (test_repo.size() == 2)

    try:
        p1 = Product('1', 'jeleuri', 'Germania')
        test_repo.store(p1)
        assert False
    except DuplicateIDException:
        assert True


def test_delete_by_id():
    test_repo = ProductInMemoryRepo()
    p1 = Product('1', 'jeleuri', 'Germania')
    p2 = Product('2', 'dropsuri', 'Romania')
    test_repo.store(p1)
    test_repo.store(p2)

    deleted_p = test_repo.delete('1')
    assert (deleted_p.getName() == 'jeleuri')
    assert (deleted_p.getCountry() == 'Germania')

    try:
        deleted_p = test_repo.delete('wrongid')
        assert False
    except ProductNotFoundException:
        assert True


def test_update():
    test_repo = ProductInMemoryRepo()
    p1 = Product('1', 'jeleuri', 'Germania')
    p2 = Product('1', 'acadele', 'Turcia')

    test_repo.store(p1)

    updated_product = test_repo.update('1', p2)
    assert (updated_product.getName() == 'acadele')
    assert (updated_product.getCountry() == 'Turcia')

    try:
        updated_product = test_repo.update('77', p1)
        assert False
    except ProductNotFoundException:
        assert True


def test_get_all():
    test_repo = ProductInMemoryRepo()
    p1 = Product('1', 'ciocolata martipan', 'Austria')
    test_repo.store(p1)

    crt_products = test_repo.get_all()
    assert (type(crt_products) == list)
    assert (len(crt_products) == 1)
    assert (crt_products[0].getName() == 'ciocolata martipan')


def test_repo_size():
    test_repo = ProductInMemoryRepo()
    test_repo.store(Product('1', 'Snickers, Bounty & Mars Mix', 'SUA'))
    assert (test_repo.size() == 1)


def test_find():
    test_repo = ProductInMemoryRepo()
    p1 = Product('1', 'jeleuri', 'Germania')
    p2 = Product('2', 'acadele', 'Turcia')
    p3 = Product('3', 'ciocolata martipan', 'Austria')
    test_repo.store(p1)

    p = test_repo.find('1')
    assert (p.getName() == 'jeleuri')
    assert (p.getCountry() == 'Germania')

    p2 = test_repo.find('J1203')
    assert (p2 is None)


class ProductRepoFile:
    def __init__(self, filename):
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca datele din fisier

        """
        try:
            f = open(self.__filename, 'r')
            # f = io.open(self.__filename, mode='r', enconding='utf-8')
        except IOError:
            raise CorruptedFileException()

        products = []
        lines = f.readlines()
        for line in lines:
            product_id, product_name, product_country = [token.strip() for token in line.split(';')]
            product = Product(product_id, product_name, product_country)
            products.append(product)

        f.close()
        return products

    def __save_to_file(self, products_list):
        """
        Salveaza in fisier
        :param products_list: lista de produse
        :type products_list: list of Products
        :return: -;produsele sunt salvate in fisier
        :rtype:
        """
        with open(self.__filename, 'w') as f:
            for product in products_list:
                product_string = str(product.getId()) + ';' + \
                                 str(product.getName()) + ';' + str(product.getCountry()) + '\n'
                f.write(product_string)

    def find(self, id):
        #TO DO: add specification
        all_products = self.__load_from_file()
        for product in all_products:
            if product.getId() == id:
                return product
        return None

    def __find_by_index(self, all_products, id):
        #TO DO: add specification

        index = -1
        for i in range(len(all_products)):
            if all_products[i].getId() == id:
                index = i
        return index

    def store(self, product):
        """
        Adauga produs in lista
        :param product: produsul de adaugat
        :type product: Product
        :return: -; lista de produse se modifica prin adaugarea produsului dat
        :rtype:
        :raises: DuplicateIDException daca exista deja produs cu id dat
        """
        all_products = self.__load_from_file()
        if product in all_products:
            raise DuplicateIDException()

        all_products.append(product)
        self.__save_to_file(all_products)

    def update(self, id, new_product):
        """
        Modifica un produs
        :param id: id-ul produsului de modificat
        :type id: str
        :return: produsul modificat
        :rtype: Product
        :raises: ProductNotFoundException daca nu exista produs cu id-ul dat
        """

        all_products = self.__load_from_file()
        index = self.__find_by_index(all_products, id)
        if index == -1:
            raise ProductNotFoundException()

        all_products[index] = new_product
        self.__save_to_file(all_products)
        return new_product

    def delete(self, id):
        #TO DO: add specification
        all_products = self.__load_from_file()
        index = self.__find_by_index(all_products, id)
        if index == -1:
            raise ProductNotFoundException()

        deleted_product = all_products.pop(index)
        self.__save_to_file(all_products)
        return deleted_product

    def size(self):
        #TO DO: add specification

        return len(self.__load_from_file())

    def delete_all(self):
        #TO DO: add specification

        self.__save_to_file([])

    def get_all(self):
        #TO DO: add specification

        return self.__load_from_file()


def test_store_file():
    test_repo = ProductRepoFile('test_products.txt')
    test_repo.delete_all()
    test_repo.store(Product('1', 'jeleuri', 'Germania'))

    assert (test_repo.size() == 1)

    try:
        test_repo.store(Product('1', 'jeleuri', 'Germania'))
        assert False
    except DuplicateIDException:
        assert True
