from domain.validators import ShowValidator
from repository.show_repo import ShowRepo
from service.show_service import ShowService
from UI.show_ui import Console

ui=Console(ShowService(ShowRepo("data/shows.txt"),ShowValidator()))
ui.show_ui()