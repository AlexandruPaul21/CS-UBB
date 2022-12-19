from domain.entities import Shop
from exceptions.exceptions import DuplicateIDException, ShopNotFoundException


class ShopInMemoryRepo:
    def __init__(self):

        # {id_magazin1: magazin1, id_magazin2: magazin2, id_magazin3: magazin3}

        self.__shops = {}

    def find(self, id):
        """
        Cauta un magazin cu id-ul dat in lista
        :param id: id-ul dat
        :type id: str
        :return: magazinul cu id-ul dat, None daca nu exista magazin cu id-ul cautat
        :rtype: Shop
        """
        if id in self.__shops:
            return self.__shops[id]
        return None

    def store(self, shop):
        """
        Adauga magazin in lista
        :param shop: magazinul de adaugat
        :type shop: Shop
        :return: -; lista de magazine se modifica prin adaugarea magazinului dat
        :rtype:
        :raises: DuplicateIDException daca exista deja magazin cu id dat
        """

        if shop.getId() in self.__shops:
            raise DuplicateIDException()

        self.__shops[shop.getId()] = shop

    def get_all(self):
        """
        Returneaza o lista cu toate magazinele disponibile
        :rtype: list of Shop objects
        """
        return list(self.__shops.values())

    def size(self):
        """
        Returneaza numarul de magazine din lista
        :return: numarul de magazine din lista
        :rtype:int
        """
        return len(self.__shops)

    def delete(self, id):
        """
        Sterge magazinul cu id dat
        :param id: id dat
        :type id: str
        :return: magazinul sters
        :rtype: Shop
        :raises: ShopNotFoundException daca nu exista magazin cu id-ul dat
        """
        if id not in self.__shops:
            raise ShopNotFoundException()

        shop = self.__shops[id]
        del self.__shops[id]
        return shop

    def update(self, id, shop):
        """
        Modifica un magazin
        :param id: id-ul magazinului de modificat
        :type id: str
        :return: magazinul modificat
        :rtype: Shop
        :raises: ShopNotFoundException daca nu exista magazin cu id-ul dat
        """

        if id not in self.__shops:
            raise ShopNotFoundException()

        # old_p = self.__shops[id]
        self.__shops[id] = shop
        return shop


# tests

class ShopRepoFile:
    def __init__(self, filename):
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca magazinele din fisier
        :return: lista cu magazine
        :rtype: list of Shops
        :raises: Exception daca nu se poate citi din fisier
        """
        try:
            f = open(self.__filename, 'r')
        except IOError:
            # file doesn't exist
            return
        shops = []
        lines = f.readlines()
        for line in lines:
            shop_id, shop_name, shop_location = [word.strip() for word in line.split(';')]
            a = Shop(shop_id, shop_name, shop_location)
            shops.append(a)
        f.close()
        return shops

    def __save_to_file(self, shops):
        """
        Salveaza in fisier o lista de magazine
        :param shops: lista de magazine care se salveaza
        :type shops: list of Shops
        :return: -; magazinele sunt salvate in fisier
        :rtype: -
        :raises: Exception daca nu se poate scrie in fisier
        """
        with open(self.__filename, 'w') as f:
            for shop in shops:
                shop_string = str(shop.getId()) + ';' + str(shop.getName()) + ';' + str(
                    shop.getLocation()) + '\n'
                f.write(shop_string)

    def store(self, shop):
        all_shops = self.__load_from_file()
        if shop in all_shops:
            raise DuplicateIDException()
        all_shops.append(shop)
        self.__save_to_file(all_shops)

    def __find_by_index(self, shops_list, id):
        index = -1
        for i, c in enumerate(shops_list):
            if c.getId() == id:
                index = i
        return index

    def delete(self, id):
        all_shops = self.__load_from_file()
        poz_to_delete = self.__find_by_index(all_shops, id)
        if poz_to_delete == -1:
            raise ShopNotFoundException()

        del_shop = all_shops.pop(poz_to_delete)
        self.__save_to_file(all_shops)
        return del_shop

    def find(self, id):
        all_shops = self.__load_from_file()
        for shop in all_shops:
            if shop.getId() == id:
                return shop
        return None

    def update(self, id, new_shop):
        all_shops = self.__load_from_file()
        poz = self.__find_by_index(all_shops, id)
        if poz == -1:
            raise ShopNotFoundException()

        all_shops[poz] = new_shop
        self.__save_to_file(all_shops)
        return new_shop

    def get_all(self):
        return self.__load_from_file()

    def size(self):
        return len(self.__load_from_file())

    def delete_all(self):
        self.__save_to_file([])

# TO DO: add tests
