"""
Se cere dezvoltarea unei aplicatii care gestioneaza produsele dintr-un magazin de dulciuri (intr-o lista).
 Fiecare produs are o denumire (string), numar de unitati in stoc (int) si pret pe unitate (float).

Aplicatia are interfata de tip consola, si ofera urmatoarele functionalitati:

1. Adaugare produs (denumire, unitati_stoc, pret)
2. Stergerea tuturor produselor care nu sunt in stoc (unitati_stoc=0)
3. Afisarea produselor  care contin in denumire un string dat.
4. Undo ultima operatie
5. Inchiderea aplicatiei

Se va adauga o optiune pentru printarea listei curente (sau se va printa dupa fiecare operatie).
Se cere folosirea procesului de dezvoltare incrementala bazata pe
 functionalitati si dezvoltarea dirijata de teste.
"""

# functii care lucreaza cu entitatea produs sau cu lista
from termcolor import colored


def generate_products():
    # stoc 0: 2
    # denumire: 'jel':2
    # denumire: 'cioco':1
    # denumire: 'acadele': 0
    return [['jelly beans', 12, 9.5], ['ciocolata', 16, 10.25], ['licorice', 1, 65],
            ['bomboane Halloween', 0, 13], ['vata de zahar', 0, 11], ['dropsuri', 100, 1.25],
            ['jeleuri haribo', 18, 5.7]]


def get_denumire(product):
    return product[0]


def get_stoc(product):
    return product[1]


def get_pret(product):
    return product[2]


def create_product(denumire, unitati_stoc, pret):
    """
    Creeaza un produs dulce cu atributele date
    :param denumire: denumirea data
    :type denumire: string
    :param unitati_stoc: numarul de unitati
    :type unitati_stoc: int
    :param pret: pretul dat
    :type pret: int
    :return: produsul cu atributele date
    :rtype: list (len(lst)=3, lst[0] = denumire, lst[1] = unitati stoc, lst[2]= pret)
    """
    return [denumire, unitati_stoc, pret]


def add_product_to_list(product_list, product):
    """
    Adauga un produs la lista
    :param product_list: lista de produse la care se adauga
    :type product_list: list (of lists)
    :param product: produsul care se adauga
    :type product: list
    :return: -; se modifica lista prin adaugarea noului element
    :rtype:
    """
    product_list.append(product)


def remove_from_list_stock_zero(product_list):
    """
    Elimina din lista produsele care nu sunt in stoc
    :param product_list: lista de produse
    :type product_list: list (of lists)
    :return: lista din care au fost eliminate produsele cu stoc 0
    :rtype: list (of lists)
    """
    new_list = [product for product in product_list if get_stoc(product) != 0]
    return new_list


def filter_by_name(product_list, my_substring):
    """
    Gaseste produsele care contin in denumire un substring dat
    :param product_list: lista de produse
    :type product_list: list (of lists)
    :param my_substring: substring-ul dat
    :type my_substring: string
    :return: lista cu produsele care contin in denumire un substring dat
    :rtype: list (of lists)
    """
    filtered_list = []
    if my_substring != '':
        for el in product_list:
            if get_denumire(el).find(my_substring) != -1:
                filtered_list.append(el)
    return filtered_list


# functii care tin de interfata utilizator

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


def add_product_ui(product_list):
    denumire = input("Denumirea produsului:")
    unitati = int(input("Numarul de unitati in stoc:"))
    pret = float(input("Pretul pe unitate:"))

    p1 = create_product(denumire, unitati, pret)
    add_product_to_list(product_list, p1)


def delete_product_ui(product_list):
    # citire valoare
    # ne-ar mai trebui un parametru la remove
    # daca am citi o valoare de la tastatura
    product_list = remove_from_list_stock_zero(product_list)
    return product_list


def filter_by_name_ui(product_list):
    subs = input('Substringul este:')

    filtered_list = filter_by_name(product_list, subs)
    print_product_list(filtered_list)


def start():
    crt_product_list = generate_products()
    finished = False
    while not finished:
        print_menu()
        option = input("Optiunea este:")
        if option == '1':
            # functionalitatea de adaugare
            add_product_ui(crt_product_list)
            print(colored('Adaugarea s-a efectuat cu succes.', 'green'))
        elif option == '2':
            crt_product_list = delete_product_ui(crt_product_list)
            print(colored('Stergerea s-a efectuat cu succes.', 'green'))
        elif option == '3':
            filter_by_name_ui(crt_product_list)
        elif option.lower() == 'p':
            print_product_list(crt_product_list)
        elif option == '5':
            finished = True
        else:
            print(colored("Optiunea introdusa este invalida.", 'red'))


# start()


def test_create():
    p1 = create_product('acadele', 15, 8.5)
    assert (type(p1) == list)

    assert (p1[0] == 'acadele')
    assert (p1[1] == 15)
    assert (p1[2] == 8.5)

    # in momentul in care vor fi si validari/exceptii
    # aici trebuie verificate si cazurile cu input
    # gresit (exceptiile)


def test_add_product_to_list():
    test_list = []
    p1 = create_product('acadele', 15, 8.5)
    p2 = create_product('jeleuri', 26, 5.6)

    add_product_to_list(test_list, p1)
    assert (len(test_list) == 1)
    assert (get_denumire(test_list[0]) == 'acadele')
    assert (get_stoc(test_list[0]) == 15)
    assert (get_pret(test_list[0]) == 8.5)

    add_product_to_list(test_list, p2)
    assert (len(test_list) == 2)
    assert (get_denumire(test_list[0]) == 'acadele')
    assert (get_stoc(test_list[0]) == 15)
    assert (get_pret(test_list[0]) == 8.5)

    assert (get_denumire(test_list[1]) == 'jeleuri')
    assert (get_stoc(test_list[1]) == 26)
    assert (get_pret(test_list[1]) == 5.6)


def test_remove_from_list_stock():
    test_list1 = generate_products()
    initial_length = len(test_list1)
    test_list1 = remove_from_list_stock_zero(test_list1)

    assert (len(test_list1) == initial_length - 2)
    assert (len([product for product in test_list1 if get_stoc(product) == 0]) == 0)
    crt_length = len(test_list1)

    test_list1 = remove_from_list_stock_zero(test_list1)
    assert (len(test_list1) == crt_length)

    test_list2 = []
    test_list2 = remove_from_list_stock_zero(test_list2)
    assert (len(test_list2) == 0)


def test_filter_by_name():
    test_list = []
    filtered_list1 = filter_by_name(test_list, 'jel')
    assert (len(filtered_list1) == 0)

    test_list2 = generate_products()

    filtered_list2 = filter_by_name(test_list2, 'jel')
    assert (len(filtered_list2) == 2)
    assert (get_denumire(filtered_list2[0]) == 'jelly beans')

    filtered_list3 = filter_by_name(test_list2, 'cioco')
    assert (len(filtered_list3) == 1)
    assert (get_denumire(filtered_list3[0]) == 'ciocolata')

    filtered_list4 = filter_by_name(test_list2, 'acadea')
    assert (len(filtered_list4) == 0)

    filtered_list5 = filter_by_name(test_list2, '')
    assert (len(filtered_list5) == 0)


test_create()
test_add_product_to_list()
test_remove_from_list_stock()
test_filter_by_name()
