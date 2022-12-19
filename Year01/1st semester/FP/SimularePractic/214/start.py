"""
Application coordinator
"""
from domain.validators import EventValidator
from repository.event_repo import EventInFileRepo
from service.event_service import EventService
from UI.ui_event import UIConsole

ui=UIConsole(EventService(EventInFileRepo("data/events.txt"),EventValidator()))
ui.show_ui()

