from repository.bug_repo import BugFileRepo
from erorros.creat_erorrs import NotFindBug

class BugService():
    def __init__(self,repo):
        """
        Metoda constructor a serverului GRASP
        :param repo: repository-ul
        """
        self.__repo=repo

    def find_bugs_for_desc(self,value):
        """
        Aceasta metoda rezolva prima cerinta
        :param value: valoarea ce trebuie cautata in descriere
        :return: o lista cu toate componentele cerute, sortata
        :rtype: list
        """
        result=[]
        for bg in self.__repo.getBugs():
            desc=bg.getDesc()
            if desc.find(value)!=-1:
                result.append(bg)
        if result==[]:
            raise NotFindBug("Nu a fost gasit nici-un bug cu descrierea cautata")
        result.sort(key=lambda x:x.getPrior(),reverse=True)
        return result

    def bugs_per_component(self):
        """
        Aceasta metoda rezolva a doua cerinta
        :return: un dicitionar ce va avea cheile denumirea componentelor iar elementele vor fi media prioritatilor
        :rtype: dict
        """
        result={}
        for bg in self.__repo.getBugs():
            aff=bg.getAff()
            result[aff]=[]
        for bg in self.__repo.getBugs():
            aff=bg.getAff()
            result[aff].append(bg.getPrior())
        bugs_type={}
        for keys,values in result.items():
            sm=0
            for elem in values:
                sm+=elem
            bugs_type[keys]=sm/len(values)
        return bugs_type

#teste
def test_BugService():
    repo=BugFileRepo("test.txt")
    srv=BugService(repo)

    assert len(srv.find_bugs_for_desc("crashes"))==3

    try:
        srv.find_bugs_for_desc("anabela")
        assert False
    except NotFindBug as e:
        assert True

def test_Bugs_Per_Comp():
    repo=BugFileRepo("test.txt")
    srv=BugService(repo)

    assert srv.bugs_per_component()=={'appCord': 10.0,
     'backend': 8.5,
     'frontend': 7.5,
     'server': 5.5,
     'service': 3.0,
     'sql': 15.0,
     'ui': 15.0}