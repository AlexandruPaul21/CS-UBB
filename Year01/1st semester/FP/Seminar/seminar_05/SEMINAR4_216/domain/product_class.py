from datetime import datetime


class Product:
    def __init__(self, denumire, unitati_stoc, pret, data_exp):
        self.__name = denumire
        self.__stoc = unitati_stoc
        self.__pret = pret
        self.__data_expirare = datetime(data_exp)

    def getName(self):
        return self.__name

    def getStoc(self):
        return self.__stoc

    def getPret(self):
        return self.__pret

    def setName(self,value):
        self.__name = value
    def setStoc(self,value):
        self.__stoc = value
    def setPret(self,value):
        self.__pret = value


# p1 = Product('acadele', 20, 10.4)
try:
    p2 = Product('jeleuri', 15, 11.22, '11/10/2020')
except TypeError:
    print('Data nu este ok. ')
