class Product:

    def __init__(self, id, denumire, tara_origine):
        self.__id = id
        self.__denumire = denumire
        self.__tara_origine = tara_origine

    def getId(self):
        return self.__id

    def getName(self):
        return self.__denumire

    def getCountry(self):
        return self.__tara_origine

    def setName(self, value):
        self.__denumire = value

    def setCountry(self, value):
        self.__tara_origine = value

    def setId(self, value):
        self.__id = value

    def __eq__(self, other):
        """
        Verifica egalitatea
        :param other: produsul cu care se compara produsul curent
        :type other: Product
        :return: True daca produsele sunt identice (au acelasi id), False altfel
        :rtype: bool
        """
        if self.__id == other.getId():
            return True
        return False

    def __str__(self):
        return 'ID :' + str(self.__id) + ';Denumire: ' + str(self.__denumire) + ';Tara origine: ' + str(
            self.__tara_origine)


def test_create_product():
    p = Product('1', 'jeleuri', 'Spania')
    assert (p.getName() == 'jeleuri')
    assert (p.getCountry() == 'Spania')

    p.setName('jeleuri Haribo')
    assert (p.getName() == 'jeleuri Haribo')
    p.setCountry('Germania')

    assert (p.getCountry() == 'Germania')


def test_equal_products():
    p1 = Product('1', 'jeleuri', 'Germania')
    p2 = Product('1', 'jeleuri', 'Germania')
    assert (p1 == p2)

    p3 = Product('2', 'jeleuri', 'Spania')
    assert (p1 != p3)


class Shop:
    def __init__(self, id, nume, locatie):
        self.__id = id
        self.__nume = nume
        self.__locatie = locatie

    def getId(self):
        return self.__id

    def getName(self):
        return self.__nume

    def getLocation(self):
        return self.__locatie

    def setId(self, value):
        self.__id = value

    def setLocatie(self, value):
        self.__nume = value

    def setLocatie(self, value):
        self.__locatie = value

    def __eq__(self, other):
        if self.__id == other.__id:
            return True
        else:
            return False

    def __str__(self):
        return 'ID: ' + str(self.__id) + ';Denumire: ' + str(self.__nume) + ';Locatie: ' + str(
            self.__locatie)


def test_create_shop():
    pass


def test_equal_shop():
    pass


class SaleItem:
    def __init__(self, product, shop, pret, stoc):
        self.__product = product
        self.__shop = shop
        self.__pret = pret
        self.__stoc = stoc

    def getProduct(self):
        return self.__product

    def getShop(self):
        return self.__shop

    def getPret(self):
        return self.__pret

    def getStoc(self):
        return self.__stoc

    def setProduct(self, value):
        self.__product = value

    def setShop(self, value):
        self.__shop = value

    def setPret(self, value):
        self.__pret = value

    def setStoc(self, value):
        self.__stoc = value

    def __eq__(self, other):
        if self.__product.getId() == other.__product.getId() and self.__shop.getId() == other.__shop.getId():
            return True
        return False

    def __str__(self):
        return 'Product: [' + str(self.__product.getName()) + '; ' + str(
            self.__product.getCountry()) + '] Shop: [' + str(self.__shop.getName()) + '; ' + str(
            self.__shop.getLocation()) + '] Pret: ' + str(self.__pret) + ' Unitati stoc: ' + str(self.__stoc)


test_create_product()
test_equal_products()


def test_create_sale_item():
    p1 = Product('1', 'jeleuri', 'Germania')
    s1 = Shop('1', 'Shop 1', 'Locatie 1')

    sale_item = SaleItem(p1, s1, 34.4, 20)
    assert (sale_item.getProduct() == p1)
    assert (sale_item.getShop() == s1)
    assert (sale_item.getPret() == 34.4)
    assert (sale_item.getStoc() == 20)


def test_equal_sale_item():
    p1 = Product('1', 'jeleuri', 'Germania')
    s1 = Shop('1', 'Shop 1', 'Locatie 1')

    sale_item1 = SaleItem(p1, s1, 34.4, 20)
    sale_item2 = SaleItem(p1, s1, 35.5, 210)

    assert (sale_item1 == sale_item2)

    p2 = Product('2', 'jeleuri Haribo', 'Germania')
    sale_item3 = SaleItem(p2, s1, 10.3, 34)
    assert (sale_item3 != sale_item2)
