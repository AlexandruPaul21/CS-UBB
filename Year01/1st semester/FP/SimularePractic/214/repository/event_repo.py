from domain.entities import event

class EventInFileRepo:
    """
    Clasa Repo este parte din arhitectura stratificata
    """
    def __init__(self,filename):
        """
        Metoda init primeste parametru fisierul din care se citesc datele
        :param filename:
        """
        self.__repo=[]
        self.__filename=filename
        self.__load_from_file()

    def __load_from_file(self):
        """
        Preia din fisier datele si le stocheaza in memorie
        """
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            for line in lines:
                event_det=line.split(" ")
                date=event_det[0]
                time=event_det[1]
                desc=""
                for elem in event_det[2:]:
                    desc+=elem+" "
                desc=desc.strip()
                e=event(date,time,desc)
                self.__repo.append(e)

    def add_ev(self,date,time,desc):
        """
        Adauaga un eveniment
        :param date: data evenimentului
        :param time: ora evenimentului
        :param desc: descriere evenimentului
        """
        e=event(date,time,desc)
        self.__repo.append(e)

    def get_all(self):
        """
        Retureneaza toatele elementele din repo
        """
        return self.__repo
