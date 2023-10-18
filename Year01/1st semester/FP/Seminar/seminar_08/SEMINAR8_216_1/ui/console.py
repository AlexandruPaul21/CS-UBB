from termcolor import colored

from exceptions.exceptions import ValidationException, DuplicateIDException, ProductNotFoundException, \
    ShopNotFoundException, ProductAlreadyAssignedException


class Console:
    def __init__(self, srvProduct, srvShop, srvSaleItem):
        self.__srv_product = srvProduct
        self.__srv_shop = srvShop
        self.__srv_sale_item = srvSaleItem

    def __print_menu(self):
        print('Comenzi disponibile pentru produs, magazin. Adaugati sufixul',
              colored('_product sau _shop', 'magenta'), ' pentru a accesa optiunea dorita.')
        print('ENTITY COMMANDS:', colored('add, delete, update, show_all', 'green'))
        print('Comenzi disponibile pentru itemi de vanzare. Se folosesc fara adaugare string.')
        print('SALE ITEM COMMANDS:',
              colored('assign_product_for_sale, top3_locations, sale_report, show_all_sale_items, exit', 'green'))

    def __show_list_of_products(self, products):
        """
        Afiseaza produsele dintr-o lista data
        """

        if len(products) == 0:
            print(colored('Nu exista produse in lista.', 'magenta'))
        else:
            print('Lista de produse este:')
            for product in products:
                # print(product)
                print('ID:', colored(product.getId(), 'cyan'), ' - Denumire:', colored(product.getName(), 'cyan'),
                      '- Tara origine:', colored(product.getCountry(), 'cyan'))

    def __show_list_of_shops(self, shops):
        """
        Afiseaza magazinele dintr-o lista data
        """

        if len(shops) == 0:
            print(colored('Nu exista magazine in lista.', 'magenta'))
        else:
            print('Lista de produse este:')
            for shop in shops:
                # print(product)
                print('ID:', colored(shop.getId(), 'cyan'), ' - Denumire:', colored(shop.getName(), 'cyan'),
                      '- Locatie:', colored(shop.getLocation(), 'cyan'))

    def __show_all_sale_items(self, sale_items):
        """
        Afiseaza produsele dintr-o lista data
        """

        if len(sale_items) == 0:
            print(colored('Nu exista sale items in lista.', 'magenta'))
        else:
            print('Lista de sale_items este:')
            for sale_item in sale_items:
                print('Product: [', colored(str(sale_item.getProduct().getName()), 'cyan'), '; ', colored(str(
                    sale_item.getProduct().getCountry()), 'cyan'),
                      '] Shop: [' + colored(str(sale_item.getShop().getName()), 'magenta'), '; ' + colored(str(
                        sale_item.getShop().getLocation()), 'magenta'), '] Pret:', colored(str(sale_item.getPret()),
                                                                                           'blue'),
                      ' Unitati stoc: ', colored(str(
                        sale_item.getStoc()), 'blue'))

    def __add_product(self):
        """
        Adauga un produs
        """
        id = input('ID produs:')
        denumire = input('Denumirea produsului:')
        tara_origine = input('Tara de origine:')

        try:
            added_product = self.__srv_product.add_product(id, denumire, tara_origine)
            print('Produsul', added_product, 'a fost adaugat cu succes.')
        except ValidationException as ve:
            print(colored(str(ve), 'red'))
        except DuplicateIDException:
            print(colored('Exista deja un produs cu id-ul dat.', 'red'))

    def __add_shop(self):
        """
        Adauga un magazin
        """
        id = input('ID magazin:')
        nume = input('Numele magazinului:')
        tara_origine = input('Locatia:')

        try:
            added_shop = self.__srv_shop.add_shop(id, nume, tara_origine)
            print('Magazinul', added_shop, 'a fost adaugat cu succes.')
        except ValidationException as ve:
            print(colored(str(ve), 'red'))
        except DuplicateIDException:
            print(colored('Exista deja un magazin cu id-ul dat.', 'red'))

    def __delete_product(self):
        """
        Sterge un produs
        """
        id = input('ID produs:')

        try:
            deleted_product = self.__srv_product.delete_by_id(id)
            print('Produsul', deleted_product, 'a fost sters cu succes.')
        except ProductNotFoundException as e:
            print(colored(str(e), 'red'))

    def __delete_shop(self):
        """
        Sterge un magazin
        """
        id = input('ID magazin:')

        try:
            deleted_shop = self.__srv_shop.delete_by_id(id)
            print('Magazinul', deleted_shop, 'a fost sters cu succes.')
        except ShopNotFoundException as e:
            print(colored(str(e), 'red'))

    def __update_product(self):
        """
        Modifica un produs
        """
        id = input('ID produs:')
        denumire = input('Denumirea produsului:')
        tara_origine = input('Tara de origine:')

        try:
            updated_product = self.__srv_product.update_product(id, denumire, tara_origine)
            print('Produsul', updated_product, 'a fost modificat cu succes.')
        except ValidationException as ve:
            print(colored(str(ve), 'red'))
        except ProductNotFoundException as e:
            print(colored(str(e), 'red'))

    def __update_shop(self):
        """
        Modifica un magazin
        """
        id = input('ID magazin:')
        nume = input('Nume nou magazin:')
        locatie = input('Locatie noua:')

        try:
            updated_shop = self.__srv_shop.update_shop(id, nume, locatie)
            print('Magazinul', updated_shop, 'a fost modificat cu succes.')
        except ValidationException as ve:
            print(colored(str(ve), 'red'))
        except ShopNotFoundException as e:
            print(colored(str(e), 'red'))

    def __add_sale_item(self):
        """
        Adauga un sale item
        """
        product_id = input('ID produs:')
        shop_id = input('ID magazin:')
        try:
            pret = float(input('Pretul produsului:'))
            stoc = int(input('Nr. de unitati in stoc:'))

            sale_item = self.__srv_sale_item.add_sale_item(product_id, shop_id, pret, stoc)
            print('Sale item-ul', sale_item, 'a fost adaugat.')
        except ValueError:
            print(colored('Pret/stoc trebuie sa fie numere.'))
        except ValidationException as ve:
            print(colored(str(ve), 'red'))
        except ProductNotFoundException as e:
            print(colored(str(e), 'red'))
        except ShopNotFoundException as e:
            print(colored(str(e), 'red'))
        except ProductAlreadyAssignedException as e:
            print(colored(str(e), 'red'))

    def show_ui(self):

        while True:
            self.__print_menu()
            cmd = input('Comanda este: ')
            cmd = cmd.lower().strip()
            if cmd == 'add_product':
                self.__add_product()
            elif cmd == 'show_all_products':
                self.__show_list_of_products(self.__srv_product.get_all_products())
            elif cmd == 'delete_product':
                self.__delete_product()
            elif cmd == 'update_product':
                self.__update_product()

            elif cmd == 'add_shop':
                self.__add_shop()
            elif cmd == 'show_all_shops':
                self.__show_list_of_shops(self.__srv_shop.get_all_shops())
            elif cmd == 'delete_shop':
                self.__delete_shop()
            elif cmd == 'update_shop':
                self.__update_shop()

            elif cmd == 'assign_product_for_sale':
                self.__add_sale_item()
            elif cmd == 'show_all_sale_items':
                self.__show_all_sale_items(self.__srv_sale_item.get_all())

            elif cmd == 'exit':
                return
            else:
                print(colored('Comanda invalida', 'red'))
