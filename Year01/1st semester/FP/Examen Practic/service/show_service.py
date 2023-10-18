from domain.entities import Show
import random

class ShowService:
    """
    Controllerul GRASP al aplicatiei
    """
    def __init__(self,repo,valid):
        """
        Clasa constructor a controllerului
        :param repo: repository-ul
        :param valid: validatorul
        """
        self.__repo=repo
        self.__valid=valid

    def add_new_show(self,title,artist,gen,time):
        """
        Functia de adaugare a unui nou show
        :param title: tilul
        :param artist: artistul
        :param gen: genul
        :param time: durata
        :raises:DomainException daca nu se respecta regulile pentru paramterii
        """
        s=Show(title,artist,gen,time)
        self.__valid.validate(s)
        self.__repo.add_show(s)

    def modify_show_service(self,title,artist,gen,time):
        """
        Functia modifica un show
        :param title: titlul
        :param artist: artistul
        :param gen: genul
        :param time: durata
        :raises:NotFoundException daca nu exista showul dar datele sunt corecte
                DomainException daca nu sunt corecte datele
        """
        s=Show(title,artist,gen,time)
        self.__valid.validate(s)
        self.__repo.modify_show(s)

    def export_srv(self,file):
        """
        Se exporta datele in fiser
        :param file: fiserul in care se exporta
        """
        self.__repo.export(file)

    def rand(self,nr):
        """
        Se adauga random un numar de nr de enititati
        :param nr: numarul de entitati
        :return: lista entitatilor adaugate
        """
        added=[]
        for i in range(nr):
            add = random.randint(9, 12)
            title=""
            for j in range(add//2):
                title+=random.choice(['a','e','i','o','u'])
                if j==1:
                    title+=" "
                title+=random.choice(['b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z'])


            artist=""
            for j in range(add//2):
                artist+=random.choice(['a','e','i','o','u'])
                if j==1:
                    artist+=" "
                artist+=random.choice(['b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z'])


            gen=random.choice(["Comedie","Concert","Balet","Altele"])
            time=random.randint(100,100000)
            added.append(Show(title,artist,gen,time))
            self.__repo.add_show(Show(title,artist,gen,time))

        return added


    def get_all_service(self):
        """
        Returneaza toate entitatile din repo
        :return: lista de entitati
        :rtype: list
        """
        return self.__repo.get_all()