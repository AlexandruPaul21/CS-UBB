from termcolor import colored

class movie:
    def __init__(self,id,title,desc,gen):
        """
        Constructorul clasei
        :param id: id-ul filmului, nu se poate modifica si nici nu se actualizeaza
        :type: int, primary key
        :param title: titulul filmului
        :type strig
        :param desc: descrierea filmului
        :param gen: genul filmului
        """
        self.__id=id
        self.__title=title
        self.__desc=desc
        self.__gen=gen

    """
    Metode de tip get si set pentru clasa movie
    """
    def getId(self):
        return self.__id
    def getTitle(self):
        return self.__title
    def getDesc(self):
        return self.__desc
    def getGen(self):
        return self.__gen

    def setTitle(self,value):
        self.__title=value
    def setDesc(self,value):
        self.__desc=value
    def setGen(self,value):
        self.__gen=value

    def __repr__(self):
        return colored("Id: ","magenta")+str(self.__id)+colored(" Titlu: ","magenta")+str(self.__title)+colored(" Descriere: ","magenta")+str(self.__desc)+colored(" Gen: ","magenta")+str(self.__gen)

    def __eq__(self, other):
        return self.__gen==other.__gen and self.__desc==other.__desc and self.__title==other.__title

class client:
    def __init__(self,id,name,cnp):
        """
        Constructorul clasei
        :param id: id-ul unic idenficat al clientului nu se poate actualiza si nu se modifica
        :param name: numele clientului
        :param cnp: codul numeric personal
        """

        self.__id=id
        self.__name=name
        self.__cnp=cnp

    """
    Metode de tip get si set pentru clasa client
    """
    def getId(self):
        return self.__id
    def getName(self):
        return self.__name
    def getCNP(self):
        return self.__cnp

    def setId(self,value):
        self.__id=value
    def setName(self,value):
        self.__name=value
    def setCNP(self,value):
        self.__cnp=value

    def __repr__(self):
        return colored("Nume: ","magenta")+str(self.__name)+colored(" CNP: ","magenta")+str(self.__cnp)

    def __eq__(self, other):
        return self.__name==other.__name and self.__cnp==other.__cnp
