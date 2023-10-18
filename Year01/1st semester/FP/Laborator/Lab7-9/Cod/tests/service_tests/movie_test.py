import unittest

from domain.entities import movie
from domain.validators import movieValidator
from repository.movies_repo import MovieInMemoryRepo
from service.movie_service import MovieService

class TestMovieService(unittest.TestCase):
    def setUp(self) -> None:
        self.__test_valid=movieValidator()
        self.__test_repo=MovieInMemoryRepo()
        self.__test_srv=MovieService(self.__test_repo,self.__test_valid)

    def test_insert_movie(self):
        film=self.__test_srv.insert_movie(1,"Ana","Fain","dragoste")
        self.assertEqual(film.getId(),1)
        self.assertEqual(film.getTitle(),"Ana")
        self.assertEqual(film.getDesc(),"Fain")
        self.assertEqual(film.getGen(),"dragoste")

        self.assertRaises(ValueError,self.__test_srv.insert_movie,1,"","Fain","Dragoste")

    def test_erase_movie(self):
        film1=self.__test_srv.insert_movie(1,"Ana","Fain","dragoste")
        film2=self.__test_srv.insert_movie(2,"Anabella","Fainut","actiune")

        self.__test_srv.erase_movie(2)
        self.assertEqual(len(self.__test_srv.get_all()),1)

        self.__test_srv.erase_movie(1)
        self.assertEqual(len(self.__test_srv.get_all()),0)

    def test_modify_film(self):
        film1=self.__test_srv.insert_movie(1,"Ana","Fain","dragoste")
        film2=self.__test_srv.insert_movie(2,"Anabella","Fainut","actiune")

        film1=self.__test_srv.modify_film(1,"Joana","Urat","tragic")
        self.assertEqual(film1.getId(),1)
        self.assertEqual(film1.getGen(),"tragic")
        self.assertEqual(film1.getDesc(),"Urat")
        self.assertEqual(film1.getTitle(),"Joana")
        self.assertRaises(ValueError,self.__test_srv.modify_film,1,"","Urat","tragic")

    def test_get_all(self):
        film1=self.__test_srv.insert_movie(1,"Ana","Fain","dragoste")
        film2=self.__test_srv.insert_movie(2,"Anabella","Fainut","actiune")

        self.assertEqual(len(self.__test_srv.get_all()),2)

    def test_search_by_id(self):
        film1=self.__test_srv.insert_movie(1,"Ana","Fain","dragoste")
        film2=self.__test_srv.insert_movie(2,"Anabella","Fainut","actiune")

        self.assertEqual(self.__test_srv.search_by_id(1),film1)
        self.assertEqual(self.__test_srv.search_by_id(2),film2)
        self.assertRaises(ValueError,self.__test_srv.search_by_id,3)

    def test_search_by_param(self):
        film1=self.__test_srv.insert_movie(1,"Ana","Fain","dragoste")
        film2=self.__test_srv.insert_movie(2,"Anabella","Fainut","actiune")

        self.assertEqual(self.__test_srv.search_by_param("Ana","",""),film1)
        self.assertEqual(self.__test_srv.search_by_param("","Fainut","actiune"),film2)
        self.assertRaises(ValueError,self.__test_srv.search_by_param,"","","")
        self.assertRaises(ValueError,self.__test_srv.search_by_param,"","Finut","")

    def test_random_movies(self):
        self.__test_srv.add_random_movies(5)

        self.assertEqual(len(self.__test_srv.get_all()),5)