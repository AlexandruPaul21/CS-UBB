class bug:
    def __init__(self,id,affected,desc,priority):
        """
        Functia constructor a clasei bug
        :param id: id-ul bugului
        :param affected: ce comp afecteaza
        :param desc: descrierea bugului
        :param priority: prioritatea sa
        """
        self.__id=id
        self.__affected=affected
        self.__desc=desc
        self.__priority=priority

    """
    Getters and setters for bug class
    """

    def getId(self):
        return self.__id
    def getAff(self):
        return self.__affected
    def getDesc(self):
        return self.__desc
    def getPrior(self):
        return self.__priority

    def setId(self,value):
        self.__id=value
    def setAff(self,value):
        self.__affected=value
    def setDesc(self,value):
        self.__desc=value
    def setPrior(self,value):
        self.__priority=value

    def __repr__(self):
        return str(self.__id)+' '+str(self.__affected)+' '+str(self.__desc)+' '+str(self.__priority)
#teste
def test_create_bug():
    bg=bug(1,"frontend","face urat",8)

    assert bg.getId()==1
    assert bg.getAff()=="frontend"
    assert bg.getDesc()=="face urat"
    assert bg.getPrior()==8
