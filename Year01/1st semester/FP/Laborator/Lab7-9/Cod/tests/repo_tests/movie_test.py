import unittest

from domain.entities import movie
from repository.movies_repo import MovieInMemoryRepo,MovieFileRepo

class TestMovieInMemRepo(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo=MovieInMemoryRepo()
        self.__repo1=MovieInMemoryRepo()
        self.__repo2=MovieInMemoryRepo()
        self.__repo3=MovieInMemoryRepo()
        self.__new_repo=MovieInMemoryRepo()

    def test_eq(self):
        film=movie(1,"Doamna","Un film fain","romantic")
        film1=movie(1,"Damna","Un film fain","romantic")


        self.__repo1.add_movie(film)
        self.__repo2.add_movie(film)
        self.assertEqual(self.__repo1,self.__repo2)

        self.__repo3.add_movie(film1)
        self.assertNotEqual(self.__repo1,self.__repo3)
        self.assertRaises(ValueError,self.__repo1.searchID,5)

    def test_add_movie(self):
        film=movie(1,"Doamna","Un film fain","romantic")

        self.__repo.add_movie(film)

        self.assertEqual(len(self.__repo.get_all_movies()),1)

    def test_delete_movie(self):
        film=movie(1,"Doamna","Un film fain","romantic")

        self.__repo.add_movie(film)
        self.__repo.delete_movie(1)

        self.assertEqual(len(self.__repo.get_all_movies()),0)

    def test_modify_movie(self):
        film=movie(1,"Doamna","Un film fain","romantic")

        self.__repo.add_movie(film)
        self.__repo.modify_movie(1,"Domnul","Un film urat","amuzant")

        film2=movie(1,"Domnul","Un film urat","amuzant")
        self.__new_repo.add_movie(film2)

        self.assertEqual(self.__repo,self.__new_repo)

    def test_get_all_movies(self):
        film=movie(1,"Doamna","Un film fain","romantic")

        self.__repo.add_movie(film)

        self.assertEqual(self.__repo.get_all_movies(),[film])

    def test_search_by_id(self):
        film=movie(1,"Doamna","Un film fain","romantic")
        self.__repo.add_movie(film)

        film=movie(2,"Doamnul","Un film tare","drama")
        self.__repo.add_movie(film)
        self.assertEqual(self.__repo.search_movie_by_id(2),film)
        self.assertEqual(self.__repo.search_movie_by_id(1).getTitle(),"Doamna")
        self.assertRaises(ValueError,self.__repo.search_movie_by_id,3)

    def test_search_by_param(self):
        film0=movie(1,"Doamna","Un film fain","romantic")
        self.__repo.add_movie(film0)

        film1=movie(2,"Doamnul","Un film tare","drama")
        self.__repo.add_movie(film1)

        self.assertEqual(self.__repo.search_movie_by_param("Doamna","",""),film0)
        self.assertEqual(self.__repo.search_movie_by_param("","Un film tare",""),film1)
        self.assertEqual(self.__repo.search_movie_by_param("","","drama"),film1)
        self.assertRaises(ValueError,self.__repo.search_movie_by_param,"","","")
        self.assertRaises(ValueError,self.__repo.search_movie_by_param,"Dmna","Un meci","dama")