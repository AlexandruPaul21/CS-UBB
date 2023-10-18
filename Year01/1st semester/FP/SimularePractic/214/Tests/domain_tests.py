from domain.entities import event
from domain.validators import EventValidator
from exceptions.event_exceptions import InvalidEventException
import unittest

class testEvent(unittest.TestCase):
    def setUp(self) -> None:
        self.__valid=EventValidator()

    def testCreateEvent(self):
        e1=event("21.04.2002","15:30","Examen FP")
        self.assertEqual(e1.getTime(),"15:30")
        self.assertEqual(e1.getDate(),"21.04.2002")
        self.assertEqual(e1.getDesc(),"Examen FP")

        e1.setDate("21.01.2001")
        e1.setTime("16:30")
        e1.setDesc("Exam FPL")
        self.assertEqual(e1.getTime(), "16:30")
        self.assertEqual(e1.getDate(), "21.01.2001")
        self.assertEqual(e1.getDesc(), "Exam FPL")

    def testValidateEvent(self):
        self.assertRaises(InvalidEventException,self.__valid.validate,"40.12.2002","15:30","Exam Fp")
        self.assertRaises(InvalidEventException,self.__valid.validate,"31.1f.2002","15:30","Exam Fp")
        self.assertRaises(InvalidEventException,self.__valid.validate,"31.12.2002","1f:30","Exam Fp")
        self.assertRaises(InvalidEventException,self.__valid.validate,"31.12.2002","15:30","")
        self.assertRaises(InvalidEventException,self.__valid.validate,"31.13.2002","15:30","Exam")
        self.assertRaises(InvalidEventException,self.__valid.validate,"31.12.2002","25:30","Exam")
        self.assertRaises(InvalidEventException,self.__valid.validate,"31.12.2002","15:61","Exam")
