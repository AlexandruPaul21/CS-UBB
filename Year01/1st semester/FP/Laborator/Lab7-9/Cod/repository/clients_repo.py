import random
import string

from domain.entities import client

def search_customer(the_list,id,n):
    if n<0:
        raise ValueError("Clientul nu a fost gasit")
    if the_list[n-1].getId()==id:
        return the_list[n-1]
    return search_customer(the_list,id,n-1)

class ClientsInMemoryRepo:
    def __init__(self):
        """
        Initializam lista de elemente ca fiind vida
        """
        self.__clients=[]
        ClientsInMemoryRepo.id=0

    def add_client(self,new_client):
        """
        Adauga un client
        :param new_client: noul client de adaugat
        """
        if new_client.getId()==0:
            for index in range(len(self.__clients)):
                if index+1!=self.__clients[index].getId():
                    ClientsInMemoryRepo.id=index+1
                    break
                if index==len(self.__clients):
                    ClientsInMemoryRepo.id=index+1
            new_client.setId(ClientsInMemoryRepo.id)
        customer=client(new_client.getId(),new_client.getName(),new_client.getCNP())
        self.__clients.append(customer)

    def delete_client(self,id):
        """
        Sterge un client
        :param id: id-ul clietnului de sters
        """
        new_clients_list=[]
        for customer in self.__clients:
            if customer.getId()!=id:
                new_clients_list.append(customer)
        self.__clients=new_clients_list

    def modify_client(self,id,new_name,new_cnp):
        """
        Modifica datele unui client
        :param id: id-ul clientului de modificat
        :param new_name: noul nume
        :param new_cnp:  noul cnp
        """
        new_clients_list=[]
        for customer in self.__clients:
            if customer.getId()==id:
                customer.setName(new_name)
                customer.setCNP(new_cnp)
            new_clients_list.append(customer)
        self.__clients=new_clients_list

    def search_client_by_id(self,id):
        """
        Cauta in lista de clienti, un client dupa id
        :param id: id-ul clientului
        :return: clientul gasit
        :raises: ValueError daca nu gasim id-ul
        """
        return search_customer(self.__clients,id,len(self.__clients))
        # for customer in self.__clients:
        #     if customer.getId()==id:
        #         return customer
        # raise ValueError("Id invalid")

    def search_client_by_param(self,name,CNP):
        """
        Cauta in lista de clienti dupa parametrii
        :param name: numele clientului
        :param CNP: CNP-ul clientului
        :return: clientul gasit
        :raise: ValueError daca clientul nu a fost gasit
        """
        for customer in self.__clients:
            if customer.getName()==name or customer.getCNP()==CNP:
                return customer
        raise ValueError("Clientul nu a fost gasit")

    def searchID(self,id):
        """
        Cauta dupa daca id-ul este assignat unui client
        :param id: id-ul filmului
        :return: True sau False
        """
        ok=1
        for elem in self.__clients:
            if elem.getId()==id:
                ok=0
        if ok:
            raise ValueError("Nu exista clientulu cu id-ul respectiv")

    def get_all_clients(self):
        """
        Returneaza intreaga lista de clienti
        """
        return self.__clients

    def __eq__(self, other):
        if self.__clients==other.__clients:
            return True
        return False

class ClientsFileRepo(ClientsInMemoryRepo):
    def __init__(self,filename):
        """
        Initializeaza repo-ul cu numele fisierului
        :param filename:
        """
        ClientsInMemoryRepo.__init__(self)
        self.__filename=filename
        self.__load_from_file()

    def __load_from_file(self):
        """
        Functia citeste datele din fisier
        """
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            for line in lines:
                if line=="\n":
                    break
                client_id, client_name, client_CNP=[token.strip() for token in line.split(';')]
                customer=client(client_id,client_name,client_CNP)
                ClientsInMemoryRepo.add_client(self,customer)

    def __save_in_file(self):
        """
        Functia scrie in fisier
        """
        with open(self.__filename,"w") as f:
            customers=ClientsInMemoryRepo.get_all_clients(self)
            for customer in customers:
                value=str(customer.getId())+";"+str(customer.getName())+";"+str(customer.getCNP())+"\n"
                f.write(value)

    def add_client(self,new_client):
        """
        Functia introduce un nou client
        :param new_client: clientul de introdus
        """
        ClientsInMemoryRepo.add_client(self,new_client)
        self.__save_in_file()

    def delete_client(self,id):
        """
        Functia sterge clientul id-ul dat
        :param id: id-ul clientului
        """
        ClientsInMemoryRepo.delete_client(self,id)
        self.__save_in_file()

    def modify_client(self,id,new_name,new_CNP):
        """
        Functia modifica clientul cu id-ul id si ii atribuie noile caracteristici
        :param id: id-ul clientului
        :param new_name: noul nume
        :param new_CNP: noul CNP
        """
        ClientsInMemoryRepo.modify_client(self,id,new_name,new_CNP)
        self.__save_in_file()

    def search_client_by_id(self,id):
        """
        Suprascrie metoda din in memory
        :return: clientul cautat
        """
        return ClientsInMemoryRepo.search_client_by_id(self,id)

    def search_client_by_param(self,name,CNP):
        """
        Suprascrie metoda din in memory
        :return: clientul cautat
        """
        return ClientsInMemoryRepo.search_client_by_param(self,name,CNP)

    def searchID(self,id):
        """
        Suprascrie metoda din in memory
        :return: clientul cautat
        """
        return ClientsInMemoryRepo.searchID(self,id)

    def get_all_clients(self):
        """
        Suprascrie metoda din in memory
        :return: clientii cautati
        """
        return ClientsInMemoryRepo.get_all_clients(self)

    def __eq__(self, other):
        """
        Suprascrie metoda eq
        """
        return ClientsInMemoryRepo.__eq__(self,other)
