"""
Scrieti o aplicatie care are interfata utilizator tip consola cu meniu. Aplicatia ofera urmatoarele functionalitati:

1. Citeste de la tastatura o lista de numere rationale (float). 4.21, 3.20
2. Afiseaza media aritmetica a numerelor din lista.
3. Gaseste toate numerele din lista care sunt (si) intregi. 4.21, 3.20, 14.00
4. Afiseaza un mesaj daca prin adunarea partii fractionare a tuturor numerelor din lista se obtine o valoare mai mare de 1.
5. Iesire din aplicatie
"""
import math

from termcolor import colored


def print_menu():
    print("1. Citeste de la tastatura o lista de numere rationale (float).")
    print("2. Afiseaza media aritmetica a numerelor din lista.")
    print("3. Gaseste toate numerele din lista care sunt (si) intregi.")
    print(
        "4. Afiseaza un mesaj daca prin adunarea partii fractionare a tuturor numerelor din lista se obtine o valoare mai mare de 1.")
    print("5. Iesire din aplicatie")


def print_list(message, lst):
    print(message, colored(lst, 'green'))


def populate_list(the_list):
    """
    Populeaza lista data cu o serie de numere predefinite
    :param the_list: lista data
    :type the_list: list
    :return: -; lista se modifica, continand la final elementele adaugate
    :rtype:
    """
    the_list.append(5.41)
    the_list.append(1.09)
    the_list.append(2.7)
    the_list.append(3.0004)
    the_list.append(0.005)


def compute_mean(the_list):
    """
    Calculeaza media aritmetica a numerelor dintr-o lista data
    :param the_list: lista de numere pentru care se doreste calcularea mediei
    :type the_list: list
    :return: media aritmetica a numerelor din lista
    :rtype: float
    """
    suma = 0
    for elem in the_list:
        suma += elem
    return suma / len(the_list)


def get_all_integers(the_list):
    """
    Gaseste toate numerele intregi din lista
    :param the_list: lista data cu numere
    :type the_list: list
    :return: lista cu toate numerele intregi din the_list
    :rtype: list
    """
    elems = []
    for el in the_list:
        # el == int(el)
        if math.floor(el) == el:
            elems.append(el)
    return elems


def is_frac_greater_than(the_list, value):
    """
    Verifica daca suma partilor fractionare a elementelor din lista este mai mare decat o valoare data
    :param the_list: lista cu numere rationale
    :type the_list: list
    :param value: valoarea cu care se compara
    :type value: int
    :return: Adevarat daca suma > value, si Fals daca suma<value
    :rtype: bool
    """
    sum_frac = 0
    for el in the_list:
        el_frac = el - int(el)
        sum_frac += el_frac

    return sum_frac > value


def read_list():
    # format: 3.21, 4.5, 10.09,

    the_list_as_string = input("Dati lista in formatul cerut:")
    # print('Lista ca string', the_list_as_string, type(the_list_as_string))
    list_of_strings = the_list_as_string.split(",")
    # print('List of strings', [elem.strip() for elem in list_of_strings])
    # number_list = []
    # for elem in list_of_strings:
    #     elem_float = float(elem)
    #     number_list.append(elem)

    number_list = [float(elem.strip()) for elem in list_of_strings]

    # print('Lista de numere', number_list, type(number_list), type(number_list[0]))

    return number_list


def start():
    current_list = []
    populate_list(current_list)
    while True:
        print_menu()
        print_list('Lista curenta este:', current_list)
        option = int(input("Optiunea dumneavoastra este:"))
        if option == 1:
            current_list = read_list()
        elif option == 2:
            print('Media aritmetica a numerelor este', compute_mean(current_list))
        elif option == 3:
            print_list('Lista cu numere intregi este:', get_all_integers(current_list))
        elif option == 4:
            crt_value = 1
            if is_frac_greater_than(current_list, crt_value):
                print('Suma e mai mare ca 1.')
            else:
                print('Suma nu e mai mare ca 1.')
        elif option == 5:
            return


start()
