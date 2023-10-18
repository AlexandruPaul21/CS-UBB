from domain.entities import Shop


class ShopService:

    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiectul de tip repo care ne ajuta sa gestionam lista de magazine
        :type repo:ShopInMemoryRepo
        :param validator: validatorul pentru verificarea magazinelor
        :type validator: ShopValidator
        """
        self.__repo = repo
        self.__validator = validator

    def add_shop(self, id, nume, locatie):
        """
        Adauga magazin


        :return: magazinul adaugat in lista
        :rtype: Shop
        :raises: ValueError daca magazinul e invalid sau mai exista un magazin cu id-ul dat

        """
        shop = Shop(id, nume, locatie)
        self.__validator.validate(shop)
        self.__repo.store(shop)
        return shop

    def get_all_shops(self):
        """
        Returneaza o lista cu toate magazinele disponibile
        :return: lista de magazine disponibile
        :rtype: list of Shop objects
        """
        return self.__repo.get_all()

    def delete_by_id(self, id):
        """
        Sterge magazin cu id dat
        :param id: id-ul dat
        :type id: str
        :return: magazinul sters
        :rtype: Shop
        :raises: ShopNotFoundException daca nu exista magazin cu id-ul dat
        """
        return self.__repo.delete(id)

    def update_shop(self, id, nume, locatie):
        """
        Modifica magazinul cu id id cu datele date
        :param id: id-ul dat
        :type id: str

        :return: magazinul modificat
        :rtype: Shop
        :raises: ValueError daca nu exista magazin cu id dat
        """
        shop = Shop(id, nume, locatie)
        self.__validator.validate(shop)
        return self.__repo.update(id, shop)
