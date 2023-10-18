from repository.event_repo import EventInFileRepo
from domain.entities import event
import unittest

class testRepoEvent(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo=EventInFileRepo("test_load.txt")

    def testLoadFromFile(self):
        list_of_events=self.__repo.get_all()
        e1=event("17.01.2022","17:30","Supt de pula")
        self.assertEqual(list_of_events[1],e1)
        e3=event("14.02.2022","10:00","Restanta ASC")
        self.assertEqual(list_of_events[3],e3)

    def testAddEv(self):
        self.__repo.add_ev("17.01.2022","17:30","Supt de pula")
        self.assertEqual(len(self.__repo.get_all()),5)