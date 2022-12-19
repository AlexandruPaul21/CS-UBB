import unittest
from domain.validators import EventValidator
from repository.event_repo import EventInFileRepo
from service.event_service import EventService
from exceptions.event_exceptions import InvalidEventException
from domain.entities import event

class testService(unittest.TestCase):
    def setUp(self) -> None:
        repo=EventInFileRepo("test_load.txt")
        valid=EventValidator()
        self.__srv=EventService(repo,valid)

    def test_initSorted(self):
        e1=event("17.01.2022","17:30","Supt de pula")
        e2=event("17.01.2022","12:00","Futut muste")
        self.assertEqual(self.__srv.init_sorted_today(),[e2,e1])

    def test_add_new_event(self):
        self.__srv.add_new_event("17.01.2022","17:30","Supt de pula")
        self.assertEqual(len(self.__srv.get_all_ev()),5)
        self.assertRaises(InvalidEventException,self.__srv.add_new_event,"40.12.2002","15:30","Exam Fp")
        self.assertRaises(InvalidEventException,self.__srv.add_new_event,"31.1f.2002","15:30","Exam Fp")
        self.assertRaises(InvalidEventException,self.__srv.add_new_event,"31.12.2002","1f:30","Exam Fp")
        self.assertRaises(InvalidEventException,self.__srv.add_new_event,"31.12.2002","15:30","")
        self.assertRaises(InvalidEventException,self.__srv.add_new_event,"31.13.2002","15:30","Exam")
        self.assertRaises(InvalidEventException,self.__srv.add_new_event,"31.12.2002","25:30","Exam")
        self.assertRaises(InvalidEventException,self.__srv.add_new_event,"31.12.2002","15:61","Exam")

    def test_find_by_date(self):
        self.assertEqual(self.__srv.find_by_date("17","01","2022"),[event("17.01.2022","12:00","Futut muste"),
                                                                    event("17.01.2022","17:30","Supt de pula")])

    def test_find_by_desc(self):
        self.assertEqual(self.__srv.find_by_desc("arhitectura"),[event("21.02.2022","14:30","Exam la arhitectura"),
                                                                 event("14.02.2022","10:00","Restanta la arhitectura")])
