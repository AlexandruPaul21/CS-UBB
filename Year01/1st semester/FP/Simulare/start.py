#aplication coordinator
from service.bug_service import BugService
from repository.bug_repo import BugFileRepo
from ui.user_interface import console

ui=console(BugService(BugFileRepo("data/bugs.txt")))

ui.show_ui()