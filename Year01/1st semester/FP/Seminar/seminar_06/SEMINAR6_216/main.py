from domain.validators import ProductValidator
from repository.product_repo import InMemoryRepository
from service.product_service import ProductService
from ui.console import Console

val = ProductValidator()
repo = InMemoryRepository()

srv = ProductService(repo, val)
ui = Console(srv)
ui.show_ui()