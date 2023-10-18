from domain.entities import client,movie
from domain.validators import clientValidator,movieValidator
from repository.clients_repo import ClientsInMemoryRepo
from repository.movies_repo import MovieInMemoryRepo

class RentInMemoryRepo():
    def __init__(self):
        """
        Initializam clasa in care vom adauga filmele
        """
        self.__rents=[]

    def add_rent(self,id1,id2):
        """
        Functia adauga la lista de inchirireri un nou film
        :param id1: id-ul clientului
        :param id2: id-ul filmului
        """
        self.__rents.append((id1,id2))

    def delete_rent(self,id1,id2):
        """
        Functia sterge o inchirere
        :param id1: id-ul clientului
        :param id2: id-ul filmului
        :raises: ValueErorr daca fimul nu a fost inchiriat de clientul respectiv
        """
        index=0
        ok=True
        for index in range(len(self.__rents)):
            if self.__rents[index][0]==id1 and self.__rents[index][1]==id2:
                self.__rents.pop(index)
                ok=False
                break
        if ok:
            raise ValueError("Rent not found")

    def get_list(self):
        """
        Aceasta functie obtine lista cu numarul filmelor inchiriate de fiecare client
        :return: lista cautata
        """
        freq=[0]*100
        for id1,id2 in self.__rents:
            freq[id1]+=1
        return freq

    def get_all_for_max(self):
        freq=[0]*100
        mx=0
        idm=0
        for id1,id2 in self.__rents:
            freq[id2]+=1
            if freq[id2]>mx:
                mx=freq[id2]
                idm=id2
        the_list=[]
        for id1,id2 in self.__rents:
            if id2==idm:
                the_list.append(id1)
        return the_list

    def get_all_id(self,id):
        """
        Functia cauta toate filmele inchiriate de un client
        :param id: id-ul clientului
        """
        films=[]
        for id1,id2 in self.__rents:
            if id1==id:
                films.append(id2)
        return films

    def get_all_id2(self,id):
        """
        Functia returneaza toti clientii care au inchiriat un film
        :param id: id-ul clientului
        :return: lista
        """
        customers=[]
        for id1,id2 in self.__rents:
            if id2==id:
                customers.append(id1)
        return customers

    def get_all(self):
        """
        Afiseaza toate inchirierile
        :return: lista de tupluri
        """
        return self.__rents

def test_add_rent():
    repo_movie=MovieInMemoryRepo()
    repo_client=ClientsInMemoryRepo()
    repo_rent=RentInMemoryRepo()
    customer=client(1,"Alex Sirbu","502021314002")
    film=movie(1,"Pistruiatul","Un film fain","drama")

    repo_client.add_client(customer)
    repo_movie.add_movie(film)

    repo_rent.add_rent(1,1)

    repo_rent.get_all()==[(1,1)]

def test_delete_rent():
    repo_movie=MovieInMemoryRepo()
    repo_client=ClientsInMemoryRepo()
    repo_rent=RentInMemoryRepo()
    customer=client(1,"Alex Sirbu","502021314002")
    film=movie(1,"Pistruiatul","Un film fain","drama")

    repo_client.add_client(customer)
    repo_movie.add_movie(film)

    repo_rent.add_rent(1,1)

    assert repo_rent.get_all()==[(1,1)]

    repo_rent.delete_rent(1,1)

    assert repo_rent.get_all()==[]

def test_get_list():
    repo_rent=RentInMemoryRepo()
    repo_rent.add_rent(1,2)
    repo_rent.add_rent(1,3)
    repo_rent.add_rent(2,4)
    repo_rent.add_rent(5,3)

    lst=repo_rent.get_list()

    assert lst==[0,2,1,0,0,1]+[0]*94

def test_get_all_id():
    repo_rent=RentInMemoryRepo()
    repo_rent.add_rent(1,1)
    repo_rent.add_rent(1,2)
    assert repo_rent.get_all_id(1)==[1,2]

def test_last():
    repo_rent=RentInMemoryRepo()
    repo_rent.add_rent(1,2)
    repo_rent.add_rent(1,3)
    repo_rent.add_rent(2,4)
    repo_rent.add_rent(5,3)

    lst=repo_rent.get_all_for_max()

    assert lst==[1,5]