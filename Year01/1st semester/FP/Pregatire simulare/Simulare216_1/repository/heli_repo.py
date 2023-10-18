from domain.entities import helicopter
from exceptions.heli_exceptions import HeliNotFoundException

def cmp(elem):
    """
    Functie de comparare pentru sortare
    :param elem: elemntul listei
    """
    return elem[1]

class HeliInMemRepo:
    """
    Clasa repo in memmry
    """
    def __init__(self):
        """
        Initializam obiectele cu o lista vida
        """
        self.__repo=[]

    def add_heli(self,new_heli):
        """
        Adaugam un elicopter in lista
        :param new_heli: noul elicopter
        """
        self.__repo.append(new_heli)

    def delete_heli(self,id):
        """
        Stergem un elicopter din lista
        :param id: id-ul elicopterului
        """
        new_repo=[]
        for heli in self.__repo:
            if heli.getId()!=id:
                new_repo.append(heli)
        self.__repo=new_repo

    # def modify_heli(self,id,new_name,new_use,new_ya):
    #     new_repo=[]
    #     for heli in self.__repo:
    #         if heli.getId()==id:
    #             heli.setName(new_name)
    #             heli.setUse(new_use)
    #             heli.setFa_Year(new_ya)
    #         new_repo.append(heli)
    #     self.__repo=new_repo

    def get_all_for_scope(self,scope):
        """
        Selectam toate elicopterele care indeplinesc un anumit scop
        :param scope: scopul pe care acestea trebuie sa il indeplineasca
        :return: o lista in care sunt prezente numele elicopterului si id-ul sau
        """
        scope_heli=[]
        for heli in self.__repo:
            uses=heli.getUse()
            for use in uses:
                if use.strip()==scope:
                    scope_heli.append([heli.getId(),heli.getName()])
        if scope_heli==[]:
            raise HeliNotFoundException("Nu a fost gasit niciun elicopter pentru scopul introdus")
        else:
            scope_heli.sort(key=cmp,reverse=True)
            return scope_heli

    def get_years_for_scopes(self):
        """
        Functia selecteaza toate elicopeterele si le retine anul fabricatiei, grupat dupa scop
        :return: un dictionar in care cheile reprezinta scouprile iar elementele anii de fabric
        """
        scope_dict={}
        for heli in self.__repo:
            for use in heli.getUse():
                try:
                    scope_dict[use].append(heli.getFa_Year())
                except:
                    scope_dict[use]=[heli.getFa_Year()]
        return scope_dict

    def get_all(self):
        """
        Returneaza toate elementele din repo
        """
        return self.__repo


class HeliFileRepo(HeliInMemRepo):
    """
    Varianta de Repo in file care va facilita lucrul cu fisiere
    """
    def __init__(self,filename):
        """
        Clasa InFILE va mosteni toate metodele de la clasa in memory
        :param filename: numele fisierului
        """
        HeliInMemRepo.__init__(self)
        self.__filename=filename
        self.__load_from_file()

    def __load_from_file(self):
        """
        Preluam toate elicopterele din fisier si le stocam in memory repo
        """
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            for line in lines:
                if line=="\n":
                    break
                h_id,h_name,h_uses,h_ya=[token.strip() for token in line.split(',')]
                h_use=[token.strip() for token in h_uses.split(' ')]
                heli=helicopter(h_id,h_name,h_use,h_ya)
                HeliInMemRepo.add_heli(self,heli)

    def __save_to_file(self):
        """
        Salvam toate elicopterele in fisier
        """
        with open(self.__filename,"w") as f:
            heli_s=HeliInMemRepo.get_all(self)
            for heli in heli_s:
                str_heli=str(heli.getId())+','+str(heli.getName())+','
                str_use=""
                for use in heli.getUse():
                    str_use+=str(use)+" "
                str_heli+=str_use+','+str(heli.getFa_Year())+"\n"
                f.write(str_heli)

    def add_heli(self,new_heli):
        """
        Suprascriem metoda din clasa in memory
        """
        HeliInMemRepo.add_heli(self,new_heli)
        self.__save_to_file()

    def delete_heli(self,id):
        """
        Suprascriem metoda din clasa in memory
        """
        HeliInMemRepo.delete_heli(self,id)
        self.__save_to_file()

    # def modify_heli(self,id,new_name,new_use,new_ya):
    #     HeliInMemRepo.delete_heli(self,id,new_name,new_use,new_ya)
    #     self.__save_to_file()

    def get_years_for_scopes(self):
        """
        Suprascriem metoda din clasa in memory
        """
        return HeliInMemRepo.get_years_for_scopes(self)

    def get_all_for_scope(self,scope):
        """
        Suprascriem metoda din clasa in memory
        """
        return HeliInMemRepo.get_all_for_scope(self,scope)

    def get_all(self):
        """
        Suprascriem metoda din clasa in memory
        """
        return HeliInMemRepo.get_all(self)

def test_init_repo():
    repo=HeliFileRepo("test_heli.txt")

    assert len(repo.get_all())==3

def test_add_heli():
    repo=HeliFileRepo("test_heli.txt")
    h=helicopter("35","Panezerus",["militar","social","concediu"],"1965")
    repo.add_heli(h)

    repo.delete_heli("35")

def test_get_heli():
    repo=HeliFileRepo("test_heli.txt")

    assert repo.get_all_for_scope("militar")==[['45', 'Stark Rebound'],['2', 'Apache']]
    assert repo.get_all_for_scope("medical")==[['12','Flamingo'],['2','Apache']]

    try:
        repo.get_all_for_scope("distractie")
        assert False
    except HeliNotFoundException as he:
        assert True

def test_get_years_for_scope():
    repo=HeliFileRepo("test_heli.txt")

    assert repo.get_years_for_scopes()=={'agricultura': ['2014'],
     'livrare': ['2019'],
     'medical': ['2014', '1995'],
     'militar': ['1995', '2019'],
     'vacanta': ['2014']}