from termcolor import colored
from domain.entities import bug
from erorros.creat_erorrs import NotFindBug

class console:
    """
    Clasa va reprezenta interfata de tip consola
    """
    def __init__(self,srv):
        """
        Vom initializa clasa cu un service
        :param srv:
        """
        self.__srv=srv

    def __solve1(self):
        """
        Se rezolva prima cerinta
        """
        try:
            desc=input("Introduceti descrierea bug-ului: ")
            rez=self.__srv.find_bugs_for_desc(desc)
            for bg in rez:
                print(bg.getId(),bg.getAff(),bg.getDesc(),bg.getPrior(),sep=',')
        except NotFindBug as e:
            print(colored(e,"red"))

    def __solve2(self):
        """
        Se rezolva cerinta 2
        """
        rez=self.__srv.bugs_per_component()
        for keys,values in rez.items():
            print(keys,end=":")
            print(values)

    def show_ui(self):
        """
        Functia afiseaza meniul principal de interactiune cu utilizatorul
        """
        End=False
        while not(End):
            print(colored("Bine ati venit!","blue"))
            print("Comenzi disponibile:")
            print("1.Afișarea tuturor bugurilor care conțin în descriere un șir de caractere citit de la tastatură,ordonate descrescător după prioritate")
            print("2.Pentru fiecare componentă afectată, afișați media priorităților bugurilor aferente")
            print("0.Exit")
            op=input("Comanda dvs: ")

            if op=="1":
                self.__solve1()
            elif op=="2":
                self.__solve2()
            elif op=="0":
                End=True
            else:
                print(colored("Comanda invalida","red"))