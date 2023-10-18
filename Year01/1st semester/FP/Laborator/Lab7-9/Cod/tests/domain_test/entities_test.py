import unittest

from domain.entities import client,movie

class TestMovie(unittest.TestCase):
    def test_create_movie(self):
        film=movie(1,"Doamna","Un film fain","romantic")
        self.assertEqual(film.getId(),1)
        self.assertEqual(film.getTitle(),"Doamna")
        self.assertEqual(film.getDesc(),"Un film fain")
        self.assertEqual(film.getGen(),"romantic")

        film.setTitle("Domnul")
        film.setDesc("Un film urat")
        film.setGen("drama")

        self.assertEqual(film.getTitle(),"Domnul")
        self.assertEqual(film.getDesc(),"Un film urat")
        self.assertEqual(film.getGen(),"drama")

    def test_eq_movie(self):
        movie1=movie(1,"Doamna","Fain","drama")
        movie2=movie(1,"Doamna","Fain","drama")

        self.assertEqual(movie1,movie2)

        movie3=movie(1,"Doamna","Fain","dama")

        self.assertNotEqual(movie1,movie3)

class TestClient(unittest.TestCase):
    def test_create_client(self):
        customer=client(1,"Georgescu Valentin","5065666545641")

        self.assertEqual(customer.getId(),1)
        self.assertEqual(customer.getName(),"Georgescu Valentin")
        self.assertEqual(customer.getCNP(),"5065666545641")

        customer.setName("George Valentin")
        customer.setCNP("5065166545641")

        self.assertEqual(customer.getName(),"George Valentin")
        self.assertEqual(customer.getCNP(),"5065166545641")

    def test_eq_clients(self):
        customer1=client(1,"Alex","5020421314002")
        customer2=client(1,"Alex","5020421314002")

        self.assertEqual(customer1,customer2)

        customer3=client(1,"Alex","5020421324002")

        self.assertNotEqual(customer3,customer1)