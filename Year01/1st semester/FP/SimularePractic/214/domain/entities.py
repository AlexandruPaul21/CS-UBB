class event:
    """
    Clasa care ne va ajuta sa gestionam listele de evenimente
    Clasa genereaza high coesion
    """
    def __init__(self,date,time,desc):
        self.__date=date
        self.__time=time
        self.__desc=desc

    """
    Getters and setter for event objects
    """
    def getDate(self):
        return self.__date
    def getTime(self):
        return self.__time
    def getDesc(self):
        return self.__desc

    def setDate(self,date):
        self.__date=date
    def setTime(self,time):
        self.__time=time
    def setDesc(self,desc):
        self.__desc=desc
    def __repr__(self):
        return str("Data: ")+str(self.__date)+str(" Ora: ")+str(self.__time)+str(" Desc: ")+str(self.__desc)
    def __eq__(self, other):
        return self.__desc==other.getDesc() and self.__date==other.getDate() and self.__time==other.getTime()

