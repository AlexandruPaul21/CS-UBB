from repository.heli_repo import HeliFileRepo
from service.heli_service import HeliService
from ui.console import Console

ui=Console(HeliService(HeliFileRepo("data/helicopters.txt")))

ui.show_ui()