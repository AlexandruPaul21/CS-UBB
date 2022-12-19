from repository.heli_repo import HeliFileRepo
from domain.entities import helicopter

class HeliService:
    """
    Clasa reprezinta un GRASP controller al aplicatiei
    """
    def __init__(self,repo):
        """
        Initializam srv ul cu un repo
        """
        self.__repo=repo

    def add_new_heli(self,new_heli):
        """
        Funcita va realiza adaugarea unui nou elicopter
        :param new_heli: noul elicopter
        """
        self.__repo.add_heli(new_heli)

    def delete_heli(self,id):
        """
        Functia va sterge un elicopter
        :param id: id-ul de sters
        """
        self.__repo.delete_heli(id)

    def get_for_scope(self,scope):
        """
        Functia returneaza toate elicopterele ce indeplinesc un scop
        :param scope: scopul din cerinta
        """
        return self.__repo.get_all_for_scope(scope)

    def get_years_for_scope(self):
        """
        Functia returneaza anii de fabricatie, grupati dupa scopul in care sunt folosite elicopterele
        :return:
        """
        return self.__repo.get_years_for_scopes()

def test_add_heli():
    test_repo=HeliFileRepo("test.txt")
    test_srv=HeliService(test_repo)
    h=helicopter("1","Ana",["transport","militar"],"1976")

    test_srv.add_new_heli(h)