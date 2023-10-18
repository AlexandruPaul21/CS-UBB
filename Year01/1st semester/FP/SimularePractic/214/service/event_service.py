import copy
from datetime import date

def comp_between_hours(e1,e2):
    """
    Functie de comparat custom care asigura sortarea corespunzatoare in functie de ora
    :param e1: primul eveniment
    :param e2: al doilea event
    :return: true sau false in functie de cel care incepe mai rapid
    """
    h1,m1=e1.getTime().strip(":")
    h2,m2=e2.getTime().strip(":")
    h1=int(h1)
    h2=int(h2)
    m1=int(m1)
    m2=int(m2)
    if h1==h2:
        return m1>m2
    return h1>h2

class EventService:
    """
    Controllerul GRASP al aplicatiei
    """
    def __init__(self,repo,valid):
        """
        Metoda constructor a clasei
        :param repo: repository-ul
        :param valid: validatorul
        """
        self.__repo=repo
        self.__valid=valid

    def init_sorted_today(self):
        """
        Functia selecteaza evenimentele de astazi si le sorteaza dupa ora de incepere
        :return: lista de evenimente sortate
        :rtype: list
        """
        today=date.today()
        day=today.day
        month=today.month
        year=today.year
        events_today=[]
        for ev in self.__repo.get_all():
            d,m,y=ev.getDate().split(".")
            d=int(d)
            m=int(m)
            y=int(y)
            if y==year and m==month and d==day:
                events_today.append(ev)

        events_today.sort(key=(lambda x: x.getTime()))
        return events_today


    def add_new_event(self,date,time,desc):
        """
        Adauga un nou event in lista
        :param date: data
        :param time: ora la care se petrece
        :param desc: descrierea lui
        :return: none
        """
        self.__valid.validate(date,time,desc)
        self.__repo.add_ev(date,time,desc)

    def find_by_date(self,day,month,year):
        """
        Gaseste un eveniment dupa ziua in care se desfasoara
        :param day: ziua
        :param month: luna
        :param year: anul
        :return: lista de evenimente
        :rtype:list
        """
        date=str(day)+'.'+str(month)+'.'+str(year)
        time="15:50"
        year="2022"
        self.__valid.validate(date,time,year)
        day=int(day)
        month=int(month)
        year=int(year)
        events_date=[]
        for ev in self.__repo.get_all():
            d,m,y=ev.getDate().split(".")
            d=int(d)
            m=int(m)
            y=int(y)
            if y==year and m==month and d==day:
                events_date.append(ev)

        events_date.sort(key=(lambda x: x.getTime()))
        return events_date

    def find_by_desc(self,text):
        """
        Gaseste evenimentele care au un anumit substring in descriere
        :param text: textul de cautat
        :return: evenimentele care respecta cerinta
        :rtype: list
        """
        events_match=[]
        for ev in self.__repo.get_all():
            desc=ev.getDesc()
            if desc.find(text)!=-1:
                events_match.append(ev)
        return events_match


    def get_all_ev(self):
        """
        Returneaza toate evenimentele
        :return: lista evenimentelor
        :rtype: list
        """
        return self.__repo.get_all()


