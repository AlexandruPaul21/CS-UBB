import random

from termcolor import colored
from utils.utils import bubble_sort,shell_sort

def cmp_name(elem):
    """
    Functie de comparare cand aceasta se face dupa nume
    :param the_list: lista
    :return: primul element, aferent numelui
    """
    return elem[0]

def cmp_movies(elem):
    """
    Functie de comparare cand aceasta se realizeaza dupa nr filme
    :param elem: list
    :return: al doilea element, nr de filme inchiriate
    """
    return elem[1]

class console:
    def __init__(self,srv,srv1,srv2):
        """
        Initializam consola pentru a putea lucra cu controllerul GRASP
        :param srv: service-ul
        """
        self.__srv=srv
        self.__srv1=srv1
        self.__srv2=srv2
        console.id=1
        console.id1=1

    #movie zone
    def __add_movie(self):
        """
        Adauga un film nou
        :raises: ValueError if the addition is not realized succesful
        """
        name=input("Introduceti numele filmului: ")
        desc=input("Introduceti descrierea: ")
        gen=input("Introduceti genul filmului: ")
        try:
            film=self.__srv.insert_movie(console.id,name,desc,gen)
            print(colored("Film adaugat cu succes cu id-ul "+str(console.id),"green"))
            console.id+=1
        except ValueError as ve:
            print(colored(ve,"red"))

    def __delete_movie(self):
        """
        Sterge filmul identidicat de id
        """
        try:
            id_m=int(input("Introduceti id-ul filmului de sters: "))
            if id_m>console.id:
                raise ValueError("Id-ul nu este valid")
            self.__srv.erase_movie(id_m)
            print(colored("Filmul a fost sters cu succes","green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    def __modify_movie(self):
        """
        Modifica filmul al carui id este introdus
        """
        try:
            id_m=int(input("Introduceti id-ul filmului de sters: "))
            if id_m>console.id:
                raise ValueError("Id-ul nu este valid")
            name=input("Introduceti noul nume filmului: ")
            desc=input("Introduceti noua descriere: ")
            gen=input("Introduceti noul gen al filmului: ")
            self.__srv.modify_film(id_m,name,desc,gen)
            print(colored("Film modificat cu succes","green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    def __search_id_movie(self):
        """
        Cauta un film dupa id
        """
        try:
            id=int(input("Introduceti id-ul de cautare: "))
            movie=self.__srv.search_by_id(id)
            return movie
        except ValueError as ve:
            print(colored(ve,"red"))

    def __search_param_movie(self):
        """
        Cauta un film dupa descriere
        """
        try:
            title=desc=gen=""
            title=input("Introduceti titlul: ")
            desc=input("Introduceti descrierea: ")
            gen=input("Introduceti genul: ")
            movie=self.__srv.search_by_param(title,desc,gen)
            return movie
        except ValueError as ve:
            print(colored(ve,"red"))

    def __rand_movie(self):
        try:
            number=random.randint(1,10)
            self.__srv.add_random_movies(number)
            st="Au fost adaugate "+str(number)+" filme"
            console.id+=number
            print(colored(st,"green"))
        except ValueError as ve:
            print(colored("Operatiunea a esuat","red"))

    #client zone
    def __add_client(self):
        """
        Introducem un client nou
        :raises: ValueError if the addition is not realized succesful
        """
        name=input("Introduceti numele: ")
        CNP=input("Introduceti CNP-ul: ")
        try:
            self.__srv1.insert_client(console.id1,name,CNP)
            console.id1+=1
            print(colored("Client adaugat cu succes cu id-ul "+str(console.id1-1),"green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    def __delete_client(self):
        """
        Realizeaza stergerea unui client
        """
        try:
            id_m=int(input("Introduceti id-ul clientului de sters: "))
            if id_m>console.id1:
                raise ValueError("Id-ul nu este valid")
            self.__srv1.erase_client(id_m)
            print(colored("Clientul a fost sters cu succes","green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    def __modify_client(self):
        """
        Modifica clientul dupa noile valori introduse de utilizator
        """
        try:
            id_m=int(input("Introduceti id-ul clientului de modificat: "))
            if id_m>console.id1:
                raise ValueError("Id-ul nu este valid")
            name=input("Introduceti noul nume : ")
            CNP=input("Introduceti noul CNP: ")
            self.__srv1.modify_customer(id_m,name,CNP)
            print(colored("Client modificat cu succes","green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    def __search_id_client(self):
        """
        Cauta un client dupa id
        """
        try:
            id=int(input("Introduceti id-ul de cautare: "))
            customer=self.__srv1.search_by_id(id)
            return customer
        except ValueError as ve:
            print(colored(ve,"red"))

    def __search_param_client(self):
        """
        Cauta un client dupa descriere
        """
        try:
            name=CNP=""
            name=input("Introduceti numele: ")
            CNP=input("Introduceti CNP-ul: ")
            customer=self.__srv1.search_by_param(name,CNP)
            return customer
        except ValueError as ve:
            print(colored(ve,"red"))

    def __rand_client(self):
        try:
            number=random.randint(1,10)
            self.__srv1.add_random_clients(number)
            st="Au fost adaugati "+str(number)+" clienti"
            console.id1+=number
            print(colored(st,"green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    def __show_all(self):
        return self.__srv1.get_all()

    #rent zone

    def __rent(self):
        """
        Functia implementeaza interfata cu utilizatorul pentru inchiriere
        """
        try:
            id1=int(input("Introduceti id-ul clientului: "))
            self.__srv1.search_id(id1)
            customer=self.__srv1.search_by_id(id1)
            print("Bine ai venit, "+str(customer.getName()))
            print("Acestea sunt filmele disponibile:")
            for elem in self.__srv.get_all():
                print(elem)
            id2=int(input("Introduceti id-ul filmului: "))
            self.__srv.search_id(id2)
            self.__srv2.add_rent_op(id1,id2)
            print(colored("Filmul a fost inchiriat cu succes","green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    def __return(self):
        """
        Functia implementeaza interfata cu utilizatorul pentru funcita de returnare
        :param id1:id-ul clientului
        :param id2:id-ul filmului
        """
        try:
            id1=int(input("Introduceti id-ul clientului: "))
            self.__srv1.search_id(id1)
            customer=self.__srv1.search_by_id(id1)
            print("Bine ai venit, "+str(customer.getName()))
            print("Acestea sunt filmele inchiriate de dvs:")
            movies=self.__srv2.get_all_for_id(id1)
            for elem in movies:
                movie=self.__srv.search_by_id(elem)
                print(movie)
            id2=int(input("Introduceti id-ul filmului de returnat: "))
            self.__srv.search_id(id2)
            self.__srv2.remove_rent_op(id1,id2)
            print(colored("Filmul a fost returnat cu succes","green"))
        except ValueError as ve:
            print(colored(ve,"red"))

    #reports
    def __report_client_nr(self):
        """
        Genereaza raportul clienti cu filme inchiriate, ordonat dupa nume
        """
        client_list=[]
        customers_freq=self.__srv2.get_list_with_ids()
        for index in range(len(customers_freq)):
            if customers_freq[index]!=0:
                customer=self.__srv1.search_by_id(index)
                client_list.append([customer.getName(),customers_freq[index]])

        client_list.sort(key=cmp_name)
        for elem in client_list:
            print(colored("Nume","magenta"),end=" ")
            print(elem[0],end="  ")
            print(colored("filme inchiriate: ","magenta"),end="")
            print(elem[1])

    def __report_movies_nr(self):
        """
        Genereaza raportul clienti cu filme inchiriate, ordonat dupa nr de filme inchiriate crescator
        """
        client_list=[]
        customers_freq=self.__srv2.get_list_with_ids()
        for index in range(len(customers_freq)):
            if customers_freq[index]!=0:
                customer=self.__srv1.search_by_id(index)
                client_list.append([customer.getName(),customers_freq[index]])

        client_list.sort(key=cmp_movies)
        for elem in client_list:
            print(colored("Nume","magenta"),end=" ")
            print(elem[0],end="  ")
            print(colored("filme inchiriate: ","magenta"),end="")
            print(elem[1])

    def __report_movies_top(self):
        """
        Genereaza raportul clienti cu filme inchiriate, ordonat dupa nr de filme inchiriate descrescator
        """
        client_list=[]
        customers_freq=self.__srv2.get_list_with_ids()
        for index in range(len(customers_freq)):
            if customers_freq[index]!=0:
                customer=self.__srv1.search_by_id(index)
                client_list.append([customer.getName(),customers_freq[index]])
        bubble_sort(client_list,key=cmp_movies,reverse=True)
        for elem in client_list:
            print(colored("Nume","magenta"),end=" ")
            print(elem[0],end="  ")
            print(colored("filme inchiriate: ","magenta"),end="")
            print(elem[1])

    def __report_movies_30(self):
        """
        Genereaza raportul clienti cu filme inchiriate, ordonat dupa nr de filme inchiriate descrescator,primii 30%
        """
        client_list=[]
        customers_freq=self.__srv2.get_list_with_ids()
        for index in range(len(customers_freq)):
            if customers_freq[index]!=0:
                customer=self.__srv1.search_by_id(index)
                client_list.append([customer.getName(),customers_freq[index]])

        index=len(self.__srv1.get_all())
        index*=3
        index//=10
        ix=0
        shell_sort(client_list,key=cmp_movies,reverse=True)
        for elem in client_list:
            if ix>index:
                break
            else:
                ix+=1
            print(colored("Nume","magenta"),end=" ")
            print(elem[0],end="  ")
            print(colored("filme inchiriate: ","magenta"),end="")
            print(elem[1])

    def __report_special(self):
        """
        Genereaz tipul special de raport
        """
        #determinam cel mai inchiriat film
        customers=self.__srv2.get_list_with_id_m()

        customers_freq=self.__srv2.get_list_with_ids()

        client_list=[]
        for elem in customers:
            customer=self.__srv1.search_by_id(elem)
            client_list.append([customer.getName(),customers_freq[elem]])

        for elem in client_list:
            print(colored("Nume","magenta"),end=" ")
            print(elem[0],end="  ")
            print(colored("filme inchiriate: ","magenta"),end="")
            print(elem[1])



    def show_ui(self):
        end=False
        while not(end):
            print(colored("Comenzi disponbibile","cyan")+": film_nou, client_nou, sterge_film, sterge_client, modifica_film, modifica_client, exit")
            print("cauta_film, cauta_client, inchiriaza_film, returneaza_film")
            print(colored("Comenzi rapoarte: ","cyan"))
            print("1.Raport clienți cu filme închiriate ordonat dupa nume = raport_filme_nume")
            print("2.Raport clienți cu filme închiriate ordonat dupa numărul de filme închiriate = raport_filme_nr")
            print("3.Raport cele mai închiriate filme = raport_top_filme")
            print("4.Raport primii 30% clienți cu cele mai multe filme = raport_30")
            print("5.Raport numele persoanelor cu nr de filme inchiriate, care au inchiriat, cel mai inchiriat film = raport_special")
            command=input("Comanda este: ")
            command.lower().strip()
            if command=="film_nou":
                self.__add_movie()
            elif command=="sterge_film":
                self.__delete_movie()
            elif command=="modifica_film":
                self.__modify_movie()
            elif command=="client_nou":
                self.__add_client()
            elif command=="sterge_client":
                self.__delete_client()
            elif command=="modifica_client":
                self.__modify_client()
            elif command=="cauta_film":
                print("Comenzi disponibile cautare: id, descriere")
                secondary_command=input("Introduceti comanada: ")
                if secondary_command=="id":
                    movie=self.__search_id_movie()
                    print(movie)
                elif secondary_command=="descriere":
                    movie=self.__search_param_movie()
                    print(movie)
                else:
                    print(colored("Comanda invalida", "red"))
            elif command=="cauta_client":
                print("Comenzi disponibile cautare: id, descriere")
                secondary_command=input("Introduceti comanada: ")
                if secondary_command=="id":
                    customer=self.__search_id_client()
                    print(customer)
                elif secondary_command=="descriere":
                    customer=self.__search_param_client()
                    print(customer)
                else:
                    print(colored("Comanda invalida", "red"))
            elif command=="inchiriaza_film":
                self.__rent()
            elif command=="returneaza_film":
                self.__return()
            elif command=="clienti_random":
                self.__rand_client()
            elif command=="filme_random":
                self.__rand_movie()
            elif command=="raport_filme_nume":
                self.__report_client_nr()
            elif command=="raport_filme_nr":
                self.__report_movies_nr()
            elif command=="raport_top_filme":
                self.__report_movies_top()
            elif command=="raport_30":
                self.__report_movies_30()
            elif command=="raport_special":
                self.__report_special()
            elif command=="exit":
                end=True
            else:
                print(colored("Comanda invalida","red"))
