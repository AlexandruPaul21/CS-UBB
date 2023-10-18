class Product:
    #__productCount = 0

    def __init__(self, id, denumire, unitati_stoc, pret):
        """
        Initializeaza un obiect de tip Produs cu valorile date
        :param denumire: denumirea produsului
        :type denumire: str
        :param unitati_stoc: numarul de unitati in stoc
        :type unitati_stoc: int (>0)
        :param pret: pretul produsului
        :type pret: float (>0)
        """

        self.__id = id
        self.__name = denumire
        self.__stoc = unitati_stoc
        self.__pret = pret
        #Product.__productCount += 1

    def getId(self):
        return self.__id

    def getName(self):
        return self.__name

    def getStoc(self):
        return self.__stoc

    def getPret(self):
        return self.__pret

    # @staticmethod
    # def getProductCount():
    #     return Product.__productCount

    def setName(self, value):
        self.__name = value

    def setStoc(self, value):
        self.__stoc = value

    def setPret(self, value):
        self.__pret = value

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
        return "Denumire: " + str(self.__name) + '; Unitati stoc: ' + str(self.__stoc) + '; Pret: ' + str(self.__pret)




def test_create_product():
    p = Product('1', 'jeleuri', 10, 12.5)
    assert (p.getName() == 'jeleuri')
    assert (p.getStoc() == 10)
    assert (p.getPret() == 12.5)

    p.setName('jeleuri Haribo')
    assert (p.getName() == 'jeleuri Haribo')
    p.setStoc(25)
    p.setPret(7.5)

    assert (p.getStoc() == 25)
    assert (p.getPret() == 7.5)


def test_equal_products():
    p1 = Product('1', 'jeleuri', 10, 12.5)
    p2 = Product('1', 'jeleuri', 10, 12.5)
    assert (p1 == p2)

    p3 = Product('2', 'jeleuri', 10, 10.5)
    assert (p1 != p3)


test_create_product()
test_equal_products()
