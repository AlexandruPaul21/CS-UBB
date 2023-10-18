from domain.entities import client,movie
from domain.validators import clientValidator,movieValidator
from repository.clients_repo import ClientsFileRepo
from repository.movies_repo import MovieFileRepo
from repository.rent_repo import RentInMemoryRepo
from service.rent_service import RentService
from service.client_service import ClientService
from service.movie_service import MovieService
from ui.console import console

Film=MovieService(MovieFileRepo("data/movies.txt"),movieValidator())
Customer=ClientService(ClientsFileRepo("data/clients.txt"),clientValidator())
Rent=RentService(RentInMemoryRepo())

ui=console(Film,Customer,Rent)

ui.show_ui()