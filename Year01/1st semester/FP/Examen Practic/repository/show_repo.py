from domain.entities import Show
from exceptions.domain_exceptions import NotFoundException

class ShowRepo:
    """
    Repositry-ul, clasa expert resposabil cu operatii CRUD, face parte din arhitectura GRASP
    """
    def __init__(self,filename):
        """
        Clasa constructor a repo-ului
        :param filename: numele fisierului in care se afla datele
        """
        self.__filename=filename
        self.__repo=[]
        self.__load_from_file()

    def __load_from_file(self):
        """
        Functia ce preia datele din fisier
        """
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            for line in lines:
                if line=="\n":
                    continue
                title,artist,gen,time=line.split(";")
                time=int(time)
                s=Show(title,artist,gen,time)
                self.__repo.append(s)

    def add_show(self,s):
        """
        Functia care adauga un nou show
        :param s: spectacolul de adaugat
        """
        self.__repo.append(s)
        self.__save_to_file()

    def modify_show(self,s):
        """
        Functia modifica un show deja existent
        :param s: showul de modificat
        :raises: NotFoundException daca nu gaseste show-ul
        """
        title=s.getTitle()
        artist=s.getArtist()
        gen=s.getGen()
        time=s.getTime()
        found=False
        rez=[]
        for show in self.__repo:
            if show.getTitle()==title and show.getArtist()==artist:
                found=True
                show.setTime(time)
                show.setGen(gen)
            rez.append(show)
        self.__repo=rez[:]
        if not found:
            raise NotFoundException("Show-ul cu datele introduse nu a fost gasit")
        self.__save_to_file()

    def export(self,file):
        """
        Functia exporta datele din repo intr-un fiser dat ca parametru
        :param file: fisierul in care trebuie exportate datele
        :return: none
        """
        arr=self.__repo[:]
        arr.sort(key=lambda x:(x.getArtist(),x.getTitle()))
        with open(file,"w") as f:
            for show in arr:
                str_show=str(show.getTitle())+";"+str(show.getArtist())+";"+str(show.getGen())+";"+str(show.getTime())+"\n"
                f.write(str_show)

    def __save_to_file(self):
        """
        Functia salveaza datele in fiser
        :return: none
        """
        with open(self.__filename,"w") as f:
            for show in self.__repo:
                str_show=str(show.getTitle())+";"+str(show.getArtist())+";"+str(show.getGen())+";"+str(show.getTime())+"\n"
                f.write(str_show)

    def get_all(self):
        """
        Functia returneaza toate spectacolele din repo
        :return: lista de spectacole
        :rtype: list
        """
        return self.__repo

