from termcolor import colored


class Console:
    def __init__(self, srv):
        self.__srv = srv

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
                      '- Stoc:', colored(product.getStoc(), 'cyan'),
                      '- Pret:',
                      colored(product.getPret(), 'cyan'))

    def __add_product(self):
        """
        Adauga un produs
        """
        id = input('ID produs:')
        denumire = input('Denumirea produsului:')
        try:
            stoc = int(input('Nr. de unitati in stoc:'))
            pret = float(input('Pretul produsului:'))
        except ValueError:
            print(colored('Stoc/pret trebuie sa fie numere.', 'red'))
            return

        try:
            added_product = self.__srv.add_product(id, denumire, stoc, pret)
            print('Produsul', added_product, 'a fost adaugat cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_product(self):
        id = input('ID produs:')

        try:
            deleted_product = self.__srv.delete_by_id(id)
            print('Produsul', deleted_product, 'a fost sters cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __update_product(self):
        """
        Modifica un produs
        """
        id = input('ID produs:')
        denumire = input('Denumirea produsului:')
        try:
            stoc = int(input('Nr. de unitati in stoc:'))
            pret = float(input('Pretul produsului:'))
        except ValueError:
            print(colored('Stoc/pret trebuie sa fie numere.', 'red'))
            return

        try:
            updated_product = self.__srv.update_product(id, denumire, stoc, pret)
            print('Produsul', updated_product, 'a fost modificat cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_stock_zero(self):
        """
        Sterge produsele care nu sunt in stoc

        """
        # stoc_to_del = input('Unitati in stoc dupa care se sterge:')

        how_many_deleted = self.__srv.delete_stock_zero()
        print('S-au sters cu succes', colored(how_many_deleted, 'cyan'), 'produse care nu erau in stoc')

    def __filter_by_name(self):
        """
        Afiseaza tooate produsele care contin un string dat in nume
        """
        substring = input('String-ul dupa care se cauta:')
        filtered_list = self.__srv.filter_by_name(substring)
        self.__show_list_of_products(filtered_list)

    def show_ui(self):
        # command-driven menu (just to practice something different)
        # Lab7-9 oricare varianta (print-menu + optiuni/comenzi) este ok
        # dar si la comenzi sa existe un user guide, ce comenzi sunt dispobibile, cum le folosim
        while True:
            print('Comenzi disponibile: add, delete_by_id, delete_stock_zero, update, filter_by_name, show_all, exit')
            cmd = input('Comanda este: ')
            cmd = cmd.lower().strip()
            if cmd == 'add':
                self.__add_product()
            elif cmd == 'show_all':
                self.__show_list_of_products(self.__srv.get_all_products())
            elif cmd == 'delete_by_id':
                self.__delete_product()
            elif cmd == 'update':
                self.__update_product()
            elif cmd == 'delete_stock_zero':
                self.__delete_stock_zero()
            elif cmd == 'filter_by_name':
                self.__filter_by_name()
            elif cmd == 'exit':
                return
            else:
                print(colored('Comanda invalida', 'red'))
