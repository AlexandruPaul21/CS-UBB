class ProductPrice:
    def __init__(self, product_id, shop_id, price):
        # fields: nume produs, nume magazin, locatie, pret
        self.__product_id = product_id
        self.__shop_id = shop_id
        self.__product_name = None
        self.__shop_name = None
        self.__shop_location = None
        self.__price = price

    def getProductId(self):
        return self.__product_id

    def getShopId(self):
        return self.__shop_id

    def getNumeProdus(self):
        return self.__product_name

    def getNumeMagazin(self):
        return self.__shop_name

    def getLocatieMagazin(self):
        return self.__shop_location

    def getPret(self):
        return self.__price

    def setNumeProdus(self, value):
        self.__product_name = value

    def setNumeMagazin(self, value):
        self.__shop_name = value

    def setLocatieMagazin(self, value):
        self.__shop_location = value

    def setPret(self, value):
        self.__price = value


class ShopProducts:
    def __init__(self, shop_id):
        # fields: nume magazin, locatie, numar produse
        self.__shop_id = shop_id
        self.__shop_name = None
        self.__shop_location = None
        self.__no_products = 1

    def getShopId(self):
        return self.__shop_id

    def getNumeMagazin(self):
        return self.__shop_name

    def getLocatieMagazin(self):
        return self.__shop_location

    def getNumarProduse(self):
        return self.__no_products

    def setNumeMagazin(self, value):
        self.__shop_name = value

    def setLocatieMagazin(self, value):
        self.__shop_location = value

    def setNumarProduse(self, value):
        self.__no_products = value

    def increaseNoProducts(self):
        self.__no_products += 1
