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
        self.__locatie = value

    def setNume(self, value):
        self.__nume = value

    def __eq__(self, other):
        if self.__id == other.__id:
            return True
        else:
            return False

    def __str__(self):
        return 'ID: ' + str(self.__id) + ';Denumire: ' + str(self.__nume) + ';Locatie: ' + str(
            self.__locatie)



class SaleItem:
    def __init__(self, product_id, shop_id, pret, stoc):
        self.__product_id = product_id
        self.__shop_id = shop_id
        self.__pret = pret
        self.__stoc = stoc

    def getProductId(self):
        return self.__product_id

    def getShopId(self):
        return self.__shop_id

    def getPret(self):
        return self.__pret

    def getStoc(self):
        return self.__stoc

    def setProductId(self, value):
        self.__product_id = value

    def setShopId(self, value):
        self.__shop_id = value

    def setPret(self, value):
        self.__pret = value

    def setStoc(self, value):
        self.__stoc = value

    def __eq__(self, other):
        if self.__product_id == other.__product_id and self.__shop_id == other.__shop_id:
            return True
        return False

    def __str__(self):
        return 'Product: [' + str(self.__product_id) + '] Shop: [' + str(self.__shop_id) + '] Pret: ' + str(
            self.__pret) + ' Unitati stoc: ' + str(self.__stoc)
