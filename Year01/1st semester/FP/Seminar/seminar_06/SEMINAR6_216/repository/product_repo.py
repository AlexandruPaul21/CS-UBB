class InMemoryRepository:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de produse (i.e. sa ofere un depozit persistent pentru obiecte
        de tip Product)

        Detaliere: seminar 7

        """

    def __init__(self):
        # products - multimea de produse pe care o gestionam
        # poate fi si dictionar, este la latitudinea noastra cum stocam datele
        # e.g. stocare in dict cu un camp in plus id_produs (dar se poate lua denumire ca si cheie
        # de ex, daca am sti ca e unic):

        # {id_produs1: produs1, id_produs2: produs2, id_produs3: produs3}
        # vs. lista: [produs1, produs2, produs3]

        self.__products = []

    def store(self, product):
        """
        Adauga produs in lista
        :param product: produsul de adaugat
        :type product: Product
        :return: -; lista de produse se modifica prin adaugarea produsului dat
        :rtype:
        :raises:
        """
        self.__products.append(product)

    # adauga in lista
    # sterge din lista
    # modifica element din list
    # cauta element in lista

    def get_all_products(self):
        """
        Returneaza o lista cu toate produsele disponibile
        :rtype: list of Product objects
        """
        return self.__products


def test_store():
    pass


def test_get_all_products():
    pass
