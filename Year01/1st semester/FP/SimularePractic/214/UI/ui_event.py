from exceptions.event_exceptions import InvalidEventException
class UIConsole:
    """
    Clasa se foloseste pentru interfata cu utilizatorul
    """
    def __init__(self,srv):
        """
        Mteoda constructor, da clasei Console un service
        :param srv: service-ul
        """
        self.__srv=srv

    def __init_all_today(self):
        """
        Identifica evenimentele ce vor avea loc azi
        """
        for ev in self.__srv.init_sorted_today():
            print(ev)

    def __add_event(self):
        """
        Adauga un event in lista de evenimente
        :raises: InvalidEventException daca nu sunt furnizate date valide
        """
        try:
            print("Format data: dd.mm.yyyy, format ora: mm:hh")
            date=input("Introduceti data: ")
            time=input("Introduceti ora: ")
            desc=input("Introduceti descrierea: ")
            self.__srv.add_new_event(date,time,desc)
            print("Evenimentul a fost adaugat cu succes in agenda")
        except InvalidEventException as ex:
            print(ex)

    def __view_events_by_date(self):
        """
        Functia afiseaza toate evenimentele ce vor avea loc intr-o data oarecare data de user
        """
        try:
            print("Format data: dd.mm.yyyy")
            date=input("Introduceti data: ")
            day,month,year=date.split(".")
            events=self.__srv.find_by_date(day,month,year)
            print("Evenimente ce au loc in "+str(date)+":")
            for ev in events:
                print(ev)
        except:
            print("Ceva nu a functionat, incercati din nou")

    def __export(self):
        """
        Functia implementeaza functionalitatea 4, cea de export
        """
        #try:
        file=input("Introduceti numele fisierului: ")
        text=input("Introduceti textul de cautat: ")
        events=self.__srv.find_by_desc(text)
        filename="data/"+str(file)
        with open(filename,"w") as f:
            for ev in events:
                str_ev=str("Data: ")+str(ev.getDate())+str(" Ora: ")+str(ev.getTime())+str(" Desc: ")+str(ev.getDesc())+"\n"
                f.write(str_ev)
        #except:
         #   print("Ceva nu a mers bine, incercati din nou")

    def show_ui(self):
        """
        Functia implementeaza meniul princpal de interactiune cu utilizatorul
        """
        print("Evenimente ce vor avea loc azi:")
        self.__init_all_today()
        out=False
        while not out:
            print("Comnezi disponibile: add, view_date, export, exit, help")
            cmd=input("Comanda: ")
            cmd=cmd.strip()
            if cmd=="add":
                self.__add_event()
            elif cmd=="view_date":
                self.__view_events_by_date()
            elif cmd=="export":
                self.__export()
            elif cmd=="exit":
                out=True
            else:
                print("Comanda invalida!")


