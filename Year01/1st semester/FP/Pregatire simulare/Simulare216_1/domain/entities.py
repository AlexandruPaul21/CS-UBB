class helicopter:
    def __init__(self,id,name,use,fabrication_year):
        """
        Constructorul clasei
        :param id: id-ul elicopetrului
        :param name: numele elicopterului
        :param use: o lista de utilizari
        :param fabrication_year: anul de fabricatie
        """
        self.__id=id
        self.__name=name
        self.__use=use
        self.__fa_year=fabrication_year

    """
    Gettere si settere penrtu helicopter
    """
    def getId(self):
        return self.__id
    def getName(self):
        return self.__name
    def getUse(self):
        return self.__use
    def getFa_Year(self):
        return self.__fa_year

    def setId(self,value):
        self.__id=value
    def setName(self,value):
        self.__name=value
    def setUse(self,value):
        self.__use=use
    def setFa_Year(self,value):
        self.__fa_year=value

def test_create_heli():
    h=helicopter("1","Panzer",["medical","concediu"],"1978")
    assert h.getId()=="1"
    assert h.getName()=="Panzer"
    assert h.getUse()==["medical","concediu"]
    assert h.getId()=="1978"