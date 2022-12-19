from domain.entities import bug

class BugFileRepo():
    def __init__(self,filename):
        """
        Metoda constructor a clasei BugFileRepo
        :param filename: fisierul din care se citeste
        """
        self.__bg=[]
        self.__filename=filename
        self.__load_from_file()

    def __load_from_file(self):
        """
        Functia care citeste din fisier bug-urile
        """
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            mark=[0]*1000
            for line in lines:
                try:
                    bg_id,bg_aff,bg_desc,bg_prior=[token.strip() for token in line.split(',')]
                    bg_id=int(bg_id)
                    bg_prior=int(bg_prior)
                    if mark[bg_id]==1:
                        raise ValueError("IdDuplicate")
                    else:
                        mark[bg_id]=1
                    bg=bug(bg_id,bg_aff,bg_desc,bg_prior)
                    self.__bg.append(bg)
                except:
                    pass

    def getBugs(self):
        """
        Functia care returneaza toate bugurile existente
        """
        return self.__bg

    def get_all(self):
        """
        Functia care returneaza toate bugurile existente
        """
        return self.__bg

#teste
def test_repo():
    repo=BugFileRepo("test.txt")

    assert len(repo.get_all())==8


