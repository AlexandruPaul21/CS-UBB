from termcolor import colored


class Console:
    def __init__(self, srv):
        self.__srv = srv

    def __show_all_products(self):
        """
        Afiseaza toate produsele disponibile
        """
        products = self.__srv.get_all_products()
        if len(products) == 0:
            print(colored('Nu exista produse in lista.', 'magenta'))
        else:
            print('Lista de produse este:')
            for product in products:
                # print(product)
                print('Denumire:', colored(product.getName(), 'cyan'), '- Stoc:', colored(product.getStoc(), 'cyan'),
                      '- Pret:',
                      colored(product.getPret(), 'cyan'))

    def __add_product(self):
        """
        Adauga un produs
        """
        denumire = input('Denumirea produsului:')
        try:
            stoc = int(input('Nr. de unitati in stoc:'))
            pret = float(input('Pretul produsului:'))
        except ValueError:
            print(colored('Stoc/pret trebuie sa fie numere.','red'))
            return

        try:
            added_product = self.__srv.add_product(denumire, stoc, pret)
            print('Produsul', added_product, 'a fost adaugat cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def show_ui(self):
        # command-driven menu (just to practice something different)
        # Lab7-9 oricare varianta (print-menu + optiuni/comenzi) este ok
        # dar si la comenzi sa existe un user guide, ce comenzi sunt dispobibile, cum le folosim
        while True:
            print('Comenzi disponibile: add, delete, show_all, undo, exit')
            cmd = input('Comanda este: ')
            cmd = cmd.lower().strip()
            if cmd == 'add':
                self.__add_product()
            elif cmd == 'show_all':
                self.__show_all_products()
            elif cmd == 'exit':
                return
            else:
                print(colored('Comanda invalida', 'red'))
