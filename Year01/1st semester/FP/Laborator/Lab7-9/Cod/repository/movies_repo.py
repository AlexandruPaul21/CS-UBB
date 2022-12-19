from domain.entities import movie

def search_movie(the_list,id,n):
    if n<0:
        raise ValueError("Id-ul nu a fost gasit")
    if the_list[n-1].getId()==id:
        return the_list[n-1]
    return search_movie(the_list,id,n-1)


class MovieInMemoryRepo:
    def __init__(self):
        """
        Initializam reposistory-ul cu o lista vida
        """
        self.__movies=[]
        MovieInMemoryRepo.id=1

    def add_movie(self,new_movie):
        """
        Adauaga un nou film
        :param new_movie:noul film de adaugat
        :type: movie
        """
        film=movie(MovieInMemoryRepo.id,new_movie.getTitle(),new_movie.getDesc(),new_movie.getGen())
        MovieInMemoryRepo.id+=1
        self.__movies.append(film)

    def delete_movie(self,id):
        """
        Sterge un film identificat dupa id
        :param id: id-ul filmului de sters
        """
        new_movie_list=[]
        for film in self.__movies:
            if film.getId()!=id:
                new_movie_list.append(film)
        self.__movies=new_movie_list

    def modify_movie(self,id,new_title,new_desc,new_gen):
        """
        Modificam filmul identificat dupa id
        :param id: id-ul filmului de modificat
        :param new_title: noul titlu
        :param new_desc: noua descriere
        :param new_gen: noul gen
        """
        new_movie_list=[]
        for film in self.__movies:
            if film.getId()==id:
                film.setTitle(new_title)
                film.setDesc(new_desc)
                film.setGen(new_gen)
            new_movie_list.append(film)
        self.__movies=new_movie_list

    def search_movie_by_id(self,id):
        """
        Cauta filmul dupa id(unic identificator)
        :param id: id-ul filmului
        :return: filmul gasit
        :raises: ValueError daca filmul nu este gasit
        """
        return search_movie(self.__movies,id,len(self.__movies))
        # for film in self.__movies:
        #     if film.getId()==id:
        #         return film
        # raise ValueError("Id invalid")

    def search_movie_by_param(self,title,desc,gen):
        """
        Cauta filmul dupa descriere
        :param title: titlul filmului
        :param desc: descrierea lui
        :param gen: genul filmului
        :return: filmul gasit
        :raises: ValueError daca filmul nu este gasit
        """
        for film in self.__movies:
            if film.getTitle()==title or film.getDesc()==desc or film.getGen()==gen:
                return film
        raise ValueError("Filmul nu a fost gasit")

    def searchID(self,id):
        """
        Cauta dupa daca id-ul este assignat unui film
        :param id: id-ul filmului
        :return: True sau False
        """
        ok=True
        for elem in self.__movies:
            if elem.getId()==id:
                ok=False
        if ok:
            raise ValueError("Id-ul nu apartine niciunui film")

    def get_all_movies(self):
        """
        Returneaza lista tuturor filmelor
        :return: lista de filme
        """
        return self.__movies

    def __eq__(self, other):
        return self.__movies==other.__movies

class MovieFileRepo(MovieInMemoryRepo):
    def __init__(self,filename):
        """
        Constructorul clasei
        :param filename: numele fisierului
        """
        MovieInMemoryRepo.__init__(self)
        self.__filename=filename
        self.__load_from_file()

    def __load_from_file(self):
        """
        Pune in repo-ul din memorie filmele din fisier
        """
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            for line in lines:
                if line=="\n":
                    break
                movie_id,movie_title,movie_desc,movie_gen=[token.strip() for token in line.split(";")]
                film=movie(movie_id,movie_title,movie_desc,movie_gen)
                MovieInMemoryRepo.add_movie(self,film)

    def __save_to_file(self):
        """
        Salveaza in fisier date din in memory
        """
        with open(self.__filename,"w") as f:
            films=MovieInMemoryRepo.get_all_movies(self)
            for film in films:
                str_movie=str(film.getId())+";"+str(film.getTitle())+";"+str(film.getDesc())+";"+str(film.getGen())+"\n"
                f.write(str_movie)

    def add_movie(self,new_movie):
        """
        Suprascrie metoda din in memory
        :param new_movie: noul film de adaugat
        """
        MovieInMemoryRepo.add_movie(self,new_movie)
        self.__save_to_file()

    def delete_movie(self,id):
        """
        Suprascrie metoda din in memory
        :param id: id-ul filmului
        """
        MovieInMemoryRepo.delete_movie(self,id)
        self.__save_to_file()

    def modify_movie(self,id,new_title,new_desc,new_gen):
        """
        Suprascrie metoda din in memory
        :param id: id-ul filmului
        :param new_title: noul titlu
        :param new_desc: noua descriere
        :param new_gen: noul gen
        """
        MovieInMemoryRepo.modify_movie(self,id,new_title, new_desc, new_gen)
        self.__save_to_file()

    def search_movie_by_id(self,id):
        """
        Suprascrie metoda din in memory
        :return: filmul cautat
        """
        return MovieInMemoryRepo.search_movie_by_id(self,id)

    def search_movie_by_param(self,title,desc,gen):
        """
        Suprascrie metoda din in memory
        :return: filmul cautat
        """
        return MovieInMemoryRepo.search_movie_by_param(self,title,desc,gen)

    def searchID(self,id):
        """
        Suprascrie metoda din in memory
        :return: filmul cautat
        """
        return MovieInMemoryRepo.searchID(self,id)

    def get_all_movies(self):
        """
        Suprascrie metoda din in memory
        :return: filmele cautate
        """
        return MovieInMemoryRepo.get_all_movies(self)

    def __eq__(self, other):
        """
        Suprascire metoda eq
        """
        return MovieInMemoryRepo.__eq__(self,other)