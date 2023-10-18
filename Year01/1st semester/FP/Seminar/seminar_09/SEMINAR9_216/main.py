from domain.validators import ProductValidator, ShopValidator, SaleItemValidator
from repository.product_repo import ProductRepoFile
from repository.repo_sale_item import SaleItemRepoMemory, SaleItemRepoFile
from repository.shop_repo import ShopRepoFile
from service.product_service import ProductService
from service.sale_item_service import SaleItemService
from service.shop_service import ShopService
from ui.console import Console

val_product = ProductValidator()
# repo_product = ProductInMemoryRepo()
repo_product_file = ProductRepoFile('data/products.txt')
srv_product = ProductService(repo_product_file, val_product)

val_shop = ShopValidator()
repo_shop = ShopRepoFile('data/shops.txt')
srv_shop = ShopService(repo_shop, val_shop)

sale_item_repo = SaleItemRepoFile('data/sale_items.txt')
sale_item_val = SaleItemValidator()
srv_sale_item = SaleItemService(sale_item_repo, sale_item_val, repo_product_file, repo_shop)

ui = Console(srv_product, srv_shop, srv_sale_item)
ui.show_ui()
