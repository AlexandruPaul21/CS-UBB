from termcolor import colored

from domain.product import get_denumire, get_stoc, get_pret, create_product
from domain.store import add_product_to_store, delete_products_from_store, filter_by_name, setup_store, \
    get_products_list, undo


def print_menu():
    print("1. Adaugare produs (denumire, unitati_stoc, pret)")
    print("2. Stergerea tuturor produselor care nu sunt in stoc (unitati_stoc=0)")
    print("3. Afisarea produselor  care contin in denumire un string dat.")
    print("4. Undo ultima operatie")
    print("P. Afisarea listei curente de produse")
    print("5. Inchiderea aplicatiei")


def print_product_list(product_list):
    for i, product in enumerate(product_list):
        print(i, 'Denumire:', colored(get_denumire(product), 'blue'), '- Unitati stoc:',
              colored(get_stoc(product), 'blue'), '-Pret:', colored(get_pret(product), 'blue'))


def add_product_ui(store):
    try:
        denumire = input("Denumirea produsului:")
        unitati = int(input("Numarul de unitati in stoc:"))
        pret = float(input("Pretul pe unitate:"))

        p1 = create_product(denumire, unitati, pret)
        add_product_to_store(store, p1)

        print(colored("Adaugare realizata cu succes.", 'green'))
    except ValueError:
        # incercarea de a converti ceva invalid prin int()
        # sau float() genereaza ValueError
        print(colored("Introduceti numere pentru unitati stoc si pret.", 'red'))


def delete_product_ui(store):
    # daca am sterge dupa o anumita valoare
    # as putea sa o citesc aici
    delete_products_from_store(store)


def filter_by_name_ui(store):
    subs = input('Substringul este:')
    product_list = get_products_list(store)

    filtered_list = filter_by_name(product_list, subs)
    print_product_list(filtered_list)


def undo_ui(store):
    try:
        undo(store)
        print(colored('Undo realizat cu succes', 'green'))
    except ValueError as ve:
        print(colored(ve, 'red'))


def start():
    store = setup_store(True)
    finished = False
    while not finished:
        print_menu()
        option = input("Optiunea este:")
        if option == '1':
            add_product_ui(store)
        elif option == '2':
            delete_product_ui(store)
            print(colored('Stergerea s-a efectuat cu succes.', 'green'))
        elif option == '3':
            filter_by_name_ui(store)
        elif option.lower() == 'p':
            print_product_list(get_products_list(store))
        elif option == '4':
            undo_ui(store)
        elif option == '5':
            finished = True
        else:
            print(colored("Optiunea introdusa este invalida.", 'red'))
