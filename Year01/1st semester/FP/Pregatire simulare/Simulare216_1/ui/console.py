from exceptions.heli_exceptions import HeliNotFoundException
from termcolor import colored

class Console:
    def __init__(self,srv):
        self.__srv=srv

    def __get_report_for_use(self):
        """
        Functia va realiza prima cerinta, realizand un raport cu elicopterele care se folosesc in anumite scopuri
        """
        try:
            util=input("Introduceti scopul dorit: ")
            heli_s=self.__srv.get_for_scope(util)
            for heli in heli_s:
                print(heli)
        except HeliNotFoundException as he:
            print(colored(he,"red"))

    def __get_report_for_years(self):
        """
        Functia va realiza un raport in care fiecarui scop ii vor fi atribuite anii in care au fost produse elicoptere
        ce indeplinesc acest scop
        """
        try:
            heli_dict=self.__srv.get_years_for_scope();
            for keys,elem in heli_dict.items():
                print(keys,end=": ")
                first=True
                for year in elem:
                    if not(first):
                        print(",",end="")
                    first=False
                    print(year,end="")
                print(" ")
        except:
            print(colored("A aparut o eroare","red"))

    def show_ui(self):
        """
        Functia afiseaza meniul principal al aplicatiei
        :return:
        """
        end=False
        while not(end):
            print(colored("Bine ati venit","blue"))
            print("Folosind elicopterele din memorie puteti alege urmatoarele operatii:")
            print(colored("1.","magenta"),"Afisarea tuturor elicopterelor ce realizeaza un anumit scop")
            print(colored("2.","magenta"),"Afisarea anilor de fabricatie grupat dupa scopuri")
            print(colored("0.","magenta"),"Exit")
            op=input(colored("Optiunea dvs este(1,2 sau 0): ","green"))
            if op=="1":
                self.__get_report_for_use()
            elif op=="2":
                self.__get_report_for_years()
            elif op=="0":
                end=True
            else:
                print(colored("Comanda invalida","red"))