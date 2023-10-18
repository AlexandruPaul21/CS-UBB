from exceptions.domain_exceptions import DomainException
from exceptions.domain_exceptions import NotFoundException

class Console:
    """
    Clasa implementeaza interfata utilizator
    """
    def __init__(self,srv):
        """
        Clasa constructor, aici se primeste un service
        :param srv: service-ul
        """
        self.__srv=srv

    def __add(self):
        """
        Implementeaza interfata utilizator pentru adaugarea unui specatol
        :return: none
        """
        try:
            title=input("Introduceti titlul: ").strip()
            artist=input("Introduceti artistul: ").strip()
            gen=input("Introduceti genul: ").strip()
            time=input("Introduceti durata in secunde: ").strip()
            time=int(time)
            self.__srv.add_new_show(title,artist,gen,time)
            print("Spectacol adaugat cu succes!")
        except DomainException as e:
            print(e)
        except ValueError as ve:
            print("Durata trebuie sa fie numar intreg")

    def __modify(self):
        """
        Implementeaza interfata utilizator pentru operatia de modificare
        :return: none
        """
        try:
            title=input("Introduceti titlul: ").strip()
            artist=input("Introduceti artistul: ").strip()
            gen=input("Introduceti genul: ").strip()
            time=input("Introduceti durata in secunde: ").strip()
            time=int(time)
            self.__srv.modify_show_service(title,artist,gen,time)
            print("Spectacol modificat cu succes!")
        except DomainException as e:
            print(e)
        except ValueError as ve:
            print("Durata trebuie sa fie numar intreg")
        except NotFoundException as nf:
            print(nf)

    def __export(self):
        """
        Implementeaza interfata utilizator pentru operatia de expoert
        :return: none
        """
        try:
            file=input("Introduceti numele fisierului: ").strip()
            self.__srv.export_srv(file)
            print("Entitati exportate cu succes")
        except:
            print("Ceva nu a mers, incercati din nou")

    def __rand(self):
        """
        Implementeaza interfata utilizator pentru operatia de random
        :return: none
        """
        try:
            nr=int(input("Introduceti numarul de entitati ce doriti a fi adaugate: "))
            added=self.__srv.rand(nr)
            print("Au fost adaugate urmatoarele spectacole:")
            for show in added:
                print(show)
        except:
            print("Ceva nu a mers, incercati din nou!")

    def show_ui(self):
        """
        Meniul principal de comunicare cu utilizatorul
        :return:
        """
        print("Bine ati venit!")
        out=False
        while not out:
            print("Comenzi disponibile: add, modify, export, random, exit, help")
            cmd=input("Comanda dumneavoastra este: ")
            cmd=cmd.strip()
            if cmd=="add":
                self.__add()
            elif cmd=="modify":
                self.__modify()
            elif cmd=="export":
                self.__export()
            elif cmd=="random":
                self.__rand()
            elif cmd=="exit":
                out=True
            elif cmd=="help":
                print("[add] - adauga un spectacool")
                print("[modif] - modifica un spectacol deja existent")
                print("[export] - da export datelor sortate intru-un fisier")
                print("[random] - genereaza random un numar de entitati")
                print("[exit] - iesire din aplicatie")
            else:
                print("Comanda invalida")