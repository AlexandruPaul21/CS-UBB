class Show:
    """
    Clasa care contine toate informatiile despre show, pentru a asigura high coesion
    """
    def __init__(self,title,artist,gen,time):
        """
        Metoda constructor a clasei
        :param title: tilul showului
        :param artist: artistul showului
        :param gen: genul showului
        :param time: durata showului
        """
        self.__title=title
        self.__artist=artist
        self.__gen=gen
        self.__time=time

    """
    Getter and setters
    """
    def getTitle(self):
        return self.__title
    def getArtist(self):
        return self.__artist
    def getGen(self):
        return self.__gen
    def getTime(self):
        return self.__time

    def setTitle(self,value):
        self.__title=value
    def setArtist(self,value):
        self.__artist=value
    def setGen(self,value):
        self.__gen=value
    def setTime(self,value):
        self.__time=value

    def __repr__(self):
        """
        Se supra-scrie metoda repr pentru a facilita identificarea eventualelor erori
        :return:
        """
        return "Titlu: "+str(self.__title)+" Autor: "+str(self.__artist)+" Gen: "+str(self.__gen)+" Durata: "+str(self.__time)
