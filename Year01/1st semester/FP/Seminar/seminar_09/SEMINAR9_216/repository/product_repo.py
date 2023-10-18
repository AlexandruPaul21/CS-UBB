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

    def delete_all(self):
        self.__products = {}


class ProductRepoFile:
    def __init__(self, filename):
        self.__filename = filename

    def __load_from_file(self):
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
        with open(self.__filename, 'w') as f:
            for product in products_list:
                product_string = str(product.getId()) + ';' + \
                                 str(product.getName()) + ';' + str(product.getCountry()) + '\n'
                f.write(product_string)

    def find(self, id):
        all_products = self.__load_from_file()
        for product in all_products:
            if product.getId() == id:
                return product
        return None

    def __find_by_index(self, all_products, id):
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
        all_products = self.__load_from_file()
        index = self.__find_by_index(all_products, id)
        if index == -1:
            raise ProductNotFoundException()

        deleted_product = all_products.pop(index)
        self.__save_to_file(all_products)
        return deleted_product

    def size(self):
        return len(self.__load_from_file())

    def delete_all(self):
        self.__save_to_file([])

    def get_all(self):
        return self.__load_from_file()


class ProductRepoFileInheritance(ProductInMemoryRepo):
    def __init__(self, filename):
        ProductInMemoryRepo.__init__(self)
        self.__filename = filename
        self.__load_from_file()

    def __load_from_file(self):
        try:
            f = open(self.__filename, 'r')
            # f = io.open(self.__filename, mode='r', enconding='utf-8')
        except IOError:
            raise CorruptedFileException()

        lines = f.readlines()
        for line in lines:
            product_id, product_name, product_country = [token.strip() for token in line.split(';')]
            product = Product(product_id, product_name, product_country)

            ProductInMemoryRepo.store(self, product)
        f.close()

    def __save_to_file(self):
        products_list = ProductInMemoryRepo.get_all(self)
        with open(self.__filename, 'w') as f:
            for product in products_list:
                product_string = str(product.getId()) + ';' + \
                                 str(product.getName()) + ';' + str(product.getCountry()) + '\n'
                f.write(product_string)

    def store(self, product):
        ProductInMemoryRepo.store(self, product)
        self.__save_to_file()

    def update(self, id, new_product):
        modified_product = ProductInMemoryRepo.update(self, id, new_product)
        self.__save_to_file()
        return modified_product

    def delete(self, id):
        deleted_product = ProductInMemoryRepo.delete(self, id)
        self.__save_to_file()
        return deleted_product

    def find(self, id):
        return ProductInMemoryRepo.find(self, id)

    def get_all(self):
        return ProductInMemoryRepo.get_all(self)

    def size(self):
        return ProductInMemoryRepo.size(self)

    def delete_all(self):
        ProductInMemoryRepo.delete_all(self)
        self.__save_to_file()
